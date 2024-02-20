# README 

- [x] Lag helloWorld endepunt
- [x] Hent data fra locodes, map til entitet
- [x] Print ut alle som er havner (function = 1) 
- [x] Konverter fra grader-minutter til lat/lon 
- [ ] Lagre til postgres database 
- [ ] GET endepunkt basert på LOCODE 
- [ ] GET for nærmeste port, basert på en possisjon (lat/lon) 
- [ ] GET for n nærmeste ports gitt en possisjon (lat/lon)


### For lokal kjøring 
Start en postgres-base   
``docker run -it --name local-postgres -e POSTGRES_PASSWORD=postgres -p 5432:5432 postgres:16``

Koble til basen  
``docker exec -it blissful_knuth psql -U postgres``

Opprett skjema og tabell
``` sql
-- Opprett skjema 
CREATE SCHEMA portreg;

-- Opprett tabell
CREATE TABLE portreg.Port (
LOCODE VARCHAR(255) PRIMARY KEY,
NAME VARCHAR(255) NOT NULL,
LAT decimal,
LON decimal
);
```
