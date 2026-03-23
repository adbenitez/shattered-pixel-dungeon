/*
 * GWT super-source: java.util.UUID stub for the HTML5 build.
 *
 * java.util.UUID is not emulated by GWT.  The game uses only
 * UUID.randomUUID().toString() to assign a unique ID to saved games.
 * This stub generates a random UUID v4 string using Math.random().
 */
package java.util;

/** Minimal GWT stub for {@code java.util.UUID}. */
public final class UUID {

    private final String value;

    private UUID(String value) {
        this.value = value;
    }

    /**
     * Returns a random UUID v4 string of the form
     * {@code xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx}.
     */
    public static UUID randomUUID() {
        return new UUID(randomUUIDString());
    }

    private static native String randomUUIDString() /*-{
        // RFC-4122 version-4 UUID using Math.random()
        return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
            var r = Math.random() * 16 | 0;
            var v = c === 'x' ? r : (r & 0x3 | 0x8);
            return v.toString(16);
        });
    }-*/;

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof UUID)) return false;
        return value.equals(((UUID) obj).value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
