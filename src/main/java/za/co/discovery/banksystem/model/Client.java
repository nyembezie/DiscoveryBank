package za.co.discovery.banksystem.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Data
@Table(name="CLIENT")
@ApiModel
public class Client {

  @Id
  @Column(name = "CLIENT_ID")
  private Integer id;

  @Column(name = "TITLE")
  private String title;

  @NotNull
  @Column(name = "NAME")
  private String name;

  @Column(name = "SURNAME")
  private String surname;

  @NotNull
  @Column(name = "DOB")
  private Date dob;

//  @JsonIgnore
  @NotNull
  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name="CLIENT_SUB_TYPE_CODE")
  private ClientSubType clientSubType;
}
