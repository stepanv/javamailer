package net.uvavru.smtp.mailer.gui;

import javax.swing.SwingUtilities;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;

/**
 * Specialized Apache Log4j appender class designed to append logs into a Swing
 * console implemented by {@link MonitorPanel} class.
 * 
 * @author stepan
 * 
 */
public class AppenderLog4j extends AppenderSkeleton {

	private MonitorPanel panelMonitor;

	/**
	 * Creates instance of {@code AppenderLog4j} which
	 * requires {@link MonitorPanel} to be able to append
	 * message somewhere.
	 * 
	 * @param panelMonitor {@code MonitorPanel} to append msgs to
	 */
	public AppenderLog4j(MonitorPanel panelMonitor) {
		this.panelMonitor = panelMonitor;
	}

	@Override
	public void close() {

	}

	@Override
	public boolean requiresLayout() {
		return false;
	}

	/**
     * Console appender which is designated to append a text to
     * the console. It's purpose is to be able to correctly
     * change GUI so that it should be called using 
     * {@link SwingUtilities#invokeLater(Runnable)
     * 
     * @author stepan
     *
     */
	class ConsoleAppender implements Runnable {

		private String text;

		/**
		 * Creates {@code ConsoleAppender} with a {@code text} which should
		 * be written to {@link MonitorPanel}'s console.
		 * 
		 * @param text String to append.
		 */
		public ConsoleAppender(String text) {
			this.text = text;
		}

		@Override
		public void run() {
			panelMonitor.appendToConsole(text);

		}

	}

	@Override
	protected void append(LoggingEvent arg0) {
		synchronized (this) {
			/*
			 * New console appender is allocated everytime smth is appended to a
			 * console. This is a serious performance issue. Should be
			 * redesigned with a pool of ConsoleAppenders... But who cares?
			 */
		    
			SwingUtilities.invokeLater(new ConsoleAppender(arg0.getMessage().toString()));
		}
	}

}
