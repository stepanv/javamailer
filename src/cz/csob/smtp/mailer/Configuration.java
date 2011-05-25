package cz.csob.smtp.mailer;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.regex.Pattern;


public class Configuration {
	public static PropertyResourceBundle props;

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
        return getDynamicProperty("ssh.host", "localhost");
    }

    public static int getSenders() {
        return Integer.parseInt(getDynamicProperty("server.threads", "5"));
    }

    public static int getTimeout() {
        return Integer.parseInt(getDynamicProperty("server.timeout", "10000"));
    }

    public static int getPort() {
        return Integer.parseInt(getDynamicProperty("server.port", "25"));
    }

    public static String getLog() {
        return getDynamicProperty("server.log", null);
    }

    public static String getSshProgram() {
        return getDynamicProperty("ssh.program", "ssh");
    }

    public static String getSshAddparams() {
        return getDynamicProperty("ssh.addparams", "");
    }

    public static String getSshUser() {
        return getDynamicProperty("ssh.user", "user");
    }

    public static String getSshSmtpHost() {
        return getDynamicProperty("ssh.smtp.host", "localhost");
    }

    public static String getSshSmtpPort() {
        return getDynamicProperty("ssh.smtp.port", "25");
    }

    public static String getSshSmtpServerPretended() {
        return getDynamicProperty("ssh.smtp.serverpretended", "localhost");
    }

    public static Pattern getServerSecurityClientPattern() {
        return Pattern.compile(getDynamicProperty("server.security.clientpattern", ".*"));
    }
}