package net.uvavru.smtp.mailer;

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

    /**
     * Where senders threads stand idle
     */
    private static Vector<Sender> sendersIdlePool = new Vector<Sender>();

    /**
     * In case that there isn't enough idle thread in the pool add sender to the
     * pool.
     * 
     * @param sender
     * @return indication whether the sender was or wasn't added to the idle
     *         pool.
     */
    public static boolean conditionallyAddSenderToTheIdlePool(Sender sender) {
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
    private static Vector<Sender> sendersRunning = new Vector<Sender>();

    public static void addRunningSender(Sender sender) {
        synchronized (sendersRunning) {
            sendersRunning.add(sender);
        }
    }

    public static void removeRunningSender(Sender sender) {
        synchronized (sendersRunning) {
            sendersRunning.remove(sender);
        }
    }

    public static int getSenderRunningCount() {
        synchronized (sendersRunning) {
            return sendersRunning.size();
        }
    }

    public static int getSenderIdlePoolCount() {
        synchronized (sendersIdlePool) {
            return sendersIdlePool.size();
        }
    }

    static void printProps() {
        logger.info("timeout= " + Configuration.getTimeout());
        logger.info("senders= " + Configuration.getSenders());
        logger.info("port= " + Configuration.getPort());
    }

    private boolean inShutdownHook = false;

    public Mailer() {
        // Add the shutdown hook as new java thread to the runtime.
        // can be added as inner class or a separate class that implements
        // Runnable or extends Thread
        
        final Mailer self = this;
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                logger.debug("in : run () : shutdownHook");
                self.stopAsync();
                logger.debug("Shutdown hook completed...");
            }
        });
    }

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
                logger.info("Sender " + sender.getId() + "stopping... (total: " + sendersRunning.size() + ")");
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
    
    private static Thread mailerThread;

    public void startAsync() {
        inShutdownHook = false;
        final Mailer mailer = this;
        mailerThread = new Thread(new Runnable() {

            public void run() {
                mailer.start();
            }
        }, "MailerMain");
        mailerThread.start();
    }
    
    public static boolean isRunning() {
        return mailerThread != null && mailerThread.isAlive();
    }

    ServerSocket serverSocket;

    public void start() {
        Configuration.touch();

        logger.info("Configuration file contains: "
                + Configuration.currentConfiguration());

        /* start sender threads */
        for (int i = 0; i < Configuration.getSenders(); ++i) {
            Sender sender = new Sender();
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
                            sender = new Sender(socketToAClient);
                            sender.start();
                            logger.info("sender " + sender.getId() + " started");
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

        new Mailer().start();

        logger.info("Mailer in shutdown");
    }
}
