call build
rsync --del -R -Pzrlt target/dependency target/classes config/*.dev.* config/*.prd.* *.md config/agent/*.dev.* config/agent/*.prd.* helpdesk.sh net01@ibm:lumen-helpdesk/
