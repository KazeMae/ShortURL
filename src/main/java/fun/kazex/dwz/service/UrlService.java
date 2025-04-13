package fun.kazex.dwz.service;

import org.springframework.scheduling.annotation.Async;

/**
 * @Author: KazeMae
 * @Date: 2025/04/13 22:17
 * @Description: 映射业务接口
 **/
public interface UrlService {
    String saveUrl(String longUrl, String originalUrl);

    String getLongUrlByShortUrl(String shortUrl);

    @Async
    void updateUrlViews(String shortUrl);
}
