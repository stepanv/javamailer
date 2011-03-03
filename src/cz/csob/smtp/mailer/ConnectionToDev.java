package cz.csob.smtp.mailer;

import java.io.OutputStream;

public class ConnectionToDev extends SimpleProcess {

	Thread sshReader;

	ConnectionToDev ssh = this;

	OutputStream output;

	ConnectionToDev(OutputStream outputToWrite) {
		super("ssh.exe " + Configuration.sshUser + "@" + Configuration.sshHost
				+ " \"telnet " + Configuration.sshSmtpHost + " "
				+ Configuration.sshSmtpPort + "\"");

		this.output = outputToWrite;

		sshReader = new Thread(new Runnable() {
			public void run() {
				try {

					String line;
					while (true) {
						line = ssh.outputLine();
						System.out.println("SSH reply:" + line);
						if (line.matches("^[0-9][0-9][0-9].*")) {
							output.write((line + "\n").getBytes());
							output.flush();
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		});
	}

	public void exec() {
		super.exec();
		sshReader.start();
	}

	public static ConnectionToDev factoryConnectionToDev(
			OutputStream outputToWrite) {
		return new ConnectionToDev(outputToWrite);
	}
}

