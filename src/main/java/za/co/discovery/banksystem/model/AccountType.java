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
@Table(name="ACCOUNT_TYPE")
@ApiModel
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class AccountType {

  @Id
  @Column(name = "ACCOUNT_TYPE_CODE")
  private String accountTypeCode;

  @NotNull
  @Column(name = "DESCRIPTION")
  private String description;

  @Column(name = "TRANSACTIONAL")
  private Boolean transactional;


}
