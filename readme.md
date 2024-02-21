# Portreg 
Tjeneste som holder en oppdatert liste over norske havner.

### For lokal kjøring 
Start en postgres-base   
``docker run -it --name local-postgres -e POSTGRES_PASSWORD=postgres -p 5432:5432 postgres:16``

Koble til basen  
``docker exec -it local-postgres psql -U postgres``

Opprett skjema og tabell
``` sql
-- Opprett skjema 
CREATE SCHEMA portreg;

-- Installer PostgreSQL's Earthdistance Module
CREATE EXTENSION IF NOT EXISTS cube;
CREATE EXTENSION IF NOT EXISTS earthdistance;

-- Opprett tabell
CREATE TABLE portreg.Port (
LOCODE VARCHAR(255) PRIMARY KEY,
NAME VARCHAR(255) NOT NULL,
LAT decimal,
LON decimal
);
```
Start applikasjonen   
`mvn spring-boot:run`

Swagger dokumentasjon er tilgjengelig på   
http://localhost:8080/portreg/swagger-ui/index.html#/

### TODO
- [x] Lag helloWorld endepunt
- [x] Hent data fra locodes, map til entitet
- [x] Print ut alle som er havner (function = 1)
- [x] Konverter fra grader-minutter til lat/lon
- [x] Lagre til postgres database
- [x] GET endepunkt basert på LOCODE
- [x] GET for nærmeste havn, basert på en posisjon (lat/lon)
- [x] GET for N nærmeste havner gitt en posisjon (lat/lon)
- [x] Regn ut avstand til nærmeste havnen
- [x] Regn ut avstand til de nærmeste havnene 