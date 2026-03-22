/*
 * GWT super-source: com.watabou.utils.ThreadCompat stub for the HTML5 build.
 *
 * GWT does not emulate Object.wait() or Object.notifyAll() because it is
 * single-threaded.  This stub replaces the real ThreadCompat so that code
 * calling those methods on arbitrary objects (e.g. CharSprite, CharSprite
 * subclasses) compiles and does nothing at runtime.
 */
package com.watabou.utils;

/**
 * GWT no-op stub for {@link com.watabou.utils.ThreadCompat}.
 *
 * All methods are no-ops: GWT is single-threaded so monitor operations
 * (wait / notify) have no meaning.
 */
public class ThreadCompat {

	/** No-op in GWT (Object.wait() is not emulated). */
	public static void objectWait(Object obj) throws InterruptedException {
		// no-op
	}

	/** No-op in GWT (Object.notifyAll() is not emulated). */
	public static void objectNotifyAll(Object obj) {
		// no-op
	}

}
