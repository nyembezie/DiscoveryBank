package za.co.discovery.banksystem.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@NoArgsConstructor
@Data
@Table(name="CREDIT_CARD_LIMIT")
@ApiModel
public class CreditCardLimit implements Serializable {

  @Id
  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name="CLIENT_ACCOUNT_NUMBER")
  private ClientAccount clientAccount;

  @NotNull
  @Column(name = "ACCOUNT_LIMIT")
  private Double accountLimit;
}
