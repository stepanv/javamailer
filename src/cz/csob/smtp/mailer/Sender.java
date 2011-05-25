package cz.csob.smtp.mailer;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import cz.csob.smtp.mailer.Receiver.Notifier;

class Sender extends Thread implements Runnable, Notifier {
	private final static int BUF_SIZE = 2048;

	private static Logger logger = Logger.getLogger(Sender.class.getName());

	/** buffer to use for requests */
	byte[] buf = new byte[BUF_SIZE];

	/** Socket to client we're handling */
	private Socket socketToAClient = null;

	/** Connection to ssh server */
	private Receiver ssh;

	private BufferedReader clientInput;

	Sender(Socket socketToAClient) {
		this.socketToAClient = socketToAClient;
	}

	public Sender() {

	}

	synchronized void setSocketToAClient(Socket socketToAClient) {
		this.socketToAClient = socketToAClient;
		notify();
	}

	private void stopHook() {
		logger.debug("running stop hook");
		// TODO rewrite this weird thing
		try {
			// synchronized (ssh) { TODO do somwthing with this...
			ssh.stdinWrite("QUIT\n");
			// }
			logger.debug("sent QUIT to remote telnet instance...");
		} catch (Exception e) {
			logger.debug("sent QUIT to remote telnet instance... NOK");
		}
		try {
			clientInput.close();
			logger.debug("closed input stream...");
		} catch (Exception e) {
			logger.debug("closed input stream... NOK");
		}
		try {
			ssh.kill();
			logger.debug("Killed ssh process...");
		} catch (Exception e) {
			logger.debug("Killed ssh process... NOK");
		}
		try {
			socketToAClient.close();
			logger.debug("Socket closed...");
		} catch (Exception e) {
			logger.debug("Socket closed... NOK");
		}
	}

	/**
	 * A volatile variable which is used to mark current thread as one which has
	 * to stop.
	 */
	private volatile boolean running = true;

	/**
	 * Stop current thread in an asynchronous safe way. This doesn't force the
	 * thread to stop immediately.
	 */
	public void stopAsync() {
		running = false;
	}

	public void stopFast() {
		this.interrupt();
	}

	/**
	 * Worker's main thread method. Worker is marked as a running thread and
	 * waiting for a socket to a client. Afterwards client is handled.
	 */
	public synchronized void run() {
		try {
			Mailer.addRunningWorker(this);
			while (running) {
				/*
				 * Waiting for a socket to a client. Socket is set by main
				 * program loop when a client is connected to the default server
				 * port. Communication then continues on a different socket so
				 * that default socket (port) is free and available for more
				 * parallel connections. Thread could be stopped by calling
				 * interrupt() method.
				 */
				while (socketToAClient == null) {
					wait();
				}

				/* handle client */
				handleClient();

				/*
				 * go back in wait queue if there's fewer than numHandler
				 * connections.
				 */
				socketToAClient = null;
				if (!Mailer.conditionallyAddWorkerToTheIdlePool(this)) {
					running = false;
				}

				logger.debug("Worker main loop ended.");
			}
		} catch (InterruptedException e) {
			/* received interrupt signal */
			logger.debug("received interrupt");
		} finally {
			stopHook();
			Mailer.removeRunningWorker(this);
			logger.debug("Worker thread removed from running ones.");
		}
	}

	private static final Pattern HELLO_PATT = Pattern.compile("[HE][EH]LO .*");

	public void endClientSession() {
		try {
			if (socketToAClient != null) {
				socketToAClient.close();
			}
		} catch (IOException e) {
			// we don't mind if something happens
		}
	}

	public void notifyMe() {
		endClientSession();
	}

	/**
	 * Handles SMTP client which has connected to this server. It forwards all
	 * client's input to remote ssh server and all remote ssh server output is
	 * written back to a client.
	 * 
	 * @throws IOException
	 *             when client unexpectedly ends a communication or another
	 *             network problem happens.
	 */
	void handleClient() {
		try {
			InputStream is = new BufferedInputStream(
					socketToAClient.getInputStream());
			PrintStream ps = new PrintStream(socketToAClient.getOutputStream());

			/*
			 * We will only block in read for this many milliseconds before we
			 * fail with java.io.InterruptedIOException, at which point we will
			 * abandon the connection.
			 */
			socketToAClient.setSoTimeout(Configuration.getTimeout());
			socketToAClient.setTcpNoDelay(true);

			SocketAddress sa = socketToAClient.getRemoteSocketAddress();
			if (!Configuration.getServerSecurityClientPattern().matcher(
					sa.toString()).matches()) {
				logger.info("Denying access for a client ip: " + sa);
				ps.print("550 " + " Will not communicate... ");
				ps.write(Mailer.EOL);
				ps.flush();
				socketToAClient.close();
				return;
			}

			/*
			 * Create ssh connection to ssh server.
			 */
			ssh = new Receiver(ps, this);
			ssh.start();

			clientInput = new BufferedReader(new InputStreamReader(is));

			/*
			 * While the client is connected keep the communication alive. When
			 * client disconnects, readLine throws an exception or isConnected()
			 * method returns false.
			 */
			while (socketToAClient.isConnected()) {
				/* client's input lines */
				String line = clientInput.readLine();

				if (line == null) {
					logger.debug("Client has closed input, sending EOL to ssh.");
					ssh.stdinWrite(Mailer.EOL);
					return;
				} else if (HELLO_PATT.matcher(line).matches()) {
					line = "HELO " + Configuration.getSshSmtpServerPretended();
				}
				logger.debug("Sending to ssh: " + line);
				ssh.stdinWrite(line + "\n");
			}
		} catch (IOException e) {
			logger.debug("Client session forcefully ended.");
			logger.debug(e);
		} finally {
			if (ssh != null) {
				logger.debug("Destroying ssh process.");
				ssh.kill();
			}
		}
	}
}
