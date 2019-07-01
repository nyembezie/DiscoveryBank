SELECT CONCAT(C.TITLE, ' ', C.NAME, ' ', C.SURNAME) CLIENT, TABLE1.LOAN_BALANCE,
TABLE2.TRANSACTIONAL_BALANCE, TABLE3.NET_POSITION  FROM CLIENT C
LEFT JOIN (
SELECT CA.CLIENT_ID CLIENT_ID1, SUM(CA.DISPLAY_BALANCE) LOAN_BALANCE
FROM CLIENT_ACCOUNT CA
WHERE CA.ACCOUNT_TYPE_CODE IN ('PLOAN', 'HLOAN')
GROUP BY CA.CLIENT_ID
) TABLE1
ON C.CLIENT_ID = TABLE1.CLIENT_ID1
LEFT JOIN (
SELECT CA.CLIENT_ID CLIENT_ID2, SUM(CA.DISPLAY_BALANCE) TRANSACTIONAL_BALANCE
FROM CLIENT_ACCOUNT CA JOIN ACCOUNT_TYPE AT
ON CA.ACCOUNT_TYPE_CODE = AT.ACCOUNT_TYPE_CODE
WHERE AT.TRANSACTIONAL = TRUE
GROUP BY CA.CLIENT_ID
) TABLE2
ON C.CLIENT_ID = TABLE2.CLIENT_ID2
LEFT JOIN (
SELECT CA.CLIENT_ID CLIENT_ID3, SUM(CA.DISPLAY_BALANCE) NET_POSITION
FROM CLIENT_ACCOUNT CA JOIN ACCOUNT_TYPE AT
ON CA.ACCOUNT_TYPE_CODE = AT.ACCOUNT_TYPE_CODE
WHERE CA.CURRENCY_CODE = 'ZAR'
GROUP BY CA.CLIENT_ID
) TABLE3 ON C.CLIENT_ID = TABLE3.CLIENT_ID3