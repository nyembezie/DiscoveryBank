package za.co.discovery.banksystem.exception;

public class ActionNotAllowedException extends RuntimeException {

  public ActionNotAllowedException(String message) {
    super(message);
  }
}
