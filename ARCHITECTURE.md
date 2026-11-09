# JARVIS: Architecture

Package: `com.jarvis.ai` | Kotlin 1.9.24 | Jetpack Compose | MVVM | minSdk 26 | target/compile SDK 34

## Layers

| Layer | Package | Phase |
|---|---|---|
| UI (Compose screens, components, theme) | `ui/` | 1 |
| ViewModel (state, MVVM) | `MainViewModel.kt` | 1 |
| Device status (online/battery) | `core/DeviceStatus.kt` | 1 |
| App launcher | `core/AppLauncher.kt` | 1 |
| AI engine + Gemini service (OkHttp) | `ai/` | 2 |
| Settings + secure key storage (DataStore + Android Keystore) | `settings/` | 2 |
| Voice engine (SpeechRecognizer, TextToSpeech) | `voice/` | 3 |
| Automation engine (Intents, AccessibilityService, NotificationListener, MediaSession) | `automation/` | 4-5 |
| Camera / gallery / files (SAF) | `media/`, `files/` | 6 |
| Coding assistant | `coding/` | 7 |
| Memory manager + Room database | `memory/`, `data/` | 8 |
| Permission manager | `permissions/` | 9 |

## Folder structure

```
jarvis-ai/
  settings.gradle.kts
  build.gradle.kts
  gradle.properties
  ARCHITECTURE.md  PROJECT_STATUS.md  BUILD_INSTRUCTIONS.md
  .github/workflows/build-apk.yml
  app/
    build.gradle.kts
    proguard-rules.pro
    src/main/
      AndroidManifest.xml
      res/ (values, drawable, mipmap-anydpi-v26)
      java/com/jarvis/ai/
        MainActivity.kt  JarvisApp.kt  MainViewModel.kt
        core/   ChatMessage.kt  DeviceStatus.kt  AppLauncher.kt
        ui/theme/       Color.kt  Theme.kt
        ui/components/  GlassCard.kt  AvatarOrb.kt  Waveform.kt  Common.kt
        ui/screens/     Home Chat Voice PhoneControl Social Coding Memory Settings
```

## Dependencies (Phase 1)

- AGP 8.5.2, Gradle 8.7, Kotlin 1.9.24, Compose compiler 1.5.14
- Compose BOM 2024.06.00 (ui, material3, material-icons-extended)
- androidx.core-ktx 1.13.1, lifecycle 2.8.4, activity-compose 1.9.1

Later phases add: OkHttp, Room (+KSP), DataStore, security-crypto.

## Design rules

- Every sensitive action (call, SMS, share, delete) needs on-screen user confirmation.
- Permissions are requested only when the user turns a feature on.
- API key is never in source; stored encrypted on-device. A production app should call Gemini through your own backend so the key cannot be extracted from the APK.
- ONLINE/OFFLINE comes from real network state; the app never pretends Gemini works offline.
