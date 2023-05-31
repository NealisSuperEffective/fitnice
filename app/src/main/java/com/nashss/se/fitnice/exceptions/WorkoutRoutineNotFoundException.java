package com.nashss.se.fitnice.exceptions;

public class WorkoutRoutineNotFoundException extends RuntimeException{
    private static final long serialVersionUID = -912326717789387971L;

    /**
     * Exception with no message or cause.
     */
    public WorkoutRoutineNotFoundException() {
        super();
    }

    /**
     * Exception with a message, but no cause.
     * @param message A descriptive message for this exception.
     */
    public WorkoutRoutineNotFoundException(String message) {
        super(message);
    }

    /**
     * Exception with no message, but with a cause.
     * @param cause The original throwable resulting in this exception.
     */
    public WorkoutRoutineNotFoundException(Throwable cause) {
        super(cause);
    }

    /**
     * Exception with message and cause.
     * @param message A descriptive message for this exception.
     * @param cause The original throwable resulting in this exception.
     */
    public WorkoutRoutineNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
