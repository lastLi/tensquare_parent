package com.tensquare.spit.service;

import com.mongodb.client.result.UpdateResult;
import com.tensquare.spit.dao.SpitDao;
import com.tensquare.spit.pojo.Spit;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;
import util.MyPageQuery;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 吐槽业务
 */
@Service
@Transactional
public class SpitService {

    @Resource
    private SpitDao spitDao;
    @Resource
    private IdWorker idWorker;
    @Resource
    private MongoTemplate mongoTemplate;
    @Resource
    private MyPageQuery myPageQuery;


    public List<Spit> findAll() {
        return spitDao.findAll();
    }

    /**
     * 根据编号查询吐槽,每次查询下就增加一下浏览量.
     *
     * @param id 吐槽编号
     * @return 吐槽
     */
    public Spit findById(String id) {
        Spit spit = spitDao.findById(id).get();
        Integer visits = spit.getVisits();
        spit.setVisits(visits == null ? 0 : visits + 1);
        return spitDao.save(spit);
    }

    public void addSpit(Spit spit) {
        Date nowTime = new Date();
        spit.set_id(idWorker.nextId() + "");
        spit.setPublishtime(nowTime);
        spit.setVisits(0);//浏览量
        spit.setShare(0);//分享数
        spit.setThumbup(0);//点赞数
        spit.setComment(0);//回复数
        //如果当前添加的吐嘈,有父节点,那么父节点的回复数要+1
        if (!StringUtils.isEmpty(spit.getParentid())) {
            Query query = new Query();
            //根据这个用户的父节点字段进行主键查询获取他的父节点,然后对他父节点的回复数加1
            query.addCriteria(Criteria.where("_id").is(spit.getParentid()));
            Update update = new Update();
            update.inc("comment", 1);
            mongoTemplate.updateFirst(query, update, "spit");
        }
        spitDao.save(spit);
    }

    public void updateById(String sId, Spit spit) {
        spit.set_id(sId);
        spitDao.save(spit);
    }


    public void deleteById(String sId) {
        spitDao.deleteById(sId);
    }

    public Page<Spit> findByParentId(String parentId, int page, int size) {
        return spitDao.findByParentid(parentId, myPageQuery.pageQuery(page, size));
    }

    /**
     * 点赞呗
     *
     * @param spitId 吐槽ID
     */
    public void thumbUp(String spitId) {
        // 原生命令   db.spit.update({"_id":?},{$inc:{thumbup:NumberInt(1)}})
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(spitId));
        Update update = new Update();
        update.inc("thumbup", 1);
        mongoTemplate.updateFirst(query, update, "spit");

    }
}
