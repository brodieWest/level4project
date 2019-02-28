[![Build Status](https://travis-ci.org/brodieWest/level4project.svg?branch=master)](https://travis-ci.org/brodieWest/level4project)
[![Coverage Status](https://coveralls.io/repos/github/brodieWest/level4project/badge.svg)](https://coveralls.io/github/brodieWest/level4project)

# Developing this project

A gradle wrapper is included along with the source code.

In any IDE supporting gradle e.g. Intelij, import as a gradle project and select the option to use the project's gradle wrapper.

Use the gradle run task to test the program in an IDE, not the standard java runner.

A JDK version 11 is required to run the program's test suit.

# Deployment

Two deployment versions are supported.

 * Windows executable with built in JRE, use the buildExe gradle task. This must be done on a windows machine as it relies on jlink to build a custom windows JDK.

 * Fat jar, use the customFatJar gradle task, the current version of the project is compatible with JDK 8, so you can change the sourceCompatibility in the build.gradle file depending on the runtime environment of the users' system.