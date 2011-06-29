package net.uvavru.smtp.mailer;

/**
 * Mailer main class. {@code Mailer} is a simple server running with one main
 * thread which listens on a specified port. In case that client connects all
 * communication is maintained by {@link Sender} thread than so that {@code
 * Mailer} can listen on the specified port again.
 * 
 * @author stepan
 * 
 */
public interface Mailer {
	/**
	 * We must use CRLF end-of-line endings for a network communication.
	 * 
	 * @see http://www.rfc-editor.org/EOLstory.txt
	 */
	public static final byte[] EOL = { (byte) '\r', (byte) '\n' };

	/**
	 * Print properties used in {@code Mailer}.
	 */
	void printProps();

	/**
	 * Add instance of {@link Sender} to a list of running threads. E.g.
	 * {@link Thread#start()} was executed but the thread might
	 * {@link Thread#wait()} if there is not a client connected.
	 * 
	 * @param sender
	 *            {@link Sender} to add
	 */
	public void addRunningSender(Sender sender);

	/**
	 * In case that there isn't enough idle thread in the pool add sender to the
	 * pool.
	 * 
	 * @param sender
	 * @return indication whether the sender was or wasn't added to the idle
	 *         pool.
	 */
	public boolean conditionallyAddSenderToTheIdlePool(Sender sender);

	/**
	 * Count of threads in an idle pool. E.g. the thread is running but it is
	 * not handling a client.
	 * 
	 * @return Count of threads
	 */
	public int getSenderIdlePoolCount();

	/**
	 * Total thread count allocated and running for client handling. Includes
	 * threads from idle pool as well as threads which are handling the client.
	 * 
	 * @return Total thread count
	 */
	public int getSenderRunningCount();

	/**
	 * Whether Mailer main thread is running. (Main thread listens on the
	 * specified port.)
	 * 
	 * @return {@code true} if {@link Mailer} instance is running.
	 */
	public boolean isRunning();

	/**
	 * Remove {@link Sender} instance from the running list.
	 * 
	 * @param sender
	 *            A Sender instance
	 */
	public void removeRunningSender(Sender sender);

	/**
	 * Starts mailer without dedicated special thread thus this call is
	 * synchronous.
	 */
	public void start();

	/**
	 * Starts mailer in a separate thread. This routine is asynchronous.
	 */
	public void startAsync();

	/**
	 * Forcefully stops {@link Mailer}'s main thread executed by
	 * {@link #startAsync()} method.
	 */
	public void stopAsync();
}
