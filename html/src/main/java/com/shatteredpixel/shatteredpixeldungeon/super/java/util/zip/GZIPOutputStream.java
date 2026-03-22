/*
 * GWT super-source: java.util.zip.GZIPOutputStream stub for the HTML5 build.
 *
 * GWT / gdx-backend-gwt does not provide a GZIPOutputStream emulation.
 * This stub extends OutputStream so that Bundle.java compiles.
 *
 * At runtime the HTML build uses Bundle.write() with compressed=false
 * (writes are only for in-memory caching of game state on this platform),
 * so this code path is never actually reached.  If it were, the data would
 * be written uncompressed (a best-effort fallback).
 */
package java.util.zip;

import java.io.IOException;
import java.io.OutputStream;

/** GWT no-compression stub for {@code java.util.zip.GZIPOutputStream}. */
public class GZIPOutputStream extends OutputStream {

	private final OutputStream out;

	public GZIPOutputStream(OutputStream out) throws IOException {
		this.out = out;
	}

	public GZIPOutputStream(OutputStream out, int size) throws IOException {
		this.out = out;
	}

	@Override
	public void write(int b) throws IOException {
		out.write(b);
	}

	@Override
	public void write(byte[] b, int off, int len) throws IOException {
		out.write(b, off, len);
	}

	@Override
	public void flush() throws IOException {
		out.flush();
	}

	@Override
	public void close() throws IOException {
		out.close();
	}

	public void finish() throws IOException {
		// no-op: nothing to finalise without real GZIP
	}
}
