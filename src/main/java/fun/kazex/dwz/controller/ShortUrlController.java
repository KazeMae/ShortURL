package fun.kazex.dwz.controller;

import fun.kazex.dwz.annotation.AccessLimit;
import fun.kazex.dwz.entity.LongUrlDTO;
import fun.kazex.dwz.entity.Result;
import fun.kazex.dwz.service.UrlService;
import fun.kazex.dwz.util.UrlUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: KazeMae
 * @Date: 2025/04/13 22:16
 * @Description: 短链接Controller
 **/
@RestController
@RequestMapping(value = "", produces =  "application/json;charset=UTF-8")
public class ShortUrlController {

    private final UrlService urlService;

    @Value("${server.host}")
    private String host;

    public ShortUrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @AccessLimit(seconds = 1, maxCount = 10, msg = "1 秒内只能生成 10 次短链接")
    @PostMapping("/generate")
    @ResponseBody
    public Result generateShortUrl(@RequestBody LongUrlDTO longUrlDTO) {
        String longUrl = longUrlDTO.getLongUrl();
        if (UrlUtils.checkUrl(longUrl)) {
            if (!longUrl.startsWith("http")) {
                longUrl = "http://" + longUrl;
            }
            String shortUrl = urlService.saveUrl(longUrl, longUrl);
            return Result.ok("请求成功", host + shortUrl);
        } else {
            return Result.create(400, "Url 有误");
        }
    }
}
