package org.coastline.one.spring.model;

/**
 * @author zouhuajian
 * @date 2020/12/7
 */
public class Result<R> {

    public static int FAIL_CODE = 1;

    private boolean success;
    private int code;
    private String message;
    private R data;

    public static <R> Result<R> ofSuccess(R data) {
        return new Result<R>()
                .setSuccess(true)
                .setMessage("success")
                .setData(data);
    }

    public static <R> Result<R> ofSuccessMsg(String msg) {
        return new Result<R>()
                .setSuccess(true)
                .setMessage(msg);
    }

    public static <R> Result<R> ofFail(String msg) {
        Result<R> result = new Result<>();
        result.setSuccess(false);
        result.setCode(FAIL_CODE);
        result.setMessage(msg);
        return result;
    }


    public static <R> Result<R> ofThrowable(int code, Throwable throwable) {
        Result<R> result = new Result<>();
        result.setSuccess(false);
        result.setCode(code);
        result.setMessage(throwable.getClass().getName() + ", " + throwable.getMessage());
        return result;
    }

    public boolean isSuccess() {
        return success;
    }

    public Result<R> setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public int getCode() {
        return code;
    }

    public Result<R> setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Result<R> setMessage(String message) {
        this.message = message;
        return this;
    }

    public R getData() {
        return data;
    }

    public Result<R> setData(R data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return "Result{" +
                "success=" + success +
                ", code=" + code +
                ", msg='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
