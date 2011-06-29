package net.uvavru.smtp.mailer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

import org.apache.log4j.Logger;

/**
 * The {@code PipedProcess} class connects streams to process's {@code stdin},
 * {@code stdout}, {@code stderr} so that any other thread is able to
 * communicate with the process via those streams.
 */
public class PipedProcess {

	/**
	 * Input stream of the process. This delivers created output data to the
	 * input of the process.
	 */
	private OutputStream stdin = null;

	private static Logger logger = Logger.getLogger(PipedProcess.class
			.getName());

	/**
	 * Standard (buffered) output of the process.
	 */
	private BufferedReader stdoutBuffered;

	/**
	 * Error (buffered) output of the process.
	 */
	private BufferedReader stderrBuffered;

	/**
	 * The process itself.
	 */
	private Process process;

	/**
	 * Command line of the process it was executed by.
	 */
	private String commandLine;

	public PipedProcess(String commandLine) {
		this.commandLine = commandLine;
	}

	/**
	 * Message written to the {@code stdin} of the process.
	 * 
	 * @param str
	 *            String to write
	 * @throws IOException
	 *             in case of IO failure.
	 */
	public void stdinWrite(String str) throws IOException {
		stdin.write(str.getBytes());
		stdin.flush();
	}

	/**
	 * Message written to the {@code stdin} of the process.
	 * 
	 * @param bytes
	 *            Array of bytes to write
	 * @throws IOException
	 *             in case of IO failure
	 */
	public void stdinWrite(byte[] bytes) throws IOException {
		stdin.write(bytes);
		stdin.flush();
	}

	/**
	 * Get one line from the process's standard output.
	 * 
	 * @return a line
	 * @throws IOException
	 *             in case of IO failure
	 */
	public String outputLine() throws IOException {
		return stdoutBuffered.readLine();
	}

	/**
	 * Get one line from the process's error output.
	 * 
	 * @return a line
	 * @throws IOException
	 *             in case of IO failure
	 */
	public String errorLine() throws IOException {
		return stderrBuffered.readLine();
	}

	/**
	 * Kills the process.
	 */
	public void destroy() {
		if (process != null) {
			process.destroy();
		}
	}

	/**
	 * Execute process with the command line specified.
	 * 
	 * @throws IOException
	 *             when exec() throws an IOException
	 */
	public void exec() throws IOException {
		// launch EXE and grab stdin/stdout and stderr
		logger.debug("executing: " + commandLine);
		process = Runtime.getRuntime().exec(commandLine);
		stdin = process.getOutputStream();

		// clean up if any output in stderr
		stdoutBuffered = new BufferedReader(new InputStreamReader(process
				.getInputStream()));
		stderrBuffered = new BufferedReader(new InputStreamReader(process
				.getErrorStream()));
	}
}
