#!/usr/bin/env sh

curl --location --request POST 'localhost:8080/add' \
--header 'Content-Type: application/json' \
--data-raw '{ "payer": "DANNON", "points": 1000, "timestamp": "2020-12-02T14:00:00Z" }'

curl --location --request POST 'localhost:8080/add' \
--header 'Content-Type: application/json' \
--data-raw '{ "payer": "UNILEVER", "points": 200, "timestamp": "2020-10-31T11:00:00Z" }'

curl --location --request POST 'localhost:8080/add' \
--header 'Content-Type: application/json' \
--data-raw '{ "payer": "DANNON", "points": -200, "timestamp": "2020-10-31T15:00:00Z" }'

curl --location --request POST 'localhost:8080/add' \
--header 'Content-Type: application/json' \
--data-raw '{ "payer": "MILLER COORS", "points": 10000, "timestamp": "2020-11-01T14:00:00Z" }'

curl --location --request POST 'localhost:8080/add' \
--header 'Content-Type: application/json' \
--data-raw '{ "payer": "DANNON", "points": 300, "timestamp": "2020-10-31T10:00:00Z" }'

curl --location --request POST 'localhost:8080/spend' \
--header 'Content-Type: application/json' \
--data-raw '{ "points": "5000"}'

#print the balances
curl --location --request GET 'localhost:8080/balance'