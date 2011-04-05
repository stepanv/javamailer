package cz.csob.smtp.mailer;

import java.io.IOException;
import java.io.OutputStream;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

/**
 * Pros: 
 * [1] when sending multiple lines (e.g. when client writes DATA section)
 * we simply send every line to the ssh server 
 * [2] when ssh sever OR client
 * closes connection, the blocking operation ends and by using this interrupted
 * thread, we can interrupt the other one.
 * 
 * @author stepan
 * 
 */
public class Receiver extends Thread implements Runnable {

	private static final Pattern CORRECT_INPUT_PATT = Pattern
			.compile("^[0-9][0-9][0-9].*");

	private static Logger logger = Logger.getLogger(Receiver.class.getName());

	private OutputStream output;

	private Notifier notifyObject;

	private PipedProcess pipedProcess;

	public interface Notifier {
		public void notifyMe();
	}

	public Receiver(OutputStream outputToWrite, Notifier notifyObject) {
		this.output = outputToWrite;
		this.notifyObject = notifyObject;
		this.pipedProcess = new PipedProcess(String.format(
				"%s %s %s@%s \"telnet %s %s\"", Configuration.sshProgram,
				Configuration.sshAddparams, Configuration.sshUser,
				Configuration.sshHost, Configuration.sshSmtpHost,
				Configuration.sshSmtpPort));
	}

	/**
	 * Executes ssh tunnel to smtp server and starts Thread reading ssh output.
	 */
	@Override
	public void run() {
		try {
			pipedProcess.exec();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			String line;
			while (true) {
				line = pipedProcess.outputLine();
				logger.debug("SSH reply:" + line);
				if (line == null) {
					// TODO maybe do something better. .. for sure!!
					logger.debug("End-of-stream reached on stream from ssh.");
					return;
				}
				if (CORRECT_INPUT_PATT.matcher(line).matches()) {
					output.write((line + "\r\n").getBytes());
					output.flush();
				}
			}
		} catch (Exception e) {
			logger.info(e);
		} finally {
			logger.debug("notifying parent thread about sshReader exiting");
			Receiver.this.notifyObject.notifyMe();
		}

	}

	public void stdinWrite(byte[] bytes) throws IOException {
		pipedProcess.stdinWrite(bytes);
	}

	public void stdinWrite(String string) throws IOException {
		pipedProcess.stdinWrite(string);

	}

	public void kill() {
		pipedProcess.destroy();

	}

}
