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
@Table(name="CLIENT_TYPE")
@ApiModel
public class ClientType {

  @Id
  @Column(name = "CLIENT_TYPE_CODE")
  private String clientTypeCode;

  @NotNull
  @Column(name = "DESCRIPTION")
  private String description;
}
