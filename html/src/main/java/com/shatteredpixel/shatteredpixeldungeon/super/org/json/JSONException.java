/*
 * GWT super-source: minimal JSONException compatible with the org.json API.
 * This file lives in the html module's super-source directory and replaces the
 * real org.json.JSONException for the GWT (HTML5) build only.
 */
package org.json;

public class JSONException extends Exception {

    public JSONException(String message) {
        super(message);
    }

    public JSONException(Throwable cause) {
        super(cause != null ? cause.getMessage() : null);
    }
}
