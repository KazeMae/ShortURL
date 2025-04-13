package fun.kazex.dwz.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: KazeMae
 * @Date: 2025/04/13 22:34
 * @Description: 链接映射
 **/
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "url_map")
public class UrlMap {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String shortUrl;

    private String longUrl;

    private Integer views;

    private Long createTime;

    public UrlMap(String shortUrl, String longUrl, Long createTime) {
        this.shortUrl = shortUrl;
        this.longUrl = longUrl;
        this.createTime = createTime;
    }
}
