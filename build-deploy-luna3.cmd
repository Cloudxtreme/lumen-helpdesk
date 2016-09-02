call build
rsync --del -R -Pzrlt target/dependency target/classes config/*.dev.* config/*.prd.* config/agent/*.dev.* config/agent/*.prd.* *.md helpdesk.sh ceefour@luna3.bippo.co.id:lumen-helpdesk/
