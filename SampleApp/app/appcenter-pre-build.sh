#!/usr/bin/env bash
# This updates the version number to be the build id from app center + the shift up to the build number from the google play store
# Currently we don't shift the ios version number.
echo "==================its pre build======================"
PROJECT_NAME=Kidzo-andro

ANDROID_GRADLE_FILE=$APPCENTER_SOURCE_DIRECTORY/SampleApp/app/build.gradle

#INFO_PLIST_FILE=$APPCENTER_SOURCE_DIRECTORY/NameOfProjectOnAppCenter/ios/$PROJECT_NAME/Info.plist
versionName=1.2.1
VERSION_CODE=$APPCENTER_BUILD_ID

#echo "====$VERSION_CODE============"

echo "************************************"
echo "$ENVIRONMENT_VARIABLE"

echo "************************************"
echo "$JAVA_HOME"


sed -i "" 's/versionName "[^"]*"/versionName "'$versionName.$VERSION_CODE'"/g' $ANDROID_GRADLE_FILE

