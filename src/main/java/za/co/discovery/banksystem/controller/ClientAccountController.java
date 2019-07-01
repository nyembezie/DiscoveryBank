package za.co.discovery.banksystem.controller;

import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import za.co.discovery.banksystem.service.ClientAccountService;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Api(value="Client Account", description="Client Account Resource REST Endpoints")
@RequestMapping("/banksystem/api/clientaccount")
@RestController
public class ClientAccountController {

  private static final Logger LOG = LoggerFactory.getLogger(ClientAccountController.class);

  @Autowired
  private ClientAccountService clientAccountService;


  @GetMapping("clienttransactionaccounts")
  public @ResponseBody List<Object[]> getClientTransactionalAccount(@RequestParam @NotNull Integer clientId,
      @RequestParam("localdatetime") @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime localdatetime) {

    LOG.info("getClientTransactionalAccount: id={}, timeStamp={}", clientId, localdatetime);

    return this.clientAccountService.getClientTransactionalAccount(clientId);

  }

  @GetMapping("randconvertedbalances")
  public List<Object[]> getBalancesWithConvertedRandValues(@RequestParam @NotNull Integer clientId,
      @RequestParam("localdatetime") @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime localdatetime) throws Exception {

    LOG.info("getBalancesWithConvertedRandValues: id={}, localdatetime={}", clientId, localdatetime);

    return this.clientAccountService.getCurrencyAccountWithConvertedValues(clientId);
  }

  @GetMapping("withdraw")
  public Map<Double, Integer> withdrawCash(@RequestParam @NotNull Integer clientId,
      @RequestParam("localdatetime") @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime localdatetime,
      @RequestParam @NotNull Integer atmId, @RequestParam @NotNull String accountNumber,
      @RequestParam @NotNull Double amount) {

    LOG.info("withdrawCash: id={}, localdatetime={}, id={}, accountNumber={}, amount={} ",
        clientId, localdatetime, atmId, accountNumber, amount);

    return this.clientAccountService.withdrawCash(clientId, atmId, accountNumber, amount);

  }

}
