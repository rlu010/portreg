# Portreg
Applikasjon som holder en oppdatert liste over UNLOCODES som har havnestatus.
Det er mulig å legge til, og fjerne hvilken nasjoner applikasjonen holder oversikt over havner til.

### For lokal kjøring

1. Start tjenestene med Docker Compose:
```sh
docker-compose up -d 
```

2. Start applikasjonen   
```sh
mvn spring-boot:run 
```

Swagger dokumentasjon er tilgjengelig på   
http://localhost:8080/portreg/swagger-ui/index.html#/ 

Interaktivt kart  
http://localhost:8080/portreg/map 

## Definitions 
https://service.unece.org/trade/locode/Service/LocodeColumn.htm#ChangeInd

according to the maintinence guides, locodes are released twice a year (july and december), but no specific dates are provided 
Thus this application refreshes the list at the end of the month
https://unece.org/trade/documents/2023/09/unlocode-maintenance-guidelines