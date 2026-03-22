/*
 * GWT super-source: org.json.JSONObject implementation for the HTML5 build.
 *
 * This file lives in the html module's super-source directory and replaces
 * the real org.json.JSONObject for GWT compilation only.
 *
 * Implementation uses GWT JSNI (JavaScript Native Interface) to delegate to
 * the browser's native JSON engine via JavaScript overlay types
 * (JavaScriptObject).  This gives us near-zero overhead and avoids the need
 * for a Java-level JSON parser in the GWT build.
 *
 * Only the methods actually called by Bundle.java (and its callers) are
 * implemented.  Unused org.json methods are stubbed out.
 */
package org.json;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayString;

import java.util.ArrayList;
import java.util.Iterator;

public final class JSONObject {

    /** The underlying JavaScript plain object. */
    final JavaScriptObject obj;

    // -----------------------------------------------------------------------
    // Constructors
    // -----------------------------------------------------------------------

    /** Creates an empty JSON object. */
    public JSONObject() {
        obj = newObj();
    }

    /** Package-private: wraps an existing JavaScript object. */
    JSONObject(JavaScriptObject obj) {
        this.obj = obj;
    }

    private static native JavaScriptObject newObj() /*-{
        return {};
    }-*/;

    // -----------------------------------------------------------------------
    // Presence / nullity
    // -----------------------------------------------------------------------

    /**
     * Returns {@code true} if the key does not exist or its value is JSON
     * {@code null}.
     */
    public boolean isNull(String key) {
        return isNullNative(obj, key);
    }

    private static native boolean isNullNative(JavaScriptObject o, String k) /*-{
        return !o.hasOwnProperty(k) || o[k] == null;
    }-*/;

    // -----------------------------------------------------------------------
    // Removal
    // -----------------------------------------------------------------------

    public Object remove(String key) {
        Object prev = opt(key);
        removeNative(obj, key);
        return prev;
    }

    private static native void removeNative(JavaScriptObject o, String k) /*-{
        delete o[k];
    }-*/;

    private Object opt(String key) {
        if (isNull(key)) return null;
        return getStringNative(obj, key); // good enough for the return value
    }

    // -----------------------------------------------------------------------
    // put – accepts all types used by Bundle.java
    // -----------------------------------------------------------------------

    public JSONObject put(String key, boolean value) throws JSONException {
        putBooleanNative(obj, key, value);
        return this;
    }

    public JSONObject put(String key, int value) throws JSONException {
        putDoubleNative(obj, key, value);
        return this;
    }

    public JSONObject put(String key, long value) throws JSONException {
        putDoubleNative(obj, key, (double) value);
        return this;
    }

    public JSONObject put(String key, float value) throws JSONException {
        putDoubleNative(obj, key, value);
        return this;
    }

    public JSONObject put(String key, double value) throws JSONException {
        putDoubleNative(obj, key, value);
        return this;
    }

    public JSONObject put(String key, String value) throws JSONException {
        if (value == null) {
            putNullNative(obj, key);
        } else {
            putStringNative(obj, key, value);
        }
        return this;
    }

    /** Called by Bundle when storing a Class object (stores the class name). */
    public JSONObject put(String key, Object value) throws JSONException {
        if (value == null) {
            putNullNative(obj, key);
        } else if (value instanceof JSONObject) {
            putObjectNative(obj, key, ((JSONObject) value).obj);
        } else if (value instanceof JSONArray) {
            putObjectNative(obj, key, ((JSONArray) value).arr);
        } else if (value instanceof Boolean) {
            putBooleanNative(obj, key, (Boolean) value);
        } else if (value instanceof Double || value instanceof Float) {
            putDoubleNative(obj, key, ((Number) value).doubleValue());
        } else if (value instanceof Number) {
            putDoubleNative(obj, key, ((Number) value).doubleValue());
        } else if (value instanceof Class) {
            putStringNative(obj, key, ((Class<?>) value).getName());
        } else {
            putStringNative(obj, key, value.toString());
        }
        return this;
    }

