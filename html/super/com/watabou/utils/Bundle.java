/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2026 Evan Debenham
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

/*
 * GWT super-source: replaces Bundle for the HTML build.
 * Game saving and loading is disabled in the HTML build; all read/write
 * operations are stubs that succeed silently or return empty values.
 * We avoid java.util.zip and org.json which are not available in GWT.
 */

package com.watabou.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

public class Bundle {

    private static final String CLASS_NAME = "__className";

    public static final String DEFAULT_KEY = "key";

    public Bundle() {}

    @Override
    public String toString() {
        return "{}";
    }

    public boolean isNull() {
        return false;
    }

    public boolean contains( String key ) {
        return false;
    }

    public boolean remove( String key ) {
        return false;
    }

    public ArrayList<String> getKeys() {
        return new ArrayList<>();
    }

    public boolean getBoolean( String key ) {
        return false;
    }

    public int getInt( String key ) {
        return 0;
    }

    public long getLong( String key ) {
        return 0L;
    }

    public float getFloat( String key ) {
        return 0f;
    }

    public String getString( String key ) {
        return "";
    }

    public Class getClass( String key ) {
        return null;
    }

    public Bundle getBundle( String key ) {
        return null;
    }

    public Bundlable get( String key ) {
        return null;
    }

    public <E extends Enum<E>> E getEnum( String key, Class<E> enumClass ) {
        return null;
    }

    public int[] getIntArray( String key ) {
        return new int[0];
    }

    public long[] getLongArray( String key ) {
        return new long[0];
    }

    public float[] getFloatArray( String key ) {
        return new float[0];
    }

    public boolean[] getBooleanArray( String key ) {
        return new boolean[0];
    }

    public String[] getStringArray( String key ) {
        return new String[0];
    }

    public Class[] getClassArray( String key ) {
        return new Class[0];
    }

    public Bundle[] getBundleArray() {
        return new Bundle[0];
    }

    public Bundle[] getBundleArray( String key ) {
        return new Bundle[0];
    }

    public Collection<Bundlable> getCollection( String key ) {
        return new ArrayList<>();
    }

    public void put( String key, boolean value ) {}
    public void put( String key, int value ) {}
    public void put( String key, long value ) {}
    public void put( String key, float value ) {}
    public void put( String key, String value ) {}
    public void put( String key, Class value ) {}
    public void put( String key, Bundle bundle ) {}
    public void put( String key, Bundlable object ) {}
    public void put( String key, Enum<?> value ) {}
    public void put( String key, int[] array ) {}
    public void put( String key, long[] array ) {}
    public void put( String key, float[] array ) {}
    public void put( String key, boolean[] array ) {}
    public void put( String key, String[] array ) {}
    public void put( String key, Class[] array ) {}
    public void put( String key, Collection<? extends Bundlable> collection ) {}

    // Saving/loading is disabled in the HTML build
    public static Bundle read( InputStream stream ) throws IOException {
        throw new IOException("Bundle serialization is not supported in the HTML build");
    }

    public static boolean write( Bundle bundle, OutputStream stream ) {
        return false;
    }

    public static boolean write( Bundle bundle, OutputStream stream, boolean compressed ) {
        return false;
    }

    public static void addAlias( Class<?> cl, String alias ) {
        // no-op: aliases are only needed for deserialization, which is disabled
    }
}
