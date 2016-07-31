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

## Populate the district and general_hospital tables

1. Open Command Prompt
2. `cd` to your `git/lumen-helpdesk` folder
3. Run `psql` e.g. for local development: `psql -hlocalhost -Upostgres lumen_lumen_dev`
4. Inside `psql`, type:

\copy lumen.district (province_id, province_name, city_id, city_name, id, name, geometry) FROM 'init/kecamatan.all.csv' (FORMAT CSV, HEADER true);

\copy lumen.general_hospital (id, name, kind, location_address, location_lat, location_lon, postal_code, phone0, phone1, phone2, faximile0, faximile1, website, email, city_id, district_id, village_id, lat, lon, geometry) FROM 'init/rumahsakitumum.all.csv' (FORMAT CSV, HEADER true);

## REST API

Access via http://localhost:8080/
