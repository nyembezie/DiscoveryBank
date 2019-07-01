package za.co.discovery.banksystem.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Table(name="CLIENT_SUB_TYPE")
@ApiModel
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ClientSubType {

  @Id
  @Column(name = "CLIENT_SUB_TYPE_CODE")
  private String clientSubTypeCode;

  @NotNull
  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name="CLIENT_SUB_TYPE_CODE", nullable = false)
  private ClientType clientType;

  @NotNull
  @Column(name = "DESCRIPTION")
  private String description;
}
