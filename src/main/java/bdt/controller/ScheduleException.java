package bdt.controller;

/** Порушення правил складання розкладу або обліку занять (повідомлення для користувача). */
public class ScheduleException extends RuntimeException {
    public ScheduleException(String message) {
        super(message);
    }
}
