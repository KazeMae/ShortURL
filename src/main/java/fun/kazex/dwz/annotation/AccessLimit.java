package fun.kazex.dwz.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: KazeMae
 * @Date: 2025/04/14 03:57
 * @Description: 访问控制
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AccessLimit {
    // 限制周期
    int seconds();

    // 规定周期内限制次数
    int maxCount();

    // 触发限制时的消息提示
    String msg() default "操作频率过高";
}
