package com.tensquare.recruit.dao;

import com.tensquare.recruit.pojo.Enterprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 企业Dao
 */
public interface EnterpriseDao extends JpaRepository<Enterprise, String>, JpaSpecificationExecutor<Enterprise> {

    /**
     *
     * @return 热门企业
     */
    @Query(value="SELECT * FROM tensquare_recruit.tb_enterprise WHERE ishot=1",nativeQuery=true)
    List<Enterprise> isHost();
}
