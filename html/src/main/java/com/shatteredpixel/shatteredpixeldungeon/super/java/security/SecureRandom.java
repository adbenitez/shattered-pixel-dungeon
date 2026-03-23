/*
 * GWT super-source: java.security.SecureRandom stub for the HTML5 build.
 *
 * java.security.SecureRandom is not available in GWT.  The only usage in the
 * game is MissileWeapon.setID, which initialises a unique long identifier.
 * This stub delegates to Math.random() which is available in all browsers.
 */
package java.security;

/** Minimal GWT stub for {@code java.security.SecureRandom}. */
public class SecureRandom {

    public SecureRandom() {
    }

    public long nextLong() {
        // Combine two random doubles to produce a 64-bit value.
        long hi = (long) (Math.random() * (double) Integer.MAX_VALUE);
        long lo = (long) (Math.random() * (double) Integer.MAX_VALUE);
        return (hi << 32) | (lo & 0xFFFFFFFFL);
    }

    public int nextInt(int bound) {
        return (int) (Math.random() * bound);
    }

    public double nextDouble() {
        return Math.random();
    }
}
