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
 * Cross-platform wrappers for Object monitor methods (wait/notify/notifyAll).
 *
 * GWT does not emulate Object.wait(), Object.notify(), or Object.notifyAll()
 * because it is single-threaded.  Code that calls those methods on arbitrary
 * objects (i.e. objects that are not java.lang.Thread instances) must go
 * through this class so that a GWT super-source no-op stub can replace the
 * calls at compile time.
 *
 * All callers must already hold the monitor for {@code obj} (i.e. be inside a
 * {@code synchronized(obj)} block) before calling these methods, exactly as
 * they would when calling Object.wait/notify directly.
 */
public class ThreadCompat {

	/** Calls {@code obj.wait()} – waits until notified. No-op in GWT. */
	public static void objectWait(Object obj) throws InterruptedException {
		obj.wait();
	}

	/** Calls {@code obj.notifyAll()} – wakes all threads waiting on obj. No-op in GWT. */
	public static void objectNotifyAll(Object obj) {
		obj.notifyAll();
	}

}
