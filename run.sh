#!/usr/bin/env bash

./gradlew assembleDebug
adb install -r app/build/outputs/apk/app-debug.apk
adb shell am start -n cn.yueying0083.pediyforum/cn.yueying0083.pediyforum.MainActivity