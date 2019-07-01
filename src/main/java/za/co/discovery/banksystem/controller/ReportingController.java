package za.co.discovery.banksystem.controller;

import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import za.co.discovery.banksystem.service.ReportingService;

@Api(value="Reporting", description="Reporting Resource REST Endpoints")
@RequestMapping("/banksystem/api/reporting")
@RestController
public class ReportingController {

  @Autowired
  private ReportingService reportingService;


  @GetMapping("highestbalanceaccount")
  public void transactionalAccountWithHighestBalance() {
    reportingService.getTransactionalAccountWithHighestBalance();
  }

  @GetMapping("aggregateposition")
  public void clientAggregatePosition() {
    reportingService.getAggregatePositionPerClient();
  }
}
