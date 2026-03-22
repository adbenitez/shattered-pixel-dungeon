/*
 * GWT super-source: org.json.JSONArray implementation for the HTML5 build.
 *
 * See JSONObject.java in the same directory for implementation notes.
 */
package org.json;

import com.google.gwt.core.client.JavaScriptObject;

public final class JSONArray {

    /** The underlying JavaScript array. */
    final JavaScriptObject arr;

    // -----------------------------------------------------------------------
    // Constructors
    // -----------------------------------------------------------------------

    public JSONArray() {
        arr = newArr();
    }

    /** Package-private: wraps an existing JavaScript array. */
    JSONArray(JavaScriptObject arr) {
        this.arr = arr;
    }

    private static native JavaScriptObject newArr() /*-{
        return [];
    }-*/;

    // -----------------------------------------------------------------------
    // Appending (no-index overloads used by Bundle.java)
    // -----------------------------------------------------------------------

    public JSONArray put(boolean value) {
        pushBooleanNative(arr, value);
        return this;
    }

    public JSONArray put(int value) {
        pushDoubleNative(arr, value);
        return this;
    }

    public JSONArray put(long value) {
        pushDoubleNative(arr, (double) value);
        return this;
    }

    public JSONArray put(float value) {
        pushDoubleNative(arr, value);
        return this;
    }

    public JSONArray put(double value) {
        pushDoubleNative(arr, value);
        return this;
    }

    public JSONArray put(String value) {
        pushStringNative(arr, value);
        return this;
    }

    public JSONArray put(JSONObject value) {
        pushObjectNative(arr, value == null ? null : value.obj);
        return this;
    }

    public JSONArray put(Object value) {
        if (value instanceof JSONObject) {
            return put((JSONObject) value);
        } else if (value instanceof JSONArray) {
            pushObjectNative(arr, ((JSONArray) value).arr);
        } else if (value instanceof Boolean) {
            return put((boolean) (Boolean) value);
        } else if (value instanceof Number) {
            return put(((Number) value).doubleValue());
        } else if (value instanceof String) {
            return put((String) value);
        } else if (value != null) {
            return put(value.toString());
        }
        return this;
    }

    // -----------------------------------------------------------------------
    // Indexed put overloads (used by Bundle.java for arrays)
    // -----------------------------------------------------------------------

    public JSONArray put(int index, boolean value) throws JSONException {
        putBooleanAt(arr, index, value);
        return this;
    }

    public JSONArray put(int index, int value) throws JSONException {
        putDoubleAt(arr, index, (double) value);
        return this;
    }

    public JSONArray put(int index, long value) throws JSONException {
        putDoubleAt(arr, index, (double) value);
        return this;
    }

    public JSONArray put(int index, float value) throws JSONException {
        putDoubleAt(arr, index, (double) value);
        return this;
    }

    public JSONArray put(int index, double value) throws JSONException {
        putDoubleAt(arr, index, value);
        return this;
    }

    public JSONArray put(int index, String value) throws JSONException {
        putStringAt(arr, index, value);
        return this;
    }

    // -----------------------------------------------------------------------
    // Length
    // -----------------------------------------------------------------------

    public int length() {
        return lengthNative(arr);
    }

    private static native int lengthNative(JavaScriptObject a) /*-{
        return a.length;
    }-*/;

    // -----------------------------------------------------------------------
    // Typed getters used by Bundle.java
    // -----------------------------------------------------------------------

    public Object get(int index) throws JSONException {
        return getString(index);
    }

    public String getString(int index) throws JSONException {
        String s = getStringAt(arr, index);
        if (s == null) throw new JSONException("JSONArray[" + index + "] is null.");
        return s;
    }

    public int getInt(int index) throws JSONException {
        return (int) getDoubleAt(arr, index);
    }

    public long getLong(int index) throws JSONException {
        return (long) getDoubleAt(arr, index);
    }

    public boolean getBoolean(int index) throws JSONException {
        return getBooleanAt(arr, index);
    }

    public double optDouble(int index, double defaultValue) {
        if (index < 0 || index >= length()) return defaultValue;
        return getDoubleAt(arr, index);
    }

    public JSONObject getJSONObject(int index) throws JSONException {
        JavaScriptObject child = getObjectAt(arr, index);
        if (child == null) throw new JSONException("JSONArray[" + index + "] is not a JSONObject.");
        return new JSONObject(child);
    }

    // -----------------------------------------------------------------------
    // JSNI helpers
    // -----------------------------------------------------------------------

    private static native void pushBooleanNative(JavaScriptObject a, boolean v) /*-{
        a.push(v);
    }-*/;
    private static native void pushDoubleNative(JavaScriptObject a, double v) /*-{
        a.push(v);
    }-*/;
    private static native void pushStringNative(JavaScriptObject a, String v) /*-{
        a.push(v);
    }-*/;
    private static native void pushObjectNative(JavaScriptObject a, JavaScriptObject v) /*-{
        a.push(v);
    }-*/;

    private static native void putBooleanAt(JavaScriptObject a, int i, boolean v) /*-{
        a[i] = v;
    }-*/;
    private static native void putDoubleAt(JavaScriptObject a, int i, double v) /*-{
        a[i] = v;
    }-*/;
    private static native void putStringAt(JavaScriptObject a, int i, String v) /*-{
        a[i] = v;
    }-*/;

    private static native String getStringAt(JavaScriptObject a, int i) /*-{
        var v = a[i];
        if (v == null) return null;
        return String(v);
    }-*/;
    private static native double getDoubleAt(JavaScriptObject a, int i) /*-{
        return +a[i];
    }-*/;
    private static native boolean getBooleanAt(JavaScriptObject a, int i) /*-{
        return !!a[i];
    }-*/;
    private static native JavaScriptObject getObjectAt(JavaScriptObject a, int i) /*-{
        var v = a[i];
        return (v != null && typeof v === 'object') ? v : null;
    }-*/;

    // -----------------------------------------------------------------------
    // Serialisation
    // -----------------------------------------------------------------------

    @Override
    public String toString() {
        return stringifyNative(arr);
    }

    private static native String stringifyNative(JavaScriptObject a) /*-{
        return JSON.stringify(a);
    }-*/;
}
