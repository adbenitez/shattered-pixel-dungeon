/*
 * GWT super-source: java.text.DecimalFormat stub for the HTML5 build.
 *
 * The game uses DecimalFormat only via Messages.decimalFormat(), which formats
 * a double using a pattern string (e.g. "#.##").  This stub delegates to the
 * same formatFixedDouble logic already present in Messages.java by using
 * JavaScript's toFixed() approximation via JSNI.
 */
package java.text;

/** Minimal GWT stub for {@code java.text.DecimalFormat}. */
public class DecimalFormat extends NumberFormat {

    private final String pattern;

    public DecimalFormat(String pattern) {
        this.pattern = pattern;
    }

    public DecimalFormat(String pattern, DecimalFormatSymbols symbols) {
        this.pattern = pattern;
    }

    @Override
    public String format(long number) {
        return format((double) number);
    }

    @Override
    public String format(double number) {
        // Count decimal digits in the pattern (characters after '.')
        int dotIdx = pattern.indexOf('.');
        int precision = 0;
        if (dotIdx >= 0) {
            for (int i = dotIdx + 1; i < pattern.length(); i++) {
                char c = pattern.charAt(i);
                if (c == '0' || c == '#') precision++;
                else break;
            }
        }
        return formatFixed(number, precision);
    }

    private static native String formatFixed(double n, int precision) /*-{
        return n.toFixed(precision);
    }-*/;
}
