#!/bin/bash
set -e

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
PROJECT_ROOT="$DIR/../.."
OUTPUT_DIR="$PROJECT_ROOT/desktop/build/macos"
APP_DIR="$OUTPUT_DIR/AuraMusic.app"
DMG_PATH="$PROJECT_ROOT/desktop/build/AuraMusic-1.0.0.dmg"

echo "==> Building Aura Music macOS Native App..."
mkdir -p "$OUTPUT_DIR"
mkdir -p "$APP_DIR/Contents/MacOS"
mkdir -p "$APP_DIR/Contents/Resources"

# Compile Swift native binary
swiftc -O "$DIR/main.swift" -o "$APP_DIR/Contents/MacOS/AuraMusic" -framework Cocoa -framework WebKit

# Copy Info.plist
cp "$DIR/Info.plist" "$APP_DIR/Contents/Info.plist"

# Copy Icon if exists
if [ -f "$PROJECT_ROOT/desktop/build/compose/default-resources/1.7.0/default-icon-mac.icns" ]; then
    cp "$PROJECT_ROOT/desktop/build/compose/default-resources/1.7.0/default-icon-mac.icns" "$APP_DIR/Contents/Resources/AppIcon.icns"
fi

echo "==> Creating macOS DMG image..."
rm -f "$DMG_PATH"
hdiutil create -volname "Aura Music" -srcfolder "$APP_DIR" -ov -format UDZO "$DMG_PATH"

echo "==> Build successful! DMG is ready at: $DMG_PATH"
