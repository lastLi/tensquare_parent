package util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 * 封装分页条件
 */
public class MyPageQuery {

    /**
     * 分页设置
     * @param page 开始页
     * @param size 显示的记录数
     * @return 分页条件
     */
    public Pageable pageQuery(int page, int size) {
        if (page < 0 || page == 0) {
            page = 1;
        }
        //分页开始 公式:  limit 起始页page(当前页-1)*size,每页显示的数量 size
        return PageRequest.of(page - 1, size);
    }
}
