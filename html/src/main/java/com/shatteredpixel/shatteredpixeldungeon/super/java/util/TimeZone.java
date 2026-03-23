/*
 * GWT super-source: java.util.TimeZone stub for the HTML5 build.
 *
 * java.util.TimeZone is not emulated by GWT.  The game uses it only to set
 * UTC on SimpleDateFormat instances.  The stub stores the ID and always
 * reports a zero offset (UTC semantics), which is what the JSNI formatters
 * in SimpleDateFormat already assume.
 */
package java.util;

/** Minimal GWT stub for {@code java.util.TimeZone}. */
public class TimeZone {

    private final String id;

    private TimeZone(String id) {
        this.id = id;
    }

    /** Returns a stub TimeZone for the given ID.  Only "UTC" is meaningful. */
    public static TimeZone getTimeZone(String id) {
        return new TimeZone(id);
    }

    public String getID() {
        return id;
    }

    /** Always returns 0 – SimpleDateFormat uses UTC via JSNI. */
    public int getOffset(long date) {
        return 0;
    }

    public int getRawOffset() {
        return 0;
    }
}
