package xyz.necmettincimen.marvel.marvel.common;

public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T result;

    public ApiResponse() {
        super();
    }

    public ApiResponse(boolean success, String msg, T result) {
        this.success = success;
        this.message = msg;
        this.result = result;
    }

    public ApiResponse(T result) {
        this.success = true;
        this.result = result;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
