package za.co.discovery.banksystem.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import za.co.discovery.banksystem.dto.BalancePerClientDto;
import za.co.discovery.banksystem.dto.FinancialPositionDto;

import java.util.List;

@Component
public class NativeRepository {

  @Autowired
  private NamedParameterJdbcTemplate jdbcTemplate;

  public List<FinancialPositionDto> calculateClientFinancialPosition() {

    MapSqlParameterSource parameters = new MapSqlParameterSource();

    String sql = "SELECT CONCAT(C.TITLE, C.NAME, C.SURNAME) FULLNAME, TABLE1.LOAN_BALANCE,\n"
        + "TABLE2.TRANSACTIONAL_BALANCE, TABLE3.NET_POSITION  FROM CLIENT C\n" + "LEFT JOIN (\n"
        + "SELECT CA.CLIENT_ID CLIENT_ID1, SUM(CA.DISPLAY_BALANCE)  LOAN_BALANCE\n" + "FROM CLIENT_ACCOUNT CA\n"
        + "WHERE CA.ACCOUNT_TYPE_CODE IN ('PLOAN', 'HLOAN')\n" + "GROUP BY CA.CLIENT_ID\n" + ") TABLE1\n" + "ON C.CLIENT_ID = TABLE1.CLIENT_ID1\n"
        + "LEFT JOIN (\n" + "SELECT CA.CLIENT_ID CLIENT_ID2, SUM(CA.DISPLAY_BALANCE) TRANSACTIONAL_BALANCE\n" + "FROM CLIENT_ACCOUNT CA JOIN ACCOUNT_TYPE AT\n"
        + "ON CA.ACCOUNT_TYPE_CODE = AT.ACCOUNT_TYPE_CODE\n" + "WHERE AT.TRANSACTIONAL = TRUE\n" + "GROUP BY CA.CLIENT_ID\n" + ") TABLE2\n"
        + "ON C.CLIENT_ID = TABLE2.CLIENT_ID2\n" + "LEFT JOIN (\n" + "SELECT CA.CLIENT_ID CLIENT_ID3, SUM(CA.DISPLAY_BALANCE) NET_POSITION\n"
        + "FROM CLIENT_ACCOUNT CA JOIN ACCOUNT_TYPE AT\n" + "ON CA.ACCOUNT_TYPE_CODE = AT.ACCOUNT_TYPE_CODE\n" + "GROUP BY CA.CLIENT_ID\n"
        + ") TABLE3 ON C.CLIENT_ID = TABLE3.CLIENT_ID3";

    return jdbcTemplate.query(sql, parameters, BeanPropertyRowMapper.newInstance(FinancialPositionDto.class));
  }


  public List<BalancePerClientDto> calculateHighestTransactionalAccountBalancePerClient() {

    MapSqlParameterSource parameters = new MapSqlParameterSource();

    String sql = "SELECT C.CLIENT_ID, C.SURNAME, CA.CLIENT_ACCOUNT_NUMBER, AT.DESCRIPTION, CA.DISPLAY_BALANCE\n" + "FROM CLIENT C\n"
        + "JOIN CLIENT_ACCOUNT CA ON C.CLIENT_ID = CA.CLIENT_ID\n" + "JOIN ACCOUNT_TYPE AT ON AT.ACCOUNT_TYPE_CODE = CA.ACCOUNT_TYPE_CODE\n" + "JOIN (\n"
        + "SELECT C.CLIENT_ID ID, MAX(CA.DISPLAY_BALANCE) MAX_BALANCE\n" + "FROM CLIENT C\n" + "JOIN CLIENT_ACCOUNT CA ON C.CLIENT_ID = CA.CLIENT_ID\n"
        + "JOIN ACCOUNT_TYPE AT ON AT.ACCOUNT_TYPE_CODE = CA.ACCOUNT_TYPE_CODE\n" + "WHERE AT.TRANSACTIONAL  = TRUE\n" + "GROUP BY C.CLIENT_ID ) TABLE1\n"
        + "ON C.CLIENT_ID = TABLE1.ID\n" + "AND CA.DISPLAY_BALANCE = TABLE1.MAX_BALANCE\n" + "ORDER BY 1 DESC";

    return jdbcTemplate.query(sql, parameters, BeanPropertyRowMapper.newInstance(BalancePerClientDto.class));

  }


}
