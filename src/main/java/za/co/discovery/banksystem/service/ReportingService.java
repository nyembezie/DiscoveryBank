package za.co.discovery.banksystem.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import za.co.discovery.banksystem.dao.NativeRepository;
import za.co.discovery.banksystem.dto.BalancePerClientDto;
import za.co.discovery.banksystem.dto.FinancialPositionDto;
import za.co.discovery.banksystem.util.Constants;
import za.co.discovery.banksystem.util.CsvWriter;

import java.io.IOException;
import java.util.List;

@Service
public class ReportingService {

  private static final Logger LOG = LoggerFactory.getLogger(ReportingService.class);

  @Autowired
  private NativeRepository nativeRepository;

  @Autowired
  private Environment env;

  public void getAggregatePositionPerClient() {
    runAggregatePostionPerClient();
  }

  @Scheduled(cron = "${cron.monthly.expression}")
  private void runAggregatePostionPerClient() {
    LOG.info(" getAggregatePositionPerClient ****** ");
    List<FinancialPositionDto> result = nativeRepository.calculateClientFinancialPosition();

    try {
      CsvWriter.printFinancialPositionPerClient(env.getProperty("reporting.aggregateposition.filepath"), result);
      EmailService.sendEmail(Constants.SYSTEM_EMAIL_ADDRESS, Constants.REPORTS_EMAIL_ADDRESS,
          "Aggregate Financial Position per Client", null, env.getProperty("reporting.aggregateposition.filepath"));
    } catch(IOException ioException) {
      LOG.error("getAggregatePositionPerClient: ", ioException);
    }
  }


  public void getTransactionalAccountWithHighestBalance() {
    runHighestAccountBalance();
  }

  @Scheduled(cron = "${cron.monthly.expression}")
  private void runHighestAccountBalance() {
    LOG.info(" getTransactionaAccountWithHighestBalance ****** ");
    List<BalancePerClientDto> result = nativeRepository.calculateHighestTransactionalAccountBalancePerClient();

    try {
      CsvWriter.printHighestBalancePerClient(env.getProperty("reporting.highestbalanceaccount.filepath"), result);
      EmailService.sendEmail(Constants.SYSTEM_EMAIL_ADDRESS, Constants.REPORTS_EMAIL_ADDRESS,
          "Highest Transactional Account Balance per Client", null, env.getProperty("reporting.highestbalanceaccount.filepath"));
    } catch(IOException ioException) {
      LOG.error("getTransactionalAccountWithHighestBalance: ", ioException);
    }
  }

}
