package cz.csob.smtp.mailer;
/* An example of a very simple, multi-threaded HTTP server.
 * Implementation notes are in WebServer.html, and also
 * as comments in the source code.
 */
import java.io.*;
import java.net.*;
import java.util.*;

public class Mailer {

	/* static class data/methods */

	/* print to stdout */
	protected static void p(String s) {
		System.out.println(s);
	}

	/* print to the log file */
	protected static void log(String s) {
		synchronized (log) {
			log.println(s);
			log.flush();
		}
	}

	static PrintStream log = null;

	/* Where worker threads stand idle */
	static Vector threads = new Vector();
	
	/* Running threads */
//	static Vector threadsRunning = new Vector();

	/* load www-server.properties from java.home */
	static void loadProps() throws IOException {
		if (Configuration.log != null) {
			p("opening log file: " + Configuration.log);
			log = new PrintStream(new BufferedOutputStream(
					new FileOutputStream(Configuration.log)));
		} else {
			p("logging to stdout");
			log = System.out;
		}
	}

	static void printProps() {
		p("timeout= " + Configuration.timeout);
		p("workers= " + Configuration.workers);
		p("port= " + Configuration.port);
	}
	
//	private static boolean inShutdownHook = false; 
	
//	static {
//		// Add the shutdown hook as new java thread to the runtime.
//		// can be added as inner class or a separate class that implements
//		// Runnable or extends Thread
//		Runtime.getRuntime().addShutdownHook(new Thread() {
//			public void run() {
//				p("in : run () : shutdownHook");
//				
//				inShutdownHook = true;
//					for (int i = 0; i < threadsRunning.size(); ++i) {
//						Object worker = threadsRunning.elementAt(i);
//						if (worker instanceof Worker) {
//							((Worker)worker).stop();
//							p("worker stopped...");
//							
//						};
//					}
//					
//				p("Shutdown hook completed...");
//			}
//		});
//	}

	public static void main(String[] a) throws Exception {
		int port = Configuration.port;
		if (a.length > 0) {
			port = Integer.parseInt(a[0]);
		}
		loadProps();
		printProps();
		
		/* start worker threads */
		for (int i = 0; i < Configuration.workers; ++i) {
			Worker w = new Worker();
			(new Thread(w, "worker #" + i)).start();
			threads.addElement(w);
		}

		ServerSocket ss = new ServerSocket(port);
		while (true) {
//			if (inShutdownHook) {
//				return;
//			}
			Socket s = ss.accept();

			Worker w = null;
			synchronized (threads) {
				if (threads.isEmpty()) {
					w = new Worker();
					w.setSocket(s);
					(new Thread(w, "additional worker")).start();
				} else {
					w = (Worker) threads.elementAt(0);
					threads.removeElementAt(0);
					w.setSocket(s);
				}
			}
		}
	}
}



class Worker extends Mailer implements Runnable {
	final static int BUF_SIZE = 2048;

	static final byte[] EOL = { (byte) '\r', (byte) '\n' };

	/* buffer to use for requests */
	byte[] buf;

	/* Socket to client we're handling */
	private Socket s;
	
	/* Connection to dev */
	ConnectionToDev ssh;
	
	BufferedReader input;

	Worker() {
		buf = new byte[BUF_SIZE];
		s = null;
	}

	synchronized void setSocket(Socket s) {
		this.s = s;
		notify();
	}
	
	public void stop() {
		try {
//			synchronized (ssh) {
				ssh.stdinWrite("QUIT\n");
//			}
			p("sent QUIT to remote telnet instance...");
		} catch (Exception e) {
			p("sent QUIT to remote telnet instance... NOK");
		}
		try {
			input.close();
			p("closed input stream...");
		} catch (Exception e) {
			p("closed input stream... NOK");
		}
		try {
			ssh.process.destroy();
			p("Destroyed ssh process...");
		} catch (Exception e) {
			p("Destroyed ssh process... NOK");
		}
		try {
			s.close();
			p("Socket closed...");
		} catch (Exception e) {
			p("Socket closed... NOK");
		}
	}

	public synchronized void run() {
//		try {
//			synchronized (threadsRunning) {
//				threadsRunning.add(this);
//			}
			
			while (true) {
				if (s == null) {
					/* nothing to do */
					try {
						wait();
					} catch (InterruptedException e) {
						/* should not happen */
						continue;
					}
				}
				try {
					handleClient();
				} catch (Exception e) {
					e.printStackTrace();
				}
				/*
				 * go back in wait queue if there's fewer than numHandler
				 * connections.
				 */
				s = null;
				Vector pool = Mailer.threads;
				synchronized (pool) {
					if (pool.size() >= Configuration.workers) {
						/* too many threads, exit this one */
						return;
					} else {
						pool.addElement(this);
					}
				}
			}
//		} finally {
//			synchronized (threadsRunning) {
//				threadsRunning.remove(this);
//			}
//		}
	}

	void handleClient() throws IOException {
		InputStream is = new BufferedInputStream(s.getInputStream());
		PrintStream ps = new PrintStream(s.getOutputStream());
		/*
		 * we will only block in read for this many milliseconds before we fail
		 * with java.io.InterruptedIOException, at which point we will abandon
		 * the connection.
		 */
		s.setSoTimeout(Configuration.timeout);
		s.setTcpNoDelay(true);
		
		SocketAddress sa = s.getRemoteSocketAddress();
		if (!sa.toString().matches(".*127\\.0\\.0\\.1:[0-9]{1,5}")) {
			ps.print("550 " + " Will not communicate... ");
			ps.write(EOL);
			ps.flush();
			s.close();
			return;
		}
		
		/* execute ssh connection */
		ssh = ConnectionToDev.factoryConnectionToDev(ps);
		ssh.exec();
		input = new BufferedReader(new InputStreamReader(is));
		try {
			String line;
			while (s.isConnected()) {
				line = input.readLine();
				
				if (line == null) {
					return;
				} else if (line.matches("[HE][EH]LO .*")) {
					line = "HELO " + Configuration.sshSmtpServerPretended;
				}
				synchronized (ssh) {
					p("sending to ssh: " + line);
					ssh.stdinWrite(line + "\n");
				}
			}
		
		} finally {
			stop();
		}
	}
}

