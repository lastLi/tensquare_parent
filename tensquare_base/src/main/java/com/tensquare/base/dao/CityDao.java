package com.tensquare.base.dao;

import com.tensquare.base.pojo.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * 城市Dao
 */
public interface CityDao extends JpaRepository<City, String>, JpaSpecificationExecutor<City> {

    /**
     * 根据城市名字查询城市
     * @param name 城市名字
     * @return 结果
     */
    @Query(value = "SELECT * FROM tensquare_base.tb_city WHERE name=#{name}", nativeQuery = true)
    boolean findByName(String name);

}
