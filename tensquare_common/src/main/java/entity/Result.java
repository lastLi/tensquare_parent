package entity;

import lombok.Data;
/**
 *公共的返回Json数据类
 */
@Data
public class Result {

    /**正确的数据就是true,别的就是false*/
    private boolean flag;

    /**状态码*/
    private Integer code;

    /**返回消息*/
    private String message;

    /**返回数据*/
    private Object data;

    public Result(boolean flag, Integer code, String message) {
        this.flag = flag;
        this.code = code;
        this.message = message;
    }
}
