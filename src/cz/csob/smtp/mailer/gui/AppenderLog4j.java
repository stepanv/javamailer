package cz.csob.smtp.mailer.gui;

import javax.swing.SwingUtilities;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;

public class AppenderLog4j extends AppenderSkeleton {

    private StringBuffer buffer = new StringBuffer();

    public static AppenderLog4j APPENDER;

    public AppenderLog4j() {
        APPENDER = this;
    }

    public StringBuffer getBuffer() {
        return buffer;
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
        
        public ConsoleAppender(String text) {
            this.text = text;
        }
        @Override
        public void run() {
            MonitorPanel.appendToConsole(text);
            
        }
        
    }

    @Override
    protected void append(LoggingEvent arg0) {
        synchronized (this) {
            buffer.append(arg0.getMessage());
            SwingUtilities.invokeLater(new ConsoleAppender(arg0.getMessage().toString()));
        }
    }

}
