package fun.kazex.dwz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import fun.kazex.dwz.entity.UrlMap;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: KazeMae
 * @Date: 2025/04/13 22:53
 * @Description: 链接映射数据库操作
 **/
@Mapper
public interface UrlMapper extends BaseMapper<UrlMap> {
}
