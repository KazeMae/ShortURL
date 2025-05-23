package fun.kazex.dwz.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author: KazeMae
 * @Date: 2025/04/13 22:18
 * @Description: 响应封装
 **/
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Result {
    @JsonProperty("code")
    private Integer code;
    @JsonProperty("msg")
    private String msg;
    @JsonProperty("data")
    private String data;

    private Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
        this.data = null;
    }

    private Result(Integer code, String msg, String data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static Result ok(String msg, String data) {
        return new Result(200, msg, data);
    }

    public static Result create(Integer code, String msg) {
        return new Result(code, msg);
    }
}
