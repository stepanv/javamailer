package cz.csob.smtp.mailer;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;


public class Configuration {
	public static PropertyResourceBundle props;

	static {
		// Read the property file
		props = (PropertyResourceBundle) ResourceBundle.getBundle("mailer");
	}
	public static final int workers = Integer.parseInt(getValueFromProperty("server.workers", "5"));
	public static final int timeout = Integer.parseInt(getValueFromProperty("server.timeout", "10000"));
	public static final String log = getValueFromProperty("server.log", null);
	public static final int port = Integer.parseInt(getValueFromProperty("server.port", "4000"));
	
	public static final String sshHost = getValueFromProperty("ssh.host", "193.244.45.216");
	public static final String sshUser = getValueFromProperty("ssh.user", "csobont");
	public static final String sshSmtpHost = getValueFromProperty("ssh.smtp.host", "amail1.servers.kbc.be");
	public static final String sshSmtpPort = getValueFromProperty("ssh.smtp.port", "25");
	
	public static final String sshSmtpServerPretended = getValueFromProperty("ssh.smtp.serverpretended", "s2010240.servers.kbct.be");

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
}