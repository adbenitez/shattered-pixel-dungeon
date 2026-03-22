/*
 * GWT super-source: java.lang.Runtime emulation for the HTML5 build.
 *
 * GWT does not emulate java.lang.Runtime.  This stub provides the small
 * subset used by GameScene.java (availableProcessors) so that the code
 * compiles.  On GWT/WebGL the browser is treated as a single-core
 * environment to avoid any accidental thread-priority logic.
 */
package java.lang;

/** Minimal GWT-compatible stub for {@code java.lang.Runtime}. */
public class Runtime {

private static final Runtime INSTANCE;

static {
INSTANCE = new Runtime();
}

Runtime() {
}

public static Runtime getRuntime() {
return INSTANCE;
}

/** Always returns 1 in GWT (single-threaded environment). */
public int availableProcessors() {
return 1;
}

/** No-op in GWT. */
public void gc() {
}

public long totalMemory() {
return 0;
}

public long freeMemory() {
return 0;
}

public long maxMemory() {
return Long.MAX_VALUE;
}
}
