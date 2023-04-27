#!/bin/bash

./gradlew clean build shadowJar
cp ./build/libs/uc-all.jar ./uc.jar
