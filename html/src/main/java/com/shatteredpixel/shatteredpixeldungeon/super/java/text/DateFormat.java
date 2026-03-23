/*
 * GWT super-source: java.text.DateFormat stub for the HTML5 build.
 *
 * java.text.DateFormat is not emulated by GWT.  Only the methods actually
 * called by the game (setTimeZone, format) are implemented.
 */
package java.text;

import java.util.Date;
import java.util.TimeZone;

/** Minimal GWT stub for {@code java.text.DateFormat}. */
public abstract class DateFormat {

    /** The time zone to use when formatting.  UTC by default. */
    protected TimeZone timeZone = TimeZone.getTimeZone("UTC");

    public void setTimeZone(TimeZone zone) {
        this.timeZone = zone;
    }

    public TimeZone getTimeZone() {
        return timeZone;
    }

    public abstract String format(Date date);
}
