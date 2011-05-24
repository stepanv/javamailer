package cz.csob.smtp.mailer.gui;

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

    @Override
    protected void append(LoggingEvent arg0) {
        synchronized (this) {
            buffer.append(arg0.getMessage());
        }
    }

}
