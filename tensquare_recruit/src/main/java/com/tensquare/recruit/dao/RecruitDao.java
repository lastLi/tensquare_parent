package com.tensquare.recruit.dao;

import com.tensquare.recruit.pojo.Recruit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 职位Dao
 */
public interface RecruitDao extends JpaRepository<Recruit, String>, JpaSpecificationExecutor<Recruit> {

    /**
     * @return 热门职位列表
     */
    @Query(value = "SELECT * FROM tensquare_recruit.tb_recruit WHERE state=2 ORDER BY  createtime DESC", nativeQuery = true)
    List<Recruit> findHost();

    /**
     * 最新职位
     * @return 最新职位列表
     */
    @Query(value = "SELECT * FROM tensquare_recruit.tb_recruit WHERE state=0 ORDER BY  createtime DESC", nativeQuery = true)
    List<Recruit> newList();
}
