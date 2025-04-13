package fun.kazex.dwz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import fun.kazex.dwz.entity.UrlMap;
import fun.kazex.dwz.mapper.UrlMapper;
import fun.kazex.dwz.service.UrlService;
import fun.kazex.dwz.util.HashUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

/**
 * @Author: KazeMae
 * @Date: 2025/04/13 22:17
 * @Description: 映射业务实现
 **/
@Service
public class UrlServiceImpl extends ServiceImpl<UrlMapper, UrlMap>
        implements UrlService {

    @Autowired
    private UrlMapper urlMapper;

    private static final String DUPLICATE = " *";

    @Override
    public String saveUrl(String longUrl) {
        String shortUrl = HashUtils.hashToBase62(longUrl);
        // 查找布隆过滤器
        if (judgeExist()) {
            // 存在 在长链接后加上指定字符串 重新保存
            return saveUrl(longUrl + DUPLICATE);
        } else {
            // 不存在
            try {
                urlMapper.insert(new UrlMap(shortUrl, longUrl, System.currentTimeMillis()));
            } catch (Exception e) {
                if (e instanceof DuplicateKeyException) {
                    //数据库已经存在此短链接，则可能是布隆过滤器误判，在长链接后加上指定字符串，重新保存
                    return saveUrl(longUrl + DUPLICATE);
                } else {
                    throw e;
                }
            }
        }
        return shortUrl;
    }

    public boolean judgeExist() {
        return false;
    }
}
