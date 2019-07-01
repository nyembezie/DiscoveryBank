package za.co.discovery.banksystem.dto;

import lombok.Data;

@Data
public class BalancePerClientDto {

  private Integer clientId;

  private String surname;

  private String clientAccountNumber;

  private String description;

  private Double displayBalance;
}
