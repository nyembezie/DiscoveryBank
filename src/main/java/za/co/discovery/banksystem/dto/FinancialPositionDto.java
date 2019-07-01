package za.co.discovery.banksystem.dto;

import lombok.Data;

@Data
public class FinancialPositionDto {

  private String fullName;

  private Double loanBalance;

  private Double transactionalBalance;

  private Double netPosition;
}
