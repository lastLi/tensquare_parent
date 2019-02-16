package com.tensquare.base.dao;


import com.tensquare.base.pojo.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 标签Dao
 */
public interface LabelDao extends JpaRepository<Label, String>, JpaSpecificationExecutor<Label> {
    /**
     * Recommend的值是1就是推荐,0就是不推荐
     * 并且状态还只能是不等于0的
     * @return 推荐标签列表
     */
    @Query(value = "SELECT * FROM tensquare_base.tb_label WHERE recommend=1 AND state!=0", nativeQuery = true)
    List<Label> recommendedTagList();

    /**
     * State的值是1就是有效,0就是无效.
     *
     * @return 有效标签列表
     */
    @Query(value = "SELECT * FROM tensquare_base.tb_label WHERE state=1", nativeQuery = true)
    List<Label> validTagList();
}
