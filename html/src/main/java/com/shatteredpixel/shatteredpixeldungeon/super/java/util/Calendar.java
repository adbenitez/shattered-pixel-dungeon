/*
 * GWT super-source: java.util.Calendar emulation for the HTML5 build.
 *
 * GWT does not emulate java.util.Calendar.  This stub provides the subset
 * of the Calendar API used by Holiday.java and News.java, backed by the
 * GWT-emulated java.util.Date (which is backed by the browser's Date object).
 */
package java.util;

/** Minimal GWT-compatible Calendar backed by {@link java.util.Date}. */
public abstract class Calendar {

// Field constants used by the game
public static final int YEAR         = 1;
public static final int MONTH        = 2;
public static final int DAY_OF_MONTH = 5;
public static final int DAY_OF_YEAR  = 6;
public static final int DAY_OF_WEEK  = 7;

// Month constants (0-indexed, same as java.util.Date)
public static final int JANUARY   = 0;
public static final int FEBRUARY  = 1;
public static final int MARCH     = 2;
public static final int APRIL     = 3;
public static final int MAY       = 4;
public static final int JUNE      = 5;
public static final int JULY      = 6;
public static final int AUGUST    = 7;
public static final int SEPTEMBER = 8;
public static final int OCTOBER   = 9;
public static final int NOVEMBER  = 10;
public static final int DECEMBER  = 11;

private long timeMillis;

protected Calendar() {
timeMillis = System.currentTimeMillis();
}

/** Returns a Calendar for the current date/time (uses GregorianCalendar). */
public static Calendar getInstance() {
return new GregorianCalendar();
}

public void setTime(Date d) {
timeMillis = d.getTime();
}

public Date getTime() {
return new Date(timeMillis);
}

/** Returns the value of the given calendar field for the stored date. */
public int get(int field) {
Date d = new Date(timeMillis);
switch (field) {
case YEAR:
return d.getYear() + 1900;
case MONTH:
return d.getMonth(); // 0-indexed in both Calendar and Date
case DAY_OF_MONTH:
return d.getDate(); // 1-indexed
case DAY_OF_WEEK:
return d.getDay() + 1; // Calendar is 1-indexed (1 = Sunday)
case DAY_OF_YEAR:
return computeDayOfYear(d);
default:
return 0;
}
}

/**
 * Returns the maximum value that the given field can have for the
 * current date.  Only {@link #DAY_OF_YEAR} is implemented.
 */
public int getActualMaximum(int field) {
if (field == DAY_OF_YEAR) {
return isLeapYear(new Date(timeMillis).getYear() + 1900) ? 366 : 365;
}
return 0;
}

// -----------------------------------------------------------------------
// Helpers
// -----------------------------------------------------------------------

private int computeDayOfYear(Date d) {
int[] dim = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
int year = d.getYear() + 1900;
if (isLeapYear(year)) dim[1] = 29;
int month = d.getMonth();
int day   = d.getDate();
int doy   = 0;
for (int i = 0; i < month; i++) doy += dim[i];
return doy + day;
}

private static boolean isLeapYear(int year) {
return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
}
}
