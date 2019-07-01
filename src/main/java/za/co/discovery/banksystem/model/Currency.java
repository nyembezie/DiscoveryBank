package za.co.discovery.banksystem.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Table(name="CURRENCY")
@ApiModel
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Currency {

  @Id
  @Column(name = "CURRENCY_CODE")
  private String currencyCode;

  @NotNull
  @Column(name = "DECIMAL_PLACES")
  private Integer decimalPlaces;

  @NotNull
  @Column(name = "DESCRIPTION")
  private String description;

}
