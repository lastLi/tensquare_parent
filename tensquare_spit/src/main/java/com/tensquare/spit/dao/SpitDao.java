package com.tensquare.spit.dao;

import com.tensquare.spit.pojo.Spit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * 吐槽dao
 */
public interface SpitDao extends MongoRepository<Spit,String> {

    /**
     * 根据父节点查询吐槽数据
     * @param Parentid 父节点
     * @param pageable 分页
     * @return 该父节点下的吐槽信息
     */
    Page<Spit> findByParentid(String Parentid, Pageable pageable);
}
