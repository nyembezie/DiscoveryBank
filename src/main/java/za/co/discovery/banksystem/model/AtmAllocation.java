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
@Table(name="ATM_ALLOCATION")
@ApiModel
public class AtmAllocation {

  @Id
  @Column(name = "ATM_ALLOCATION_ID")
  private Integer id;

  @NotNull
  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name="ATM_ID")
  private Atm atm;

  @NotNull
  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name="DENOMINATION_ID")
  private Denomination denomination;

  @NotNull
  @Column(name = "COUNT")
  private Integer count;
}
