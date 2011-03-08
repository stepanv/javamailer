package cz.csob.smtp.mailer;

/* An example of a very simple, multi-threaded HTTP server.
 * Implementation notes are in WebServer.html, and also
 * as comments in the source code.
 */
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import org.apache.log4j.Logger;

public class Mailer {
	/**
	 * We must use CRLF end-of-line endings for a network communication.
	 * 
	 * @see http://www.rfc-editor.org/EOLstory.txt
	 */
	public static final byte[] EOL = { (byte) '\r', (byte) '\n' };

	private static Logger logger = Logger.getLogger(Mailer.class.getName());

	static PrintStream log = null;

	/**
	 * Where worker threads stand idle
	 */
	private static Vector<Sender> workersIdlePool = new Vector<Sender>();

	/**
	 * In case that there isn't enough idle thread in the pool add worker to the
	 * pool.
	 * 
	 * @param worker
	 * @return indication whether the worker was or wasn't added to the idle
	 *         pool.
	 */
	public static boolean conditionallyAddWorkerToTheIdlePool(Sender worker) {
		synchronized (workersIdlePool) {
			if (workersIdlePool.size() >= Configuration.workers) {
				/* too many threads, exit this one */
				return false;
			} else {
				workersIdlePool.addElement(worker);
				return true;
			}
		}
	}

	/**
	 * Running threads
	 */
	private static Vector<Sender> workersRunning = new Vector<Sender>();

	public static void addRunningWorker(Sender worker) {
		synchronized (workersRunning) {
			workersRunning.add(worker);
		}
	}

	public static void removeRunningWorker(Sender worker) {
		synchronized (workersRunning) {
			workersRunning.remove(worker);
		}
	}

	static void printProps() {
		logger.info("timeout= " + Configuration.timeout);
		logger.info("workers= " + Configuration.workers);
		logger.info("port= " + Configuration.port);
	}

	private static boolean inShutdownHook = false;

	static {
		// Add the shutdown hook as new java thread to the runtime.
		// can be added as inner class or a separate class that implements
		// Runnable or extends Thread
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				logger.debug("in : run () : shutdownHook");

				inShutdownHook = true;
				synchronized (workersRunning) {
					for (int i = 0; i < workersRunning.size(); ++i) {
						Sender worker = workersRunning.elementAt(i);
						logger.info("Worker " + worker.getId() + "stopping...");
						worker.stopFast();
					}
				}

				logger.debug("Shutdown hook completed...");
			}
		});
	}

	/**
	 * Main mathod of Mailer program.
	 * 
	 * @param args
	 *            as program arguments
	 * @throws Exception
	 */
	public static void main(String[] args) {

		if (args.length > 0) {
			int port;
			try {
				port = Integer.parseInt(args[0]);
				Configuration.port = port;
			} catch (NumberFormatException e) {
				logger.error("Program port argument '" + args[0]
						+ "' not an integer");
			}
		}
		
		Configuration.touch();

		logger.info("Configuration file contains: "
				+ Configuration.currentConfiguration());

		/* start worker threads */
		for (int i = 0; i < Configuration.workers; ++i) {
			Sender w = new Sender();
			w.start();
			logger.info("worker thread " + w.getId() + " started");
			workersIdlePool.addElement(w);
		}

		try {

			/* create a socket on a specified port */
			logger.debug("creating socket at port: " + Configuration.port);
			ServerSocket serverSocket = new ServerSocket(Configuration.port);

			/* main program loop */
			while (true) {
				if (inShutdownHook) {
					return;
				}
				try {
					Socket socketToAClient = serverSocket.accept();

					Sender worker = null;
					synchronized (workersIdlePool) {
						if (workersIdlePool.isEmpty()) {
							worker = new Sender(socketToAClient);
							worker.start();
							logger.info("worker " + worker.getId() + " started");
						} else {
							worker = workersIdlePool.elementAt(0);
							workersIdlePool.removeElementAt(0);
							worker.setSocketToAClient(socketToAClient);
						}
					}
				} catch (IOException e) {
					logger.info("Communication with client was forcefully ended.");
				}
			}
		} catch (IOException e) {
			logger.error("Cannot open socket at port: " + Configuration.port);
		}

		logger.info("Mailer in shutdown");
	}
}
