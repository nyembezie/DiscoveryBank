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

@Entity
@NoArgsConstructor
@Data
@Table(name="DENOMINATION")
@ApiModel
public class Denomination {

  @Id
  @Column(name = "DENOMINATION_ID")
  private Integer denominationId;

  @NotNull
  @Column(name = "VALUE")
  private Double value;

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "DENOMINATION_TYPE_CODE")
  private DenominationType denominationType;
}
