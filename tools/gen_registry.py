#!/usr/bin/env python3
"""
Generate ClassRegistry.java for the Shattered Pixel Dungeon GWT build.
Handles both top-level classes AND public static inner classes.
Correctly handles any modifier ordering (abstract public / public abstract).
"""
import os, re

# Resolve the repository root relative to this script's location.
# gen_registry.py lives at  <repo>/tools/gen_registry.py, so the repo root
# is two levels up.  This avoids hard-coding an absolute CI path.
_SCRIPT_DIR = os.path.dirname(os.path.abspath(__file__))
BASE = os.path.normpath(os.path.join(_SCRIPT_DIR, ".."))
CORE = os.path.join(BASE, "core/src/main/java")
SPDCLASSES = os.path.join(BASE, "SPD-classes/src/main/java")

SCAN_DIRS = [
    (CORE,       "com/shatteredpixel/shatteredpixeldungeon/actors",  True),
    (CORE,       "com/shatteredpixel/shatteredpixeldungeon/items",   True),
    (CORE,       "com/shatteredpixel/shatteredpixeldungeon/levels",  True),
    (CORE,       "com/shatteredpixel/shatteredpixeldungeon/plants",  True),
    (CORE,       "com/shatteredpixel/shatteredpixeldungeon/tiles",   True),
    (CORE,       "com/shatteredpixel/shatteredpixeldungeon/journal", True),
    (SPDCLASSES, "com/watabou/utils",                                False),
]

# Modifiers we might see in any order before 'class|interface|enum'
MOD = r'(?:public|protected|private|abstract|static|final|strictfp|\s)+'

def classify_name(content, classname):
    """
    Returns 'concrete', 'abstract', 'interface', or 'enum' for a named class.
    Handles any ordering of modifiers.
    """
    # look for a declaration of this specific classname
    pat = re.compile(
        r'\b(' + MOD + r')(class|interface|enum)\s+' + re.escape(classname) + r'\b'
    )
    m = pat.search(content)
    if not m:
        return 'concrete'   # fallback
    modifiers = m.group(1)
    keyword = m.group(2)
    if keyword == 'interface':
        return 'interface'
    if keyword == 'enum':
        return 'enum'
    if 'abstract' in modifiers:
        return 'abstract'
    return 'concrete'

def parse_file(fpath, pkg, outer_classname):
    """
    Returns list of (bin_name, java_ref, kind) for the outer class and
    all public/package static inner classes in the file.
    """
    results = []
    try:
        content = open(fpath).read()
    except Exception:
        return results

    # top-level class
    top_kind = classify_name(content, outer_classname)
    if top_kind != 'interface':
        fq = pkg + '.' + outer_classname
        results.append((fq, fq, top_kind))

    # static inner classes: look for patterns like
    #   public static [abstract] class InnerName
    #   abstract static class InnerName  (any order)
    inner_pat = re.compile(
        r'\b(' + MOD + r')(class|interface|enum)\s+([A-Za-z_][A-Za-z0-9_]*)\b'
    )
    for m in inner_pat.finditer(content):
        modifiers = m.group(1)
        keyword = m.group(2)
        inner_name = m.group(3)

        # must be static (inner) and not the outer class itself
        if 'static' not in modifiers or inner_name == outer_classname:
            continue
        if keyword == 'interface':
            continue

        is_abstract = 'abstract' in modifiers
        inner_kind = 'enum' if keyword == 'enum' else ('abstract' if is_abstract else 'concrete')
        bin_name = pkg + '.' + outer_classname + '$' + inner_name
        java_ref = pkg + '.' + outer_classname + '.' + inner_name
        results.append((bin_name, java_ref, inner_kind))

    return results

entries = []
for src_root, rel_pkg, provide_factories in SCAN_DIRS:
    pkg_dir = os.path.join(src_root, rel_pkg)
    for root, dirs, files in os.walk(pkg_dir):
        rel_from_src = os.path.relpath(root, src_root)
        pkg = rel_from_src.replace(os.sep, '.')
        for fname in sorted(files):
            if not fname.endswith('.java'):
                continue
            outer_classname = fname[:-5]
            fpath = os.path.join(root, fname)
            for bin_name, java_ref, kind in parse_file(fpath, pkg, outer_classname):
                entries.append((bin_name, java_ref, kind, provide_factories))

# De-duplicate
seen = set()
deduped = []
for e in entries:
    if e[0] not in seen:
        seen.add(e[0])
        deduped.append(e)
entries = deduped

HEADER = '''/*
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

package com.shatteredpixel.shatteredpixeldungeon.html;

import com.watabou.utils.Reflection;

/**
 * GWT class registry: manually registers every game class that may appear
 * as a {@code __className} key in a save Bundle so that the GWT super-source
 * {@link Reflection#forName} / {@link Reflection#newInstance} can resolve them.
 *
 * This file is AUTO-GENERATED by tools/gen_registry.py.
 * Do not edit by hand; run the generator script after adding new Bundlable classes.
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public final class ClassRegistry {

    private ClassRegistry() {}

    /** Call once, before any Bundle is read, to populate the registry. */
    public static void registerAll() {'''

lines = [HEADER]

for bin_name, java_ref, kind, provide_factories in entries:
    if kind in ('abstract', 'enum') or not provide_factories:
        lines.append(f"        Reflection.registerClass(\"{bin_name}\", {java_ref}.class);")
    else:
        lines.append(f"        Reflection.registerClass(\"{bin_name}\", {java_ref}.class, () -> new {java_ref}());")

lines.append("")
lines.append("        // --- classes outside the scanned package roots ---")
lines.append("        Reflection.registerClass(\"com.shatteredpixel.shatteredpixeldungeon.Rankings$Record\", com.shatteredpixel.shatteredpixeldungeon.Rankings.Record.class, () -> new com.shatteredpixel.shatteredpixeldungeon.Rankings.Record());")

lines.append("    }")
lines.append("}")

# Write to the canonical output path inside the repo, unless stdout is requested
_OUT = os.path.join(
    BASE,
    "html/src/main/java/com/shatteredpixel/shatteredpixeldungeon/html/ClassRegistry.java"
)
import sys
if len(sys.argv) > 1 and sys.argv[1] == "--stdout":
    print('\n'.join(lines))
else:
    with open(_OUT, 'w') as f:
        f.write('\n'.join(lines))
    print(f"Written {len(lines)} lines to {_OUT}")
