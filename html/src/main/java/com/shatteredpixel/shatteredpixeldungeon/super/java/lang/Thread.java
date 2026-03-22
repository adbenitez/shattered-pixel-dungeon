/*
 * GWT super-source: java.lang.Thread emulation for the HTML5 build.
 *
 * GWT is single-threaded.  This stub exposes the Thread API used by
 * Shattered Pixel Dungeon so that the code compiles.  All operations that
 * involve actual thread management are no-ops.
 *
 * This file supersedes the minimal Thread stub provided by gdx-backend-gwt
 * (which lacks currentThread(), interrupted(), start(), run(), isAlive(),
 * setName(), setPriority(), interrupt(), getStackTrace(), join(), and the
 * wait()/notify() monitor methods that Thread inherits from Object in a
 * normal JVM but which GWT's Object emulation does not include).
 */
package java.lang;

import com.google.gwt.core.client.GWT;

/** GWT single-threaded emulation of {@code java.lang.Thread}. */
public class Thread {

	public static final int MIN_PRIORITY  = 1;
	public static final int NORM_PRIORITY = 5;
	public static final int MAX_PRIORITY  = 10;

	private static final Thread MAIN_THREAD = new Thread("main");

	private String name;
	private int    priority = NORM_PRIORITY;

	public Thread() {
		this.name = "Thread";
	}

	public Thread(String name) {
		this.name = name;
	}

	// -----------------------------------------------------------------------
	// Static methods
	// -----------------------------------------------------------------------

	/** Returns a stub representing the current (only) thread. */
	public static Thread currentThread() {
		return MAIN_THREAD;
	}

	/** Always returns {@code false} in GWT (never interrupted). */
	public static boolean interrupted() {
		return false;
	}

	public static void sleep(long millis) throws InterruptedException {
		// no-op in GWT
	}

	public static void setDefaultUncaughtExceptionHandler(
			final UncaughtExceptionHandler javaHandler) {
		GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
			@Override
			public void onUncaughtException(Throwable e) {
				javaHandler.uncaughtException(MAIN_THREAD, e);
			}
		});
	}

	// -----------------------------------------------------------------------
	// Instance methods
	// -----------------------------------------------------------------------

	/** Entry point for anonymous Thread subclasses.  No-op by default. */
	public void run() {
		// subclasses override this
	}

	/** No-op in GWT: starts nothing.  Overrides must define run(). */
	public void start() {
		// no-op – GWT cannot start real threads
	}

	public boolean isAlive() {
		return false;
	}

	public boolean isInterrupted() {
		return false;
	}

	/** No-op in GWT. */
	public void interrupt() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public StackTraceElement[] getStackTrace() {
		return new StackTraceElement[0];
	}

	public void join() throws InterruptedException {
		// no-op in GWT
	}

	public void join(long millis) throws InterruptedException {
		// no-op in GWT
	}

	public void setDaemon(boolean on) {
	}

	public boolean isDaemon() {
		return false;
	}

	// -----------------------------------------------------------------------
	// UncaughtExceptionHandler interface
	// -----------------------------------------------------------------------

	public interface UncaughtExceptionHandler {
		void uncaughtException(Thread t, Throwable e);
	}
}
