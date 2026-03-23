/*
 * GWT super-source: no-op replacement for ThreadCompat in the HTML5 build.
 *
 * GWT is single-threaded and does not emulate Object.wait() or
 * Object.notifyAll().  This super-source stub replaces the real
 * ThreadCompat (which calls those methods) with no-ops so that the GWT
 * compiler can compile the code without errors.
 *
 * All callers of these methods in the game are inside synchronized blocks
 * that are themselves no-ops in GWT, so removing the wait/notify calls is
 * safe for the single-threaded HTML5 build.
 */
package com.watabou.utils;

/** GWT no-op stub for {@link com.watabou.utils.ThreadCompat}. */
public class ThreadCompat {

	/** No-op in GWT: Object.wait() does not exist in the GWT Object emulation. */
	public static void objectWait(Object obj) throws InterruptedException {
		// no-op in GWT
	}

	/** No-op in GWT: Object.notifyAll() does not exist in the GWT Object emulation. */
	public static void objectNotifyAll(Object obj) {
		// no-op in GWT
	}

}
