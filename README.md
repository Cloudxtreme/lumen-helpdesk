# Lumen Helpdesk

## Building and Running

1. in PostgreSQL, pgAdmin III Create `lumen_lumen_dev` database
2. In `config` folder (not in `src\main\resources` folder), copy `application.dev.properties` to `application.properties` (make it)
3. If you use proxy, you need to edit `application.properties` and enter your proxy address+username+password, from `http.proxyHost` to `https.proxyPort` delete "#" , 
    enter your `spring.datasource.username` and `spring.datasource.password`,  delete "#" ,
4. In `config/agent` folder, copy `(agentId).AgentSocialConfig.dev.json` to `(agentId).AgentSocialConfig.json` (make it) , agent ID : example "arkan"
5. In `config/agent` folder, copy `(agentId).TwitterAuthorization.dev.jsonld` to `(agentId).TwitterAuthorization.jsonld` (make it) , agent ID : example "arkan"
6. click menu `run` choose `edit configuration` in working directory fill with `$MODULE_DIR$` , click ok
7. click reasoner>src>main>java>right click in `HelpdeskApp` choose Run
