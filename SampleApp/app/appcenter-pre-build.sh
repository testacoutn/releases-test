#!/usr/bin/env bash

echo "==================Running pre build script======================"

ANDROID_GRADLE_FILE=$APPCENTER_SOURCE_DIRECTORY/SampleApp/app/build.gradle

echo "*****environment variable******"
echo “VERSION=${VERSION}”


versioninbuildgradle=`cat $ANDROID_GRADLE_FILE | grep versionName | awk '{print $2}' | tr -d \"`
echo "******Versionname in build.gradle file"
echo $versioninbuildgradle



new_version=$VERSION.$APPCENTER_BUILD_ID

#modifying the versionname with new_version in build.gradle file.
sed -i "" 's/versionName "[^"]*"/versionName "'$new_version'"/g' $ANDROID_GRADLE_FILE

#Using appcenter api's updating the VERSION environment variable in appcenter build configuration.

export DATA=`curl -sX GET "https://api.appcenter.ms/v0.1/apps/testaccoutn/sampleapp/branches/master/config" -H "Content-Type: application/json" -H "X-API-Token: c105225fa414b9e5d5bcef925f400a5150af6610" |  jq '.environmentVariables += [{"name": "VERSION", "value":"'$new_version'"}]' -c`

curl -X PUT -d "$DATA" "https://api.appcenter.ms/v0.1/apps/testaccoutn/sampleapp/branches/master/config" -H "Content-Type: application/json" -H "X-API-Token: c105225fa414b9e5d5bcef925f400a5150af6610"
 
#curl -sX GET "https://api.appcenter.ms/v0.1/apps/testaccoutn/sampleapp/branches/master/config" -H "Content-Type: application/json" -H "X-API-Token: c105225fa414b9e5d5bcef925f400a5150af6610" | jq