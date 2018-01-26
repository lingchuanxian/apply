package cn.fjlcx.application.core;

/**
 * 响应结果生成工具
 */
public class ResultGenerator {
    private static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";

    public static Result genSuccessResult() {
        return new Result()
                .setCode(ResultCode.SUCCESS)
                .setMessage(DEFAULT_SUCCESS_MESSAGE);
    }

    public static Result genSuccessResult(Object data) {
        return new Result()
                .setCode(ResultCode.SUCCESS)
                .setMessage(DEFAULT_SUCCESS_MESSAGE)
                .setData(data);
    }

    public static Result genFailResult(String message) {
        return new Result()
                .setCode(ResultCode.FAIL)
                .setMessage(message);
    }
    
    public static Result genNoAuthorityResult() {
        return new Result()
                .setCode(ResultCode.NOAUTHORITY)
                .setMessage("您暂无权限访问，请联系管理员");
    }
    
    public static Result genUnauthorizedResult() {
        return new Result()
                .setCode(ResultCode.UNAUTHORIZED)
                .setMessage("登录超时，请重新登录");
    }
}
