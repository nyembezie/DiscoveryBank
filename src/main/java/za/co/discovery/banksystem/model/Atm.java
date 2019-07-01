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
@Table(name="ATM")
@ApiModel
public class Atm {

  @Id
  @Column(name = "ATM_ID")
  private Integer id;

  @NotNull
  @Column(name = "NAME")
  private String name;

  @NotNull
  @Column(name = "LOCATION")
  private String location;
}
