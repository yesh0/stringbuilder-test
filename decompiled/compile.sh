#!/bin/sh

compile() {
    ver="$1"
    src="$2"

    base=$(basename "$src" ".java")
    dir=$(dirname "$src")
    file="$dir/$base$ver.java"
    class="$dir/$base$ver.class"
    out="$dir/$base$ver.txt"

    PATH="/usr/lib/jvm/java-$ver-openjdk/bin:$PATH" javac -version
    sed -e "s/$base/$base$ver/" "$src" > "$file"
    PATH="/usr/lib/jvm/java-$ver-openjdk/bin:$PATH" javac "$file"

    PATH="/usr/lib/jvm/java-$ver-openjdk/bin:$PATH" javap -c "$base$ver" > "$out"
    rm "$file"
    rm "$class"
}

src="$1"
for ver in 8 11 17; do
    compile "$ver" "$src"
done
