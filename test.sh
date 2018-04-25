#!/bin/bash

# A simple shell script that tests the Black Duck Eclipse plugin solution.
# Passes maven args through to both reactor builds

# Due to Tycho limitations, pom-first Maven dependencies must be resolved in a
# separate reactor first...
echo "Wrapping solution dependencies into OSGI bundle..."
cd ./bundles/blackduck-eclipse-plugin-maven-dependencies/
mvn clean install $@
rc=$?
if [[ $rc -ne 0 ]] ; then
  echo "Could not create dependencies OSGI bundle."; exit $rc
fi
echo "Solution dependency OSGI bundle created."

# ...then we can build the rest of the project.
echo "Building and testing solution..."
cd ../../
mvn clean verify $@
rc=$?
if [[ $rc -ne 0 ]] ; then
  echo "Solution build and test failed."; exit $rc
fi
echo "Solution was built and tested successfully."