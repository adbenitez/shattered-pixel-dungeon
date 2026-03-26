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

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import java.io.IOException;
import java.util.ArrayList;

/**
 * GWT super-source stub for FileUtils.
 * File I/O and game saving are disabled for HTML builds.
 */
public class FileUtils {

	public static void setDefaultFileProperties(com.badlogic.gdx.Files.FileType type, String path) {
	}

	public static FileHandle getFileHandle(String name) {
		return Gdx.files.internal(name);
	}

	public static FileHandle getFileHandle(Files.FileType type, String name) {
		return Gdx.files.getFileHandle(name, type);
	}

	public static FileHandle getFileHandle(Files.FileType type, String basePath, String name) {
		return Gdx.files.getFileHandle(basePath + name, type);
	}

	public static boolean cleanTempFiles() {
		return false;
	}

	public static boolean cleanTempFiles(String dirName) {
		return false;
	}

	public static boolean fileExists(String name) {
		return false;
	}

	public static long fileLength(String name) {
		return 0;
	}

	public static boolean deleteFile(String name) {
		return false;
	}

	public static void overwriteFile(String name, int bytes) {
	}

	public static boolean dirExists(String name) {
		return false;
	}

	public static boolean deleteDir(String name) {
		return false;
	}

	public static ArrayList<String> filesInDir(String name) {
		return new ArrayList<>();
	}

	public static Bundle bundleFromFile(String fileName) throws IOException {
		throw new IOException("File I/O not supported in HTML build");
	}

	public static void bundleToFile(String fileName, Bundle bundle) throws IOException {
		// no-op: saving disabled in HTML build
	}
}
