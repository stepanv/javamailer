package net.uvavru.smtp.mailer;

import java.io.IOException;
import java.io.OutputStream;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

/**
 * The {@code Receiver} class is a specialized thread which gets data from the
 * {@link PipedProcess} instance and forwards them to {@link Mailer}'s clients.
 * It is not necessary to allocate special thread for incoming data because SMTP
 * communication is synchronous, but it has few pros:
 * 
 * [1] when sending multiple lines (e.g. when client writes DATA section) we
 * simply send every line to the ssh server [2] when ssh sever OR client closes
 * connection, the blocking operation ends and by using this interrupted thread,
 * we can interrupt the other one.
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

	/**
	 * Notification interface which forces all implementation to react when
	 * other threads need it.
	 * 
	 * @author stepan
	 * 
	 */
	public interface Notifier {
		/**
		 * Implementation should react somehow when {@code notifyMe} method is
		 * called.
		 */
		public void notifyMe();
	}

	private static long receiverIdGenerator = 0;

	/**
	 * Creates {@code Receiver} instance.
	 * 
	 * @param outputToWrite
	 *            Stream to write output to
	 * @param notifyObject
	 *            Notification about {@code Receiver} end object.
	 */
	public Receiver(OutputStream outputToWrite, Notifier notifyObject) {
		super("Receiver-" + receiverIdGenerator++);

		this.output = outputToWrite;
		this.notifyObject = notifyObject;
		this.pipedProcess = new PipedProcess(String.format(
				"%s %s %s@%s \"telnet %s %s\"", Configuration.getSshProgram(),
				Configuration.getSshAddparams(), Configuration.getSshUser(),
				Configuration.getSshHost(), Configuration.getSshSmtpHost(),
				Configuration.getSshSmtpPort()));
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
				String errorLine = pipedProcess.errorLine();
				if (errorLine != null && !"".equals(errorLine)) {
					logger.error(errorLine);
				}
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

	/**
     * Message written to the {@code stdin} of the piped process.
     * 
     * @param bytes Array of bytes to write
     * @throws IOException in case of IO failure
     */
	public void stdinWrite(byte[] bytes) throws IOException {
		pipedProcess.stdinWrite(bytes);
	}

	/**
     * Message written to the {@code stdin} of the piped process.
     * 
     * @param str String to write
     * @throws IOException in case of IO failure.
     */
	public void stdinWrite(String string) throws IOException {
		pipedProcess.stdinWrite(string);

	}

	/**
	 * Kills receiver's piped process.
	 */
	public void kill() {
		pipedProcess.destroy();

	}

}
