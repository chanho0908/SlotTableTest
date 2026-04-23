# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

SlotTable is an Android application built with Jetpack Compose and Kotlin. The project appears to be focused on exploring Compose internals, particularly the SlotTable mechanism (based on the project name).

**Package**: `com.peto.slottable`
**Min SDK**: 24
**Target SDK**: 36 (Android API 36 with minor API level 1)
**Build System**: Gradle with Kotlin DSL

## Build Commands

### Build the app
```bash
./gradlew assembleDebug
./gradlew assembleRelease
```

### Run tests
```bash
# Run all unit tests
./gradlew test

# Run specific unit test
./gradlew test --tests com.peto.slottable.ExampleUnitTest

# Run all instrumented tests (requires connected device/emulator)
./gradlew connectedAndroidTest

# Run specific instrumented test
./gradlew connectedAndroidTest -Pandroid.testInstrumentationRunnerArguments.class=com.peto.slottable.ExampleInstrumentedTest
```

### Lint and code quality
```bash
# Run Android lint
./gradlew lint

# Clean build
./gradlew clean
```

### Install and run
```bash
# Install debug build on connected device
./gradlew installDebug

# Uninstall app
./gradlew uninstallDebug
```

## Project Structure

### Source organization
- `app/src/main/java/com/peto/slottable/` - Main application code
  - `MainActivity.kt` - Single activity entry point using Compose
  - `ui/theme/` - Compose theme configuration (Color, Type, Theme)

### Build configuration
- Uses Gradle version catalogs (`gradle/libs.versions.toml`) for dependency management
- Kotlin Compose plugin for Compose compiler configuration
- Compose BOM (Bill of Materials) version: 2026.02.01

### Key dependencies
- **Compose UI**: Full Jetpack Compose stack with Material3
- **Activity Compose**: androidx.activity:activity-compose:1.13.0
- **Lifecycle Runtime**: androidx.lifecycle:lifecycle-runtime-ktx:2.10.0
- **Core KTX**: androidx.core:core-ktx:1.18.0

### Testing setup
- **Unit tests**: JUnit 4.13.2
- **Instrumented tests**: AndroidX Test (JUnit, Espresso)
- **Compose UI tests**: Compose UI Test JUnit4

## Architecture Notes

### Compose-first application
This is a pure Compose application with no XML layouts. The MainActivity sets content using `setContent` and enables edge-to-edge display.

### Current implementation
The app currently contains a minimal Compose setup with:
- Single `MainActivity` as the entry point
- `Greeting` composable (simple text display with unused state variable)
- Material3 theme integration
- Edge-to-edge layout support

### Theme system
Custom theme files in `ui/theme/`:
- `Color.kt` - Color palette definitions
- `Type.kt` - Typography system
- `Theme.kt` - Main theme composable combining colors, typography, and Material3

## Development Notes

### Compile SDK configuration
The project uses an advanced `compileSdk` configuration with release API 36 and minor API level 1:
```kotlin
compileSdk {
    version = release(36) {
        minorApiLevel = 1
    }
}
```

### Java compatibility
- Source/target compatibility: Java 11
- This is appropriate for the Kotlin and Compose versions in use

### ProGuard
ProGuard is configured but disabled by default (`isMinifyEnabled = false`). Rules are in `proguard-rules.pro`.

### Compose compiler
Uses Kotlin Compose plugin (version 2.2.10) which handles Compose compiler configuration automatically without manual compiler dependencies.