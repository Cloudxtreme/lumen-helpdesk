#!/bin/bash
# we don't build the WAR because deploying a compressed WAR takes too much time
# better to build a standard stuff
./build.sh
rsync --del -R -Pzrlt target/dependency target/classes config/*.dev.* config/*.prd.* *.md helpdesk.sh net01@ibm:lumen-helpdesk/
