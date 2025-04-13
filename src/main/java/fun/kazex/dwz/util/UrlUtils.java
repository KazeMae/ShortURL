package fun.kazex.dwz.util;

import java.util.regex.Pattern;

/**
 * @Author: KazeMae
 * @Date: 2025/04/13 23:00
 * @Description: Url 校验
 **/
public class UrlUtils {

    private final static Pattern URL_REG = Pattern.compile("^(((ht|f)tps?):\\/\\/)?[\\w-]+(\\.[\\w-]+)+([\\w.,@?^=%&:/~+#-]*[\\w@?^=%&/~+#-])?$");

    public static boolean checkUrl(String url) {
        return URL_REG.matcher(url).matches();
    }
}
