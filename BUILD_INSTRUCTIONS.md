# BUILD INSTRUCTIONS

## Honest note about Termux

Building Android apps directly inside Termux is unreliable: Google's official Android SDK
build tools (aapt2, etc.) are made for x86 Linux/Windows/macOS, not for the ARM Termux
environment, so Gradle often fails there. The simplest reliable way from a phone is
**GitHub Actions**: GitHub builds the APK on its servers and you download it.
Termux is still used here for `git` to upload the project.

## Method A (recommended): GitHub Actions, phone only

1. Create a free account at https://github.com and make a **new empty repository** named `jarvis-ai`
   (Private is fine).
2. In Termux:

```bash
pkg update -y && pkg upgrade -y
pkg install -y git unzip
termux-setup-storage          # allow storage access when asked
cd ~
cp ~/storage/downloads/jarvis-ai.zip .   # wherever you saved the zip
unzip jarvis-ai.zip
cd jarvis-ai
git init
git branch -M main
git add .
git commit -m "JARVIS phase 1"
git remote add origin https://github.com/YOUR_USERNAME/jarvis-ai.git
git push -u origin main
```

   When asked for a password, use a GitHub **Personal Access Token**
   (GitHub > Settings > Developer settings > Tokens, tick `repo` and `workflow`).
3. Open the repo on GitHub > **Actions** tab > "Build JARVIS APK". It starts on push
   (or press **Run workflow**). It takes about 5-8 minutes.
4. Open the finished run > **Artifacts** > download `jarvis-ai-debug-apk` (a zip) > extract
   `app-debug.apk`.

## Method B: Android Studio on a PC

1. Install Android Studio, choose **Open**, select the `jarvis-ai` folder, let Gradle sync.
2. **Build > Build APK(s)**. Output: `app/build/outputs/apk/debug/app-debug.apk`.

## Method C: Command line on a PC with JDK 17 + Android SDK

```bash
gradle assembleDebug      # needs Gradle 8.7+
```

APK output: `app/build/outputs/apk/debug/app-debug.apk`

(The Gradle wrapper jar can't be generated without Gradle. If you have Gradle once,
run `gradle wrapper --gradle-version 8.7` to create `gradlew`.)

## Installing the APK on your phone

1. Copy/download `app-debug.apk` to your phone.
2. Tap it. If asked, allow "Install unknown apps" for your file manager or browser.
3. Install and open **JARVIS**.

## If the build fails

Copy the red error text from the Actions log and send it to me. I will fix it before Phase 2.
