#!/usr/bin/env bash
# Ejecuta la suite en Android sobre la nube de LambdaTest.
#   export LT_USERNAME=tu_usuario
#   export LT_ACCESS_KEY=tu_access_key
#   ./run-android.sh
set -e

: "${LT_USERNAME:?Falta exportar LT_USERNAME}"
: "${LT_ACCESS_KEY:?Falta exportar LT_ACCESS_KEY}"

HUB="https://${LT_USERNAME}:${LT_ACCESS_KEY}@mobile-hub.lambdatest.com/wd/hub"

./gradlew clean test aggregate \
  -Dproperties=serenity.properties \
  -Dappium.hub="${HUB}" \
  --tests "*AndroidRunner"
