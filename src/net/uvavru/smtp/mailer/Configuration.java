package net.uvavru.smtp.mailer;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.regex.Pattern;


public class Configuration {
	public static PropertyResourceBundle props;

	public static final String SERVER_THREADS = "server.threads";                                                                                    
	public static final String SERVER_TIMEOUT = "server.timeout";                                                                                    
	public static final String SERVER_LOG = "server.log";                                                                                            
	public static final String SERVER_PORT = "server.port";    
	public static final String SERVER_SECURITYCLIENTPATTERN = "server.securityClientPattern";
	public static final String SSH_USER = "ssh.user";                                                                                                
	public static final String SSH_HOST = "ssh.host";                                                                                                
	public static final String SSH_EXECUTABLE = "ssh.executable";                                                                                          
	public static final String SSH_ADDPARAMS = "ssh.addparams";                                                                                      
	public static final String SSH_SMTP_HOST = "ssh.smtp.host";                                                                                      
	public static final String SSH_SMTP_PORT = "ssh.smtp.port";                                                                                      
	public static final String SSH_SMTP_SERVERPRETENDED = "ssh.smtp.serverpretended";                                                                
	public static final String LOG4J_ROOTLOGGER = "log4j.rootLogger";                                                                                
	public static final String LOG4J_APPENDER_A1 = "log4j.appender.A1";                                                                              
	public static final String LOG4J_APPENDER_A1_LAYOUT = "log4j.appender.A1.layout";                                                                
	public static final String LOG4J_APPENDER_A1_LAYOUT_CONVERSIONPATTERN = "log4j.appender.A1.layout.ConversionPattern";
	
	static {
		// Read the property file
		props = (PropertyResourceBundle) ResourceBundle.getBundle("mailer");
	}

	private static String getBundleProperty(String key, String defaultValue) {
		String value = null;
		try {
			value = props.getString(key).trim();
		} catch (Exception e) {
			value = defaultValue;
		}
		return value;
	}
	
	/**
	 * Well formatted configuration prepared for output to a log.
	 * 
	 * @return formatted configuration
	 */
	public static String currentConfiguration() {
		StringBuilder sb = new StringBuilder();
		
		for (String key : props.keySet()) {
			sb.append("\n");
			sb.append(key.toString());
			sb.append("=");
			sb.append(System.getProperty(key.toString(), props.getString(key)));
		}
		
		return sb.toString();		
	}

	/**
	 * Touch config class. This causes static initialization.
	 */
	public static void touch() {			
	}
	
	private static String getDynamicProperty(String propertyName, String defaultValue) {
	    return System.getProperty(propertyName, getBundleProperty(propertyName, defaultValue));
	}

    public static String getSshHost() {
        return getDynamicProperty(SSH_HOST, "localhost");
    }

    public static int getSenders() {
        return Integer.parseInt(getDynamicProperty(SERVER_THREADS, "5"));
    }

    public static int getTimeout() {
        return Integer.parseInt(getDynamicProperty(SERVER_TIMEOUT, "10000"));
    }

    public static int getPort() {
        return Integer.parseInt(getDynamicProperty(SERVER_PORT, "25"));
    }

    public static String getLog() {
        return getDynamicProperty(SERVER_LOG, null);
    }

    public static String getSshProgram() {
        return getDynamicProperty(SSH_EXECUTABLE, "ssh");
    }

    public static String getSshAddparams() {
        return getDynamicProperty(SSH_ADDPARAMS, "");
    }

    public static String getSshUser() {
        return getDynamicProperty(SSH_USER, "user");
    }

    public static String getSshSmtpHost() {
        return getDynamicProperty(SSH_SMTP_HOST, "localhost");
    }

    public static String getSshSmtpPort() {
        return getDynamicProperty(SSH_SMTP_PORT, "25");
    }

    public static String getSshSmtpServerPretended() {
        return getDynamicProperty(SSH_SMTP_SERVERPRETENDED, "localhost");
    }

    public static Pattern getServerSecurityClientPattern() {
        return Pattern.compile(getDynamicProperty(SERVER_SECURITYCLIENTPATTERN, ".*"));
    }
}