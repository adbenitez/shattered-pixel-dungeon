/*
 * GWT super-source: java.text.NumberFormat stub for the HTML5 build.
 *
 * java.text.NumberFormat is not emulated by GWT.  The game uses it to
 * format score values (long/int) as locale-aware strings with grouping
 * separators (e.g. 1,234,567).  This stub provides a simple JavaScript-
 * backed implementation using toLocaleString() for grouping.
 */
package java.text;

import java.util.Locale;

/** Minimal GWT stub for {@code java.text.NumberFormat}. */
public abstract class NumberFormat {

    public static NumberFormat getInstance() {
        return new SimpleNumberFormat();
    }

    public static NumberFormat getInstance(Locale locale) {
        return new SimpleNumberFormat();
    }

    public static NumberFormat getIntegerInstance() {
        return new SimpleNumberFormat();
    }

    public abstract String format(long number);

    public abstract String format(double number);

    // -----------------------------------------------------------------------
    // Simple implementation that uses JS toLocaleString-style grouping
    // -----------------------------------------------------------------------

    private static class SimpleNumberFormat extends NumberFormat {

        @Override
        public String format(long number) {
            // JSNI cannot safely handle long; convert to double first.
            return formatDouble((double) number);
        }

        @Override
        public String format(double number) {
            return formatDouble(number);
        }

        private static native String formatDouble(double n) /*-{
            return n.toLocaleString();
        }-*/;
    }
}
