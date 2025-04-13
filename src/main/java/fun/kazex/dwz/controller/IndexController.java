package fun.kazex.dwz.controller;

import fun.kazex.dwz.service.UrlService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author: KazeMae
 * @Date: 2025/04/14 00:33
 * @Description: 页面响应
 **/
@Controller
public class IndexController {

    private final UrlService urlService;

    public IndexController(UrlService urlService) {
        this.urlService = urlService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/{shortUrl}")
    public String redirect(@PathVariable String shortUrl) {
        String longUrl = urlService.getLongUrlByShortUrl(shortUrl);
        if (longUrl != null) {
            // 更新访问次数
            urlService.updateUrlViews(shortUrl);
            // 查询到对应的原始链接，302重定向
            return "redirect:" + longUrl;
        }
        // 直接返回首页
        return "redirect:/";
    }
}