    private static native void putBooleanNative(JavaScriptObject o, String k, boolean v) /*-{
        o[k] = v;
    }-*/;
    private static native void putDoubleNative(JavaScriptObject o, String k, double v) /*-{
        o[k] = v;
    }-*/;
    private static native void putStringNative(JavaScriptObject o, String k, String v) /*-{
        o[k] = v;
    }-*/;
    private static native void putObjectNative(JavaScriptObject o, String k, JavaScriptObject v) /*-{
        o[k] = v;
    }-*/;
    private static native void putNullNative(JavaScriptObject o, String k) /*-{
        o[k] = null;
    }-*/;

    // -----------------------------------------------------------------------
    // get / opt – typed accessors used by Bundle.java
    // -----------------------------------------------------------------------

    public Object get(String key) throws JSONException {
        if (isNull(key)) throw new JSONException("JSONObject[\"" + key + "\"] not found.");
        return getStringNative(obj, key);
    }

    public String getString(String key) throws JSONException {
        if (isNull(key)) throw new JSONException("JSONObject[\"" + key + "\"] not found.");
        return getStringNative(obj, key);
    }

    public String optString(String key) {
        return optString(key, "");
    }

    public String optString(String key, String defaultValue) {
        if (isNull(key)) return defaultValue;
        return getStringNative(obj, key);
    }

    public boolean optBoolean(String key) {
        return optBoolean(key, false);
    }

    public boolean optBoolean(String key, boolean defaultValue) {
        if (isNull(key)) return defaultValue;
        return getBooleanNative(obj, key);
    }

    public int optInt(String key) {
        return optInt(key, 0);
    }

    public int optInt(String key, int defaultValue) {
        if (isNull(key)) return defaultValue;
        return (int) getDoubleNative(obj, key);
    }

    public long optLong(String key) {
        return optLong(key, 0L);
    }

    public long optLong(String key, long defaultValue) {
        if (isNull(key)) return defaultValue;
        return (long) getDoubleNative(obj, key);
    }

    public double optDouble(String key, double defaultValue) {
        if (isNull(key)) return defaultValue;
        return getDoubleNative(obj, key);
    }

    public JSONObject optJSONObject(String key) {
        if (isNull(key)) return null;
        JavaScriptObject child = getObjectNative(obj, key);
        return child == null ? null : new JSONObject(child);
    }

    public JSONArray getJSONArray(String key) throws JSONException {
        if (isNull(key)) throw new JSONException("JSONObject[\"" + key + "\"] is not a JSONArray.");
        JavaScriptObject arr = getObjectNative(obj, key);
        if (arr == null) throw new JSONException("JSONObject[\"" + key + "\"] is not a JSONArray.");
        return new JSONArray(arr);
    }

    private static native String getStringNative(JavaScriptObject o, String k) /*-{
        var v = o[k];
        if (v == null) return "";
        return String(v);
    }-*/;
    private static native boolean getBooleanNative(JavaScriptObject o, String k) /*-{
        return !!o[k];
    }-*/;
    private static native double getDoubleNative(JavaScriptObject o, String k) /*-{
        return +o[k];
    }-*/;
    private static native JavaScriptObject getObjectNative(JavaScriptObject o, String k) /*-{
        var v = o[k];
        return (v != null && typeof v === 'object') ? v : null;
    }-*/;

    // -----------------------------------------------------------------------
    // Key iteration – used by Bundle.getKeys()
    // -----------------------------------------------------------------------

    public Iterator<String> keys() {
        JsArrayString ks = keysNative(obj);
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < ks.length(); i++) {
            list.add(ks.get(i));
        }
        return list.iterator();
    }

    private static native JsArrayString keysNative(JavaScriptObject o) /*-{
        return Object.keys(o);
    }-*/;

    // -----------------------------------------------------------------------
    // Serialisation
    // -----------------------------------------------------------------------

    @Override
    public String toString() {
        return stringifyNative(obj);
    }

    private static native String stringifyNative(JavaScriptObject o) /*-{
        return JSON.stringify(o);
    }-*/;
}
