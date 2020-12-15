package zee.library.arouter.exception;

public class NoRouterFoundException extends RuntimeException {

    public NoRouterFoundException(String detailMessage) {
        super(detailMessage);
    }
}