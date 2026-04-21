package com.sudoku.model;

/**
 * @author TheintSandi
 * @ImplNote Represents the result of a controller action.
 * Tells the View what to display and what to do next.
 */
public class Result {

    public enum Status {
        SUCCESS, FAILURE, QUIT, GAME_OVER
    }

    private final Status status;
    private final String message;

    private Result(Status status, String message) {
        this.status  = status;
        this.message = message;
    }

    public static Result success(String message) {
        return new Result(Status.SUCCESS, message);
    }

    public static Result failure(String message) {
        return new Result(Status.FAILURE, message);
    }

    public static Result quit(String message) {
        return new Result(Status.QUIT, message);
    }

    public static Result gameOver(String message) {
        return new Result(Status.GAME_OVER, message);
    }

    public Status getStatus()   { return status; }
    public String getMessage()  { return message; }

    public boolean isQuit()     { return status == Status.QUIT; }
    public boolean isGameOver() { return status == Status.GAME_OVER; }
    public boolean isSuccess()  { return status == Status.SUCCESS; }
}