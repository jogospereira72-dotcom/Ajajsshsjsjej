#!/bin/sh

APP_HOME=$(CDPATH= cd -- "$(dirname -- "$0")" && pwd -P)
CLASSPATH=$APP_HOME/gradle/wrapper/gradle-wrapper.jar

if [ ! -f "$CLASSPATH" ]; then
  echo "Gradle Wrapper jar ausente; execute com Gradle instalado ou copie gradle/wrapper/gradle-wrapper.jar." >&2
  exit 1
fi

exec "${JAVA_HOME:+$JAVA_HOME/bin/}java" -classpath "$CLASSPATH" org.gradle.wrapper.GradleWrapperMain "$@"
