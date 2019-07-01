# Bank balance and dispensing system

## set-up

- mvn clean package

- navigate to /target 

- java -jar banksystem-0.0.1-SNAPSHOT.jar 

## url's
- swagger page : http://localhost:8080/swagger-ui.html

- h2 database console: http://localhost:8080/h2-console

## use cases
1. Display transactional accounts with balances
    - example request: http://localhost:8080/banksystem/api/clientaccount/clienttransactionaccounts?clientId=2&localdatetime=2019-06-30T00:00:00
    
2. Display currency accounts with converted Rand values
    - example request: http://localhost:8080/banksystem/api/clientaccount/randconvertedbalances?clientId=1&localdatetime=2019-06-30T00:00:00
    
3. Withdraw cash
    - example request: http://localhost:8080/banksystem/api/clientaccount/withdraw?clientId=1&localdatetime=2019-06-30T00:00:00&atmId=2&accountNumber=1053664521&amount=250


4. Reporting – Find the transactional account per client with the highest balance
    - src/main/resources/static/highestTransactionalAccountBalancePerClient.sql

5. Reporting – Calculate aggregate financial position per client
    - src/main/resources/static/aggregateFinancialPositionPerClient.sql