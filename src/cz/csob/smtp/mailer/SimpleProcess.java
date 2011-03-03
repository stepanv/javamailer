package cz.csob.smtp.mailer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class SimpleProcess {
	OutputStream stdin = null;

	InputStream stderr = null;

	InputStream stdout = null;

	public Process process;

	BufferedReader outputReader;

	BufferedReader errorReader;
	
	String commandLine;
	
	public SimpleProcess(String commandLine) {
		// TODO Auto-generated constructor stub
		this.commandLine = commandLine;
	}

	public void stdinWrite(String str) throws IOException {
		stdin.write(str.getBytes());
		stdin.flush();
	}

	public String outputLine() throws IOException {
		return outputReader.readLine();
	}

	public String errorLine() throws IOException {
		return errorReader.readLine();
	}

	public int waitFor() throws InterruptedException {
		return process.waitFor();
	}

	void exec() {
		try {
			// launch EXE and grab stdin/stdout and stderr
			process = Runtime.getRuntime()
					.exec(commandLine);
			stdin = process.getOutputStream();
			stderr = process.getErrorStream();
			stdout = process.getInputStream();

			// clean up if any output in stderr
			outputReader = new BufferedReader(new InputStreamReader(stdout));
			errorReader = new BufferedReader(new InputStreamReader(stderr));

		} catch (Exception err) {
			err.printStackTrace();
		}
	}
}
