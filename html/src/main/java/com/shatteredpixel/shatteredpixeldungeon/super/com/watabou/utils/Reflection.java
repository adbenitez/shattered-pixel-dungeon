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

package com.watabou.utils;

/**
 * GWT super-source stub for Reflection.
 * All methods return null/false to avoid ClassReflection and prevent
 * IReflectionCache2Generated constant-pool overflow in GWT compilation.
 * Also provides isAssignableFrom() and isInstance() using getSuperclass() since
 * GWT's java.lang.Class does not include these methods.
 */
public class Reflection {

	public static boolean isMemberClass(Class cls) {
		return false;
	}

	public static boolean isStatic(Class cls) {
		return false;
	}

	public static <T> T newInstance(Class<T> cls) {
		return null;
	}

	public static <T> T newInstanceUnhandled(Class<T> cls) throws Exception {
		throw new Exception("Reflection not supported in HTML build");
	}

	public static Class forName(String name) {
		return null;
	}

	public static Class forNameUnhandled(String name) throws Exception {
		throw new Exception("Reflection not supported in HTML build");
	}

	/**
	 * GWT-compatible implementation of Class.isAssignableFrom().
	 * GWT's java.lang.Class does not include isAssignableFrom(), so we implement
	 * it here using getSuperclass() traversal.
	 */
	public static boolean isAssignableFrom(Class<?> sup, Class<?> sub) {
		if (sup == null || sub == null) return false;
		if (sup == sub) return true;
		Class<?> superClass = sub.getSuperclass();
		while (superClass != null) {
			if (superClass == sup) return true;
			superClass = superClass.getSuperclass();
		}
		return false;
	}

	/**
	 * GWT-compatible implementation of Class.isInstance().
	 */
	public static boolean isInstance(Class<?> cls, Object obj) {
		if (obj == null) return false;
		return isAssignableFrom(cls, obj.getClass());
	}
}
