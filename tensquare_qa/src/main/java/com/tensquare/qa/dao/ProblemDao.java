package com.tensquare.qa.dao;

import com.tensquare.qa.pojo.Problem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 *
 * @author Administrator
 */
public interface ProblemDao extends JpaRepository<Problem, String>, JpaSpecificationExecutor<Problem> {

    /**
     * @param labelId  编号
     * @param pageable 分页
     * @return 最新回复
     */
    @Query(value = "SELECT * FROM tensquare_qa.tb_problem INNER JOIN tensquare_qa.tb_pl ON tensquare_qa.tb_problem.id = tensquare_qa.tb_pl.problemid AND tensquare_qa.tb_pl.labelid = #{labelId} ORDER BY replytime DESC ",
            nativeQuery = true)
    Page<Problem> newList(String labelId, Pageable pageable);

    /**
     * @param labelId  编号
     * @param pageable 分页
     * @return 热门回复
     */
    @Query(value = "SELECT * FROM tensquare_qa.tb_problem INNER JOIN tensquare_qa.tb_pl ON tensquare_qa.tb_problem.id = tensquare_qa.tb_pl.problemid AND tensquare_qa.tb_pl.labelid = #{labelId} ORDER BY reply DESC ",
            nativeQuery = true)
    Page<Problem> hotList(String labelId, Pageable pageable);

    /**
     * @param labelId  编号
     * @param pageable 分页
     * @return 等待回复
     */
    @Query(value = "SELECT * FROM tensquare_qa.tb_problem INNER JOIN tensquare_qa.tb_pl ON tensquare_qa.tb_problem.id = tensquare_qa.tb_pl.problemid AND tensquare_qa.tb_pl.labelid = #{labelId} AND reply=0 ORDER BY createtime DESC ",
            nativeQuery = true)
    Page<Problem> waitList(String labelId, Pageable pageable);

}
