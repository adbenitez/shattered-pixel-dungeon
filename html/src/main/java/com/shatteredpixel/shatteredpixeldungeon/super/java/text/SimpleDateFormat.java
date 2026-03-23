/*
 * GWT super-source: java.text.SimpleDateFormat stub for the HTML5 build.
 *
 * java.text.SimpleDateFormat is not emulated by GWT.  The game uses it for
 * two patterns:
 *   "yyyy-MM-dd"   – used in Dungeon.java and WndDailies.java
 *   "HH:mm:ss"     – used in HeroSelectScene.java
 *
 * This stub handles both patterns using the browser Date object via JSNI.
 */
package java.text;

import java.util.Date;
import java.util.Locale;

/** Minimal GWT stub for {@code java.text.SimpleDateFormat}. */
public class SimpleDateFormat extends DateFormat {

    private final String pattern;

    public SimpleDateFormat(String pattern) {
        this.pattern = pattern;
    }

    public SimpleDateFormat(String pattern, Locale locale) {
        this.pattern = pattern;
    }

    @Override
    public String format(Date date) {
        // Use the millisecond timestamp; timeZone offset is always 0 for UTC.
        long ms = date.getTime();
        return formatNative(ms, pattern);
    }

    private static native String formatNative(double ms, String pattern) /*-{
        var d = new Date(ms);
        var y  = d.getUTCFullYear();
        var mo = d.getUTCMonth() + 1; // 0-indexed → 1-indexed
        var dd = d.getUTCDate();
        var hh = d.getUTCHours();
        var mm = d.getUTCMinutes();
        var ss = d.getUTCSeconds();

        function pad2(n) { return n < 10 ? '0' + n : '' + n; }

        var result = pattern;
        result = result.replace('yyyy', '' + y);
        result = result.replace('MM',   pad2(mo));
        result = result.replace('dd',   pad2(dd));
        result = result.replace('HH',   pad2(hh));
        result = result.replace('mm',   pad2(mm));
        result = result.replace('ss',   pad2(ss));
        return result;
    }-*/;
}
