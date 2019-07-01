package za.co.discovery.banksystem.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@NoArgsConstructor
@Data
@Table(name="CURRENCY_CONVERSION_RATE")
@ApiModel
public class CurrencyConversionRate {

  @Id
  @Column(name = "CURRENCY_CODE")
  private String currencyCode;

  @NotNull
  @Column(name = "CONVERSION_INDICATOR")
  private String conversionIndicator;

  @NotNull
  @Column(name = "RATE")
  private Double rate;

}
