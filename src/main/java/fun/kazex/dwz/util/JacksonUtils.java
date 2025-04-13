package fun.kazex.dwz.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: KazeMae
 * @Date: 2025/04/14 04:03
 * @Description:
 **/
@Slf4j
public class JacksonUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String writeValueAsString(Object value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
            return "";
        }
    }

    public static <T> T convertValue(Object fromValue, Class<T> toValueType) {
        return objectMapper.convertValue(fromValue, toValueType);
    }
}

