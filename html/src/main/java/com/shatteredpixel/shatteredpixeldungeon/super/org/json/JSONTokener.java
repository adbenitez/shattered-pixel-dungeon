/*
 * GWT super-source: org.json.JSONTokener implementation for the HTML5 build.
 *
 * Bundle.java only uses JSONTokener to parse a top-level JSON value from a
 * String.  We delegate to the browser's native JSON.parse() via JSNI.
 */
package org.json;

import com.google.gwt.core.client.JavaScriptObject;

public final class JSONTokener {

    private final String source;

    public JSONTokener(String source) {
        this.source = source;
    }

    /**
     * Parses the source string and returns the top-level JSON value.
     * Returns a {@link JSONObject} for a JSON object, or a {@link JSONArray}
     * for a JSON array.
     *
     * @throws JSONException if the source is not valid JSON.
     */
    public Object nextValue() throws JSONException {
        try {
            JavaScriptObject parsed = parseNative(source);
            if (isArray(parsed)) {
                return new JSONArray(parsed);
            } else {
                return new JSONObject(parsed);
            }
        } catch (Exception e) {
            throw new JSONException("Failed to parse JSON: " + e.getMessage());
        }
    }

    private static native JavaScriptObject parseNative(String json) /*-{
        return JSON.parse(json);
    }-*/;

    private static native boolean isArray(JavaScriptObject o) /*-{
        return Array.isArray(o);
    }-*/;
}
