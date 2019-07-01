package za.co.discovery.banksystem.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@NoArgsConstructor
@Data
@Table(name="CLIENT_ACCOUNT")
@ApiModel
public class ClientAccount {

  @Id
  @Column(name = "CLIENT_ACCOUNT_NUMBER")
  private String clientAccountNumber;

  @NotNull
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name="CLIENT_ID")
  private Client client;

  @NotNull
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name="ACCOUNT_TYPE_CODE")
  private AccountType accountType;

  @NotNull
  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name="CURRENCY_CODE")
  private Currency currency;

  @Column(name = "DISPLAY_BALANCE")
  private Double displayBalance;
}
