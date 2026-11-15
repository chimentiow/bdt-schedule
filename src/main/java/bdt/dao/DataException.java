package bdt.dao;

/** Неперевірюваний виняток рівня доступу до даних. */
public class DataException extends RuntimeException {
    public DataException(String message, Throwable cause) {
        super(message, cause);
    }
}
