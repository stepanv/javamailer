package net.uvavru.smtp.mailer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import org.apache.log4j.Logger;

/**
 * The {@link Mailer} interface implementation.
 * 
 * @author stepan
 *
 */
public class MailerImpl implements Mailer {
	private static Logger logger = Logger.getLogger(Mailer.class.getName());

	/**
	 * Where senders threads stand idle
	 */
	private Vector<Sender> sendersIdlePool = new Vector<Sender>();

	@Override
	public boolean conditionallyAddSenderToTheIdlePool(Sender sender) {
	    if (!isRunning()) {
	        return false;
	    }
		synchronized (sendersIdlePool) {
			if (sendersIdlePool.size() >= Configuration.getSenders()) {
				/* too many threads, exit this one */
				return false;
			} else {
				sendersIdlePool.addElement(sender);
				return true;
			}
		}
	}

	/**
	 * Running threads
	 */
	private Vector<Sender> sendersRunning = new Vector<Sender>();

	@Override
	public void addRunningSender(Sender sender) {
		synchronized (sendersRunning) {
			sendersRunning.add(sender);
		}
	}

	@Override
	public void removeRunningSender(Sender sender) {
		synchronized (sendersRunning) {
			sendersRunning.remove(sender);
		}
	}

	@Override
	public int getSenderRunningCount() {
		synchronized (sendersRunning) {
			return sendersRunning.size();
		}
	}

	@Override
	public int getSenderIdlePoolCount() {
		synchronized (sendersIdlePool) {
			return sendersIdlePool.size();
		}
	}

	@Override
	public void printProps() {
		logger.info("timeout= " + Configuration.getTimeout());
		logger.info("senders= " + Configuration.getSenders());
		logger.info("port= " + Configuration.getPort());
	}

	private boolean inShutdownHook = false;

	/**
	 * Creates {@link Mailer} instance.
	 */
	public MailerImpl() {
		final Mailer self = this;
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				logger.debug("in : run () : shutdownHook");
				self.stopAsync();
				logger.debug("Shutdown hook completed...");
			}
		});
	}

	@Override
	public void stopAsync() {
		inShutdownHook = true;

		try {
			if (serverSocket != null) {
				serverSocket.close();
			}
		} catch (IOException e) {
			logger.debug(e);
		}

		synchronized (sendersRunning) {
			for (int i = 0; i < sendersRunning.size(); ++i) {
				Sender sender = sendersRunning.elementAt(i);
				logger.info("Sender " + sender.getId() + "stopping... (total: "
						+ sendersRunning.size() + ")");
				sender.stopFast();
			}
		}

		synchronized (sendersIdlePool) {
			for (Sender sender : sendersIdlePool) {
				sender.stopFast();
			}
			sendersIdlePool.clear();
		}
	}

	private Thread mailerThread;

	@Override
	public void startAsync() {
		inShutdownHook = false;
		mailerThread = new Thread(new Runnable() {

			public void run() {
				MailerImpl.this.start();
			}
		}, "MailerMain");
		mailerThread.start();
	}

	@Override
	public boolean isRunning() {
		return mailerThread != null && mailerThread.isAlive();
	}

	ServerSocket serverSocket;

	@Override
	public void start() {
		Configuration.touch();

		logger.info("Configuration file contains: "
				+ Configuration.currentConfiguration());

		/* start sender threads */
		for (int i = 0; i < Configuration.getSenders(); ++i) {
			Sender sender = new Sender(this);
			sender.start();
			logger.info("sender thread " + sender.getId() + " started");
			sendersIdlePool.addElement(sender);
		}

		try {

			/* create a socket on a specified port */
			logger.debug("creating socket at port: " + Configuration.getPort());
			serverSocket = new ServerSocket(Configuration.getPort());

			/* main program loop */
			while (true) {
				if (inShutdownHook) {
					return;
				}
				try {
					Socket socketToAClient = serverSocket.accept();

					Sender sender = null;
					synchronized (sendersIdlePool) {
						if (sendersIdlePool.isEmpty()) {
							sender = new Sender(socketToAClient, this);
							sender.start();
							logger
									.debug("sender " + sender.getId()
											+ " started");
						} else {
							sender = sendersIdlePool.elementAt(0);
							sendersIdlePool.removeElementAt(0);
							sender.setSocketToAClient(socketToAClient);
						}
					}
				} catch (IOException e) {
					logger.info("Listening on port " + Configuration.getPort()
							+ " was forcefully ended.");
				}
			}
		} catch (IOException e) {
			logger.error("Cannot open socket at port: "
					+ Configuration.getPort());
		} finally {
			stopAsync();
		}
		logger.debug("Mailer main thread ending");
	}

	/**
	 * Main mathod of Mailer program.
	 * 
	 * @param args
	 *            as program arguments
	 * @throws Exception
	 */
	public static void main(String[] args) {

		new MailerImpl().start();

		logger.info("Mailer in shutdown");
	}
}
