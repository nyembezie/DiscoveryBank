package za.co.discovery.banksystem.util;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import za.co.discovery.banksystem.dto.BalancePerClientDto;
import za.co.discovery.banksystem.dto.FinancialPositionDto;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class CsvWriter {

  private static final Logger LOG = LoggerFactory.getLogger(CsvWriter.class);

  public static void printHighestBalancePerClient(String filePath, final List<BalancePerClientDto> perClientList) throws IOException {

    CSVPrinter csvPrinter = null;

    try {
      BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath));
      csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
          .withHeader(Constants.CSV_HIGHEST_BALANCE_PER_CLIENT_HEADER));

      for(BalancePerClientDto bpc: perClientList) {
        csvPrinter.printRecord(bpc.getClientId(), bpc.getSurname(), bpc.getClientAccountNumber(),
            bpc.getDescription(), bpc.getDisplayBalance());
      }

    } catch(IOException ex) {
      LOG.error("printHighestBalancePerClient: ", ex);
      throw ex;
    } finally {
      csvPrinter.flush();
    }

  }

  public static void printFinancialPositionPerClient(String filePath, List<FinancialPositionDto> positionDtos) throws IOException {

    CSVPrinter csvPrinter = null;

    try {
      BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath));
      csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
          .withHeader(Constants.CSV_FINANCIAL_POSITION_PER_CLIENT_HEADER));

      for(FinancialPositionDto fpd: positionDtos) {
        csvPrinter.printRecord(fpd.getFullName(), fpd.getLoanBalance(), fpd.getTransactionalBalance(), fpd.getNetPosition());
      }

    } catch(IOException ex) {
      LOG.error("printFinancialPositionPerClient: ", ex);
      throw ex;
    } finally {
      csvPrinter.flush();
    }

  }
}
