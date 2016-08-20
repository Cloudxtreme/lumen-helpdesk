#!/bin/bash
SCRIPT_DIR="$(dirname $0)"
java -Xmx3g -Djava.awt.headless=true -cp $SCRIPT_DIR'/target/classes':$SCRIPT_DIR'/target/dependency/*' org.lskk.lumen.helpdesk.HelpdeskApp "$@"
