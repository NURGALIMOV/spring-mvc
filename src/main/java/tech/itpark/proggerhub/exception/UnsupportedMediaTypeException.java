package tech.itpark.proggerhub.exception;

public class UnsupportedMediaTypeException extends RuntimeException {

    public UnsupportedMediaTypeException() {
        super();
    }

    public UnsupportedMediaTypeException(String message) {
        super(message);
    }

    public UnsupportedMediaTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnsupportedMediaTypeException(Throwable cause) {
        super(cause);
    }

    protected UnsupportedMediaTypeException(String message, Throwable cause, boolean enableSuppression,
                                            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
