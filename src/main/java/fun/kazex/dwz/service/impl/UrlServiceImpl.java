package fun.kazex.dwz.service.impl;

import cn.hutool.bloomfilter.BitMapBloomFilter;
import cn.hutool.bloomfilter.BloomFilterUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import fun.kazex.dwz.entity.UrlMap;
import fun.kazex.dwz.mapper.UrlMapper;
import fun.kazex.dwz.service.UrlService;
import fun.kazex.dwz.util.HashUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

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

    @Autowired
    private StringRedisTemplate redisTemplate;

    // 自定义长链接防重复字符串
    private static final String DUPLICATE = "*";
    // 最近使用的短链接过期时间(分钟)
    private static final long TIMEOUT = 10;
    // 创建布隆过滤器
    private static final BitMapBloomFilter FILTER = BloomFilterUtil.createBitMap(10);


    @Override
    public String saveUrl(String longUrl, String originalUrl) {
        String shortUrl = HashUtils.hashToBase62(longUrl);
        // 保留长度为1的短链接
        if (shortUrl.length() == 1) {
            longUrl += DUPLICATE;
            shortUrl = saveUrl(longUrl, originalUrl);
        // 在布隆过滤器中查找是否存在
        } else if (FILTER.contains(shortUrl)) {
            // 存在，查找是否有缓存
            String redisLongURL = redisTemplate.opsForValue().get(shortUrl);
            if (originalUrl.equals(redisLongURL)) {
                // 有缓存，重置过期时间
                redisTemplate.expire(shortUrl, TIMEOUT, TimeUnit.MINUTES);
                return shortUrl;
            }
            // 没有缓存，在长链接后加上指定字符串，重新 hash
            longUrl += DUPLICATE;
            shortUrl = saveUrl(longUrl, originalUrl);
        } else {
            // 不存在
            try {
                urlMapper.insert(new UrlMap(shortUrl, originalUrl));
                FILTER.add(shortUrl);
                // 添加缓存
                redisTemplate.opsForValue().set(shortUrl, originalUrl, TIMEOUT, TimeUnit.MINUTES);
            } catch (Exception e) {
                if (e instanceof DuplicateKeyException) {
                    // 数据库已经存在此短链接，可能是布隆过滤器误判，在长链接后加上指定字符串，重新保存
                    //数据库已经存在此短链接，则可能是布隆过滤器误判，在长链接后加上指定字符串，重新hash
                    longUrl += DUPLICATE;
                    shortUrl = saveUrl(longUrl, originalUrl);
                } else {
                    throw e;
                }
            }
        }
        return shortUrl;
    }

    @Override
    public String getLongUrlByShortUrl(String shortUrl) {
        //查找Redis中是否有缓存
        String longUrl = redisTemplate.opsForValue().get(shortUrl);
        if (longUrl != null) {
            //有缓存，延迟缓存时间
            redisTemplate.expire(shortUrl, TIMEOUT, TimeUnit.MINUTES);
            return longUrl;
        }
        //Redis没有缓存，从数据库查找
        UrlMap urlMap = urlMapper.selectOne(new LambdaUpdateWrapper<UrlMap>()
                .eq(UrlMap::getShortUrl, shortUrl));
        if (urlMap == null)
            return null;
        // 数据库中存在
        redisTemplate.opsForValue().set(shortUrl, urlMap.getLongUrl(), TIMEOUT, TimeUnit.MINUTES);
        return urlMap.getLongUrl();
    }

    @Override
    public void updateUrlViews(String shortUrl) {
        urlMapper.update(new LambdaUpdateWrapper<UrlMap>()
                .eq(UrlMap::getShortUrl, shortUrl)
                .setSql("views = views + 1"));
    }
}
