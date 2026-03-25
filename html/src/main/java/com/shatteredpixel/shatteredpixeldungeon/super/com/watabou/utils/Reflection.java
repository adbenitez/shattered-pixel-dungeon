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
 * GWT super-source: replaces com.watabou.utils.Reflection for the HTML5 build.
 *
 * The regular Reflection.java delegates every operation to libGDX's
 * ClassReflection, which relies on the generated IReflectionCache2Generated
 * class.  That class is a single Java class whose constant pool grows with
 * every reflected type; for this project it blows past the JVM 65 536-entry
 * limit and the GWT compilation fails.
 *
 * This replacement uses a simple HashMap-based registry that is populated
 * at start-up by ClassRegistry.registerAll() (called from HtmlLauncher).
 * No reflection cache is generated at all, so the constant-pool limit is
 * never hit.  The gdx.reflect.include configuration properties in
 * GdxDefinition.gwt.xml are therefore not needed and have been removed.
 *
 * The public API (method signatures) is identical to the original
 * Reflection.java so that every call site compiles without modification.
 */

package com.watabou.utils;

import java.util.HashMap;

/**
 * GWT super-source replacement for {@link com.watabou.utils.Reflection}.
 *
 * <p>Uses a manually-populated registry instead of libGDX's
 * {@code IReflectionCache2Generated} to avoid the JVM 65 536-entry
 * constant-pool limit.</p>
 *
 * <p>Call {@code ClassRegistry.registerAll()} once during start-up (before
 * any save Bundle is read) to populate the registry.</p>
 */
public class Reflection {

	/** Factory interface used to create new instances without reflection. */
	public interface ClassFactory {
		Object create();
	}

	private static final HashMap<String, Class<?>> classMap    = new HashMap<>();
	private static final HashMap<Class<?>, ClassFactory> factories = new HashMap<>();

	/**
	 * Register a class for {@link #forName} look-up only (no factory).
	 * Use this for abstract classes, interfaces, and enums.
	 */
	public static void registerClass(String name, Class<?> cls) {
		classMap.put(name, cls);
	}

	/**
	 * Register a class for both {@link #forName} look-up and
	 * {@link #newInstance} instantiation.
	 */
	public static void registerClass(String name, Class<?> cls, ClassFactory factory) {
		classMap.put(name, cls);
		factories.put(cls, factory);
	}

	// -----------------------------------------------------------------------
	// API matching com.watabou.utils.Reflection
	// -----------------------------------------------------------------------

	/**
	 * Always returns {@code false}.
	 *
	 * <p>All registered Bundlable classes are either top-level or public
	 * static inner classes; neither category requires an enclosing instance.
	 * Bundle.java uses this to skip non-static inner classes, but since those
	 * are never registered (and {@link #newInstance} returns {@code null} for
	 * unregistered types), returning {@code false} here is safe.</p>
	 */
	public static boolean isMemberClass(Class cls) {
		return false;
	}

	/** Not called when {@link #isMemberClass} returns {@code false}. */
	public static boolean isStatic(Class cls) {
		return false;
	}

	/**
	 * Creates a new instance using the registered factory, or returns
	 * {@code null} if no factory was registered for {@code cls}.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T newInstance(Class<T> cls) {
		ClassFactory factory = factories.get(cls);
		if (factory != null) {
			return (T) factory.create();
		}
		return null;
	}

	/** Same as {@link #newInstance} but throws instead of returning null. */
	@SuppressWarnings("unchecked")
	public static <T> T newInstanceUnhandled(Class<T> cls) throws Exception {
		T result = newInstance(cls);
		if (result == null) {
			throw new Exception("No factory registered for class: " + cls.getName());
		}
		return result;
	}

	/**
	 * Looks up a class by its binary name (as returned by
	 * {@link Class#getName()}).  Returns {@code null} when not found.
	 */
	public static Class forName(String name) {
		return classMap.get(name);
	}

	/** Same as {@link #forName} but throws instead of returning null. */
	public static Class forNameUnhandled(String name) throws Exception {
		Class cls = forName(name);
		if (cls == null) {
			throw new Exception("Class not found in GWT registry: " + name);
		}
		return cls;
	}

	/** Delegates to {@link Class#isAssignableFrom}, which GWT emulates. */
	public static boolean isAssignableFrom(Class parent, Class child) {
		return parent.isAssignableFrom(child);
	}

	/** Delegates to {@link Class#isInstance}, which GWT emulates. */
	public static boolean isInstance(Class cls, Object obj) {
		return cls.isInstance(obj);
	}
}
