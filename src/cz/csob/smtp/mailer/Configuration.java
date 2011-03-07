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
	public static final int workers = Integer.parseInt(getValueFromProperty("server.workers", "5"));
	public static final int timeout = Integer.parseInt(getValueFromProperty("server.timeout", "10000"));
	public static int port = Integer.parseInt(getValueFromProperty("server.port", "25"));
	
	public static final String log = getValueFromProperty("server.log", null);
	public static final String sshHost = getValueFromProperty("ssh.host", "localhost");
	public static final String sshProgram = getValueFromProperty("ssh.program", "ssh");
	public static final String sshAddparams = getValueFromProperty("ssh.addparams", "");
	public static final String sshUser = getValueFromProperty("ssh.user", "user");
	public static final String sshSmtpHost = getValueFromProperty("ssh.smtp.host", "localhost");
	public static final String sshSmtpPort = getValueFromProperty("ssh.smtp.port", "25");
	public static final String sshSmtpServerPretended = getValueFromProperty("ssh.smtp.serverpretended", "localhost");
	
	public static final Pattern serverSecurityClientPattern = Pattern.compile(getValueFromProperty("server.security.clientpattern", ".*"));

	protected static String getValueFromProperty(String key) {
		return getValueFromProperty(key, "");
	}

	private static String getValueFromProperty(String key, String defaultValue) {
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
			sb.append(props.getString(key));
		}
		
		return sb.toString();		
	}

	/**
	 * Touch config class. This causes static initialization.
	 */
	public static void touch() {			
	}
}