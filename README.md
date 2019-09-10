# Conichi Challenge

## API features

* Current ISO timestamp and human-readable timestamp on `/time`
* Vat validation on `/api/vat/validate`
* Currency conversion on `/api/currency/convert`

#### Third party APIs being used:

* Vat Country Code extraction & Validation: https://vatlayer.com/
* Currency conversion: http://free.currencyconverterapi.com/

## Implementation decisions. 

* Java 8 and Spring for backend
* H2 in-memory db for caching
* For currency exchange rates, 15 minutes of expire time in order to reduce # of requests

## How to start the application:

### Requirements:

* Java 8

### Run: 

* Execute `mvn spring-boot:run` in the project folder

#### (Alternative) Running by docker

* Build jar file

`mvn package`

* Build the docker image

`docker build -t conichi .`

* Run docker image

`sudo docker run -p 8081:8081 -d conichi`

## Endpoints

##### `GET /time`

Example: `GET http://localhost:8081/time`

##### `GET /api/currency/convert`

Example: `GET http://localhost:8081/api/currency/convert?source_currency=TRY&target_currency=USD&amount=12345`

##### `GET /api/vat/validate`

Example: `GET http://localhost:8081/api/vat/validate?vat_number=LU26375245`