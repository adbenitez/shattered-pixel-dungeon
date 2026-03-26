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
 * GWT super-source: no-op stub for com.watabou.utils.Reflection.
 *
 * The regular Reflection.java delegates to libGDX's ClassReflection, which
 * generates a huge IReflectionCache2Generated class that overflows the JVM
 * 65 536-entry constant-pool limit for this project's class count.
 *
 * Since the HTML build does not use the Bundle serialization system for
 * saving game state, we simply return safe null/false values for every
 * operation.  Bundle.get() will return null (no objects restored from JSON),
 * which means save-loading is disabled in the HTML build.
 *
 * No gdx.reflect.include entries are needed in GdxDefinition.gwt.xml.
 */

package com.watabou.utils;

/**
 * GWT no-op stub for {@link com.watabou.utils.Reflection}.
 * All methods return safe defaults; the Bundle system will silently skip
 * object restoration at runtime, which is acceptable for the HTML build.
 */
public class Reflection {

	public static boolean isMemberClass( Class cls ) {
		return false;
	}

	public static boolean isStatic( Class cls ) {
		return true;
	}

	public static <T> T newInstance( Class<T> cls ) {
		return null;
	}

	public static <T> T newInstanceUnhandled( Class<T> cls ) throws Exception {
		return null;
	}

	public static Class forName( String name ) {
		return null;
	}

	public static Class forNameUnhandled( String name ) throws Exception {
		return null;
	}

	public static boolean isAssignableFrom( Class parent, Class child ) {
		return parent.isAssignableFrom( child );
	}

	public static boolean isInstance( Class cls, Object obj ) {
		return cls.isInstance( obj );
	}

}
