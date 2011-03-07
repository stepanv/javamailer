package cz.csob.smtp.mailer;

import java.io.IOException;
import java.io.OutputStream;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public class ConnectionToSmtp {

	private static final Pattern CORRECT_INPUT_PATT = Pattern
			.compile("^[0-9][0-9][0-9].*");

	private static Logger logger = Logger.getLogger(ConnectionToSmtp.class.getName());
	
	private Thread sshReader = new Thread(new Runnable() {
		public void run() {
			try {
				String line;
				while (true) {
					line = ssh.outputLine();
					logger.debug("SSH reply:" + line);
					if (line == null) {
						// TODO maybe do something better. .. for sure!!
						output.close();
						return;
					}
					if (CORRECT_INPUT_PATT.matcher(line).matches()) {
						output.write((line + "\r\n").getBytes());
						output.flush();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	});

	private PipedProcess ssh = new PipedProcess(String.format(
			"%s %s %s@%s \"telnet %s %s\"", Configuration.sshProgram,
			Configuration.sshAddparams, Configuration.sshUser,
			Configuration.sshHost, Configuration.sshSmtpHost,
			Configuration.sshSmtpPort));

	private OutputStream output;

	ConnectionToSmtp(OutputStream outputToWrite) {
		this.output = outputToWrite;
	}

	public void exec() {
		ssh.exec();
		sshReader.start();
	}

	public static ConnectionToSmtp factoryConnectionToDev(
			OutputStream outputToWrite) {
		return new ConnectionToSmtp(outputToWrite);
	}

	public void stdinWrite(String string) throws IOException {
		ssh.stdinWrite(string);
	}

	public void destroy() {
		ssh.destroy();
	}
}
