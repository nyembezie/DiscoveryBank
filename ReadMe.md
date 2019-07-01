# Bank balance and dispensing system

# set-up
- update, in application.properties file, where you want reports to be saved(see use case 4 & 5) 
    - use case 4: reporting.highestbalanceaccount.filepath
    - use case 5: reporting.aggregateposition.filepath

- mvn clean package

- navigate to /target 

- java -jar banksystem-0.0.1-SNAPSHOT.jar 

#url's
- swagger page : http://localhost:8080/swagger-ui.html

- h2 database console: http://localhost:8080/h2-console

#USE CASES
1. Display transactional accounts with balances
    - example request: http://localhost:8080/banksystem/api/clientaccount/clienttransactionaccounts?clientId=2&isClientLoggedIn=true&localdatetime=2019-06-30T00:00:00
    
2. Display currency accounts with converted Rand values
    - example request: http://localhost:8080/banksystem/api/clientaccount/randconvertedbalances?clientId=1&localdatetime=2019-06-30T00:00:00&isClientLoggedIn=true
    
3. Withdraw cash
    - example request: http://localhost:8080/banksystem/api/clientaccount/withdraw?clientId=1&localdatetime=2019-06-30T00:00:00&isClientLoggedIn=true&atmId=2&accountNumber=1053664521&amount=250


4. Reporting – Find the transactional account per client with the highest balance
    - example request: http://localhost:8080/banksystem/api/reporting/highestbalanceaccount

5. Reporting – Calculate aggregate financial position per client
    - example request: http://localhost:8080/banksystem/api/reporting/aggregateposition