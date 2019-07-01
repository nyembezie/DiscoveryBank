package za.co.discovery.banksystem.util;

public class Constants {

  public static final String AUTH_EXCEPTION_MESSAGE = "Client not authenticated";

  public static final Double MAXIMUM_NEGATIVE_BALANCE = 10000.00;

  public static final String INSUFFICIENT_FUNDS = "Insufficient funds";

  public static final String ATM_EXCEPTION_MESSAGE = "ATM not registered or unfunded";

  public static final String ATM_AMOUNT_MESSAGE = "Amount not available, would you like to draw %f";

  public static final String CSV_HIGHEST_BALANCE_PER_CLIENT_HEADER[] = {"CLIENT ID", "CLIENT SURNAME", "CLIENT ACCOUNT NUMBER", "ACCOUNT DESCRIPTION", "DISPLAY BALANCE"};

  public static final String CSV_FINANCIAL_POSITION_PER_CLIENT_HEADER[] = {"FULL NAME", "LOAN BALANCE", "TRANSACTIONAL BALANCE", "NET POSITION"};

  public static final String REPORTS_EMAIL_ADDRESS = "support@thebank.com";

  public static final String SYSTEM_EMAIL_ADDRESS = "banksystem@thebank.com";

  public static final String NO_ACCOUNTS_MESSAGE = "No accounts to display";
}
