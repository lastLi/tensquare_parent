package entity;

import lombok.Data;

import java.util.List;

/**
 * 用于返回分页结果
 */
@Data
public class PageResult<T> {

    /**
     * 总记录数
     */
    private Long total;
    /**
     * 分页的数据
     */
    private List<T> rows;
}
