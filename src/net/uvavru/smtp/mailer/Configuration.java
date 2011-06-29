package net.uvavru.smtp.mailer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

/**
 * Configuration class. It is possible to have properties
 * <code>mailer.properties<code> file if it 
 * is on classpath. If no such a file is provided, default values are used.
 * 
 * @author stepan
 * 
 */
public class Configuration {
	private static PropertyResourceBundle props;

	private static Map<String, String> defaults = new HashMap<String, String>();

	public static final String SERVER_THREADS = "server.threads";
	public static final String SERVER_TIMEOUT = "server.timeout";
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
		defaults.put(SERVER_THREADS, "5");
		defaults.put(SERVER_TIMEOUT, "10000");
		defaults.put(SERVER_PORT, "25");
		defaults.put(SSH_EXECUTABLE, "ssh");
		defaults.put(SSH_ADDPARAMS, "");
		defaults.put(SSH_USER, "user");
		defaults.put(SSH_HOST, "localhost");
		defaults.put(SSH_SMTP_HOST, "localhost");
		defaults.put(SSH_SMTP_PORT, "25");
		defaults.put(SSH_SMTP_SERVERPRETENDED, "localhost");
		defaults.put(SERVER_SECURITYCLIENTPATTERN, ".*");

		props = (PropertyResourceBundle) ResourceBundle.getBundle("mailer");

	}

	/**
	 * Gets property from {@code mailer.properties} configuration file.
	 * 
	 * @param key
	 *            Property name
	 * @param defaultValue
	 *            Default value for a property if there is not such one
	 * @return Property value
	 */
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

		for (String key : defaults.keySet()) {
			sb.append("\n").append(key.toString()).append("=");
			sb.append(getProperty(key.toString()));
		}

		return sb.toString();
	}

	/**
	 * Touch config class. This causes static initialization.
	 */
	public static void touch() {
	}

	/**
	 * Get property for {@code propertyName}. In case that there is no
	 * definition for the @ propertyName} in System properties or than in
	 * property file {@code mailer.properties}, {@code defaultValue} is used.
	 * 
	 * @param propertyName
	 *            Name of the property
	 * @param defaultValue
	 *            Default value of property if there no such a property exists
	 * @return Property value
	 */
	public static String getDynamicProperty(String propertyName,
			String defaultValue) {
		return System.getProperty(propertyName, getBundleProperty(propertyName,
				defaultValue));
	}

	/**
	 * Get property for {@code propertyName}. System properties as well as
	 * configuration file is looked in.
	 * 
	 * @param propertyName
	 *            Name of the property
	 * @return Property value
	 */
	public static String getProperty(String propertyName) {
		return getDynamicProperty(propertyName, defaults.get(propertyName));
	}

	public static String getSshHost() {
		return getProperty(SSH_HOST);
	}

	public static int getSenders() {
		return Integer.parseInt(getProperty(SERVER_THREADS));
	}

	public static int getTimeout() {
		return Integer.parseInt(getProperty(SERVER_TIMEOUT));
	}

	public static int getPort() {
		return Integer.parseInt(getProperty(SERVER_PORT));
	}

	public static String getSshProgram() {
		return getProperty(SSH_EXECUTABLE);
	}

	public static String getSshAddparams() {
		return getProperty(SSH_ADDPARAMS);
	}

	public static String getSshUser() {
		return getProperty(SSH_USER);
	}

	public static String getSshSmtpHost() {
		return getProperty(SSH_SMTP_HOST);
	}

	public static String getSshSmtpPort() {
		return getProperty(SSH_SMTP_PORT);
	}

	public static String getSshSmtpServerPretended() {
		return getProperty(SSH_SMTP_SERVERPRETENDED);
	}

	public static Pattern getServerSecurityClientPattern() {
		return Pattern.compile(getProperty(SERVER_SECURITYCLIENTPATTERN));
	}
	
	public static void write(File file) throws IOException {
	    BufferedWriter bw = new BufferedWriter(new FileWriter(file));
	    for (Entry<String, String> entry : defaults.entrySet()) {
	        bw.write(entry.getKey() + " = " + getProperty(entry.getKey()) + System.getProperty("line.separator"));
	    }
	    bw.close();
	}
}