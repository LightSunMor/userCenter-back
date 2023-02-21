package com.usercenterdemo.common;

/***
 *  返回结果工具类
 *      简化controller返回对象对象代码，拿到更下一层来写new
 * @author MorSun
 */
public class ResultUtils {
    /**
     *  成功返回工具
     * @param data
     * @param <T>
     * @return
     */
    public static <T> BaseResponse<T> success(T data)
    {
        return new BaseResponse<>(200,data,"success","");
    }

    public static BaseResponse success(ErrorCode errorCode)
    {
        return new BaseResponse(errorCode);
    }

    /**
     * 请求失败返回工具
     * @param errorCode
     * @return
     */
    public static BaseResponse error(ErrorCode errorCode){
        return new BaseResponse(errorCode);
    };

    /**
     * 请求失败返回工具
     * @param errorCode
     * @param description
     * @return
     */
    public static BaseResponse error(ErrorCode errorCode,String description){
        return new BaseResponse(errorCode,description);
    };
}
