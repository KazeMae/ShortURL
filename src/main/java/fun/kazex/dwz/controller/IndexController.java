package fun.kazex.dwz.controller;

import fun.kazex.dwz.service.UrlService;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${server.host}")
    private String host;

    public IndexController(UrlService urlService) {
        this.urlService = urlService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/{shortURL}")
    public String redirect(@PathVariable String shortURL) {
        String longURL = urlService.getLongUrlByShortUrl(shortURL);
        if (longURL != null) {
            //查询到对应的原始链接，302重定向
            return "redirect:" + longURL;
        }
        //没有对应的原始链接，直接返回首页
        return "redirect:/";
    }
}
