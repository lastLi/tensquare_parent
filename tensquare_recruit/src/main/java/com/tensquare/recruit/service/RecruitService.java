package com.tensquare.recruit.service;

import com.tensquare.recruit.dao.RecruitDao;
import com.tensquare.recruit.pojo.Recruit;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import util.IdWorker;
import util.MyPageQuery;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 职位业务
 */
@Service
public class RecruitService {
    @Resource
    private RecruitDao recruitDao;

    @Resource
    private IdWorker idWorker;


    @Resource
    private MyPageQuery myPageQuery;

    /**
     * @return 全部招聘列表
     */
    public List<Recruit> findAll() {
        return recruitDao.findAll();
    }

    /**
     * 新增一个职位
     *
     * @param recruit 新的职位
     */
    public void addRecruit(Recruit recruit) {
        recruit.setId(idWorker.nextId() + "");
        recruit.setCreatetime(new Date());
        recruitDao.save(recruit);
    }

    /**
     * @param recruitId 职位编号
     * @return 该编号职位
     */
    public Recruit findById(String recruitId) {
        return recruitDao.findById(recruitId).get();
    }

    /**
     * 根据编号修改职位
     *
     * @param recruitId 职位编号
     * @param recruit   新的职位
     */
    public void updateById(String recruitId, Recruit recruit) {
        recruit.setId(recruitId);
        recruitDao.save(recruit);
    }

    /**
     * 根据编号删除职位
     *
     * @param recruitId 职位编号
     */
    public void deleteById(String recruitId) {
        recruitDao.deleteById(recruitId);
    }

    /**
     * 根据条件查询职位
     *
     * @param recruit 职位实体
     * @return 符合条件的信息
     */
    public List<Recruit> search(Recruit recruit) {
        return recruitDao.findAll(queryByCondition(recruit));
    }

    /**
     * 分页显示城市
     *
     * @param city 城市
     * @param page 页码
     * @param size 每页显示数据量
     * @return 分页数据
     */
    public Page<Recruit> pageQuery(Recruit city, int page, int size) {
        Pageable pageable = myPageQuery.pageQuery(page, size);
        return recruitDao.findAll(queryByCondition(city), pageable);
    }

    /**
     * 推荐职位
     *
     * @return 推荐职位列表
     */
    public List<Recruit> recommend() {
        return recruitDao.findHost();
    }

    /**
     * 最新职位
     * @return 最新职位列表
     */
    public Object newList() {
        return recruitDao.newList();

    }

    private Specification<Recruit> queryByCondition(Recruit recruit) {
        return new Specification<Recruit>() {
            /**
             *
             * @param root 表名的意思
             * @param criteriaQuery 封装查询关键字
             * @param criteriaBuilder 封装条件对象
             * @return 返回一个SQL语句查询的谓语
             */
            @Override
            public Predicate toPredicate(Root<Recruit> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (!StringUtils.isEmpty(recruit.getJobname())) {
                    //创建查询条件jobname like %xxx%
                    Predicate predicate = criteriaBuilder.like(root.get("jobname").as(String.class), "%" + recruit.getJobname() + "%");
                    predicates.add(predicate);
                }
                if (!StringUtils.isEmpty(recruit.getSalary())) {
                    //创建查询条件salary = xxx
                    Predicate predicate = criteriaBuilder.equal(root.get("salary").as(String.class), recruit.getSalary());
                    predicates.add(predicate);
                }

                if (!StringUtils.isEmpty(recruit.getCondition())) {
                    //创建查询条件condition = xxx
                    Predicate predicate = criteriaBuilder.equal(root.get("condition").as(String.class), recruit.getCondition());
                    predicates.add(predicate);
                }

                if (!StringUtils.isEmpty(recruit.getEducation())) {
                    //创建查询条件condition = xxx
                    Predicate predicate = criteriaBuilder.equal(root.get("education").as(String.class), recruit.getEducation());
                    predicates.add(predicate);
                }

                if (!StringUtils.isEmpty(recruit.getType())) {
                    //创建查询条件type = xxx
                    Predicate predicate = criteriaBuilder.equal(root.get("type").as(String.class), recruit.getType());
                    predicates.add(predicate);
                }
                if (!StringUtils.isEmpty(recruit.getAddress())) {
                    //创建查询条件address = xxx
                    Predicate predicate = criteriaBuilder.equal(root.get("address").as(String.class), recruit.getAddress());
                    predicates.add(predicate);
                }
                if (!StringUtils.isEmpty(recruit.getEid())) {
                    //创建查询条件eid = xxx
                    Predicate predicate = criteriaBuilder.equal(root.get("eid").as(String.class), recruit.getEid());
                    predicates.add(predicate);
                }
                if (!StringUtils.isEmpty(recruit.getState())) {
                    //创建查询条件state = xxx
                    Predicate predicate = criteriaBuilder.equal(root.get("state").as(String.class), recruit.getState());
                    predicates.add(predicate);
                }
                if (!StringUtils.isEmpty(recruit.getLabel())) {
                    //创建查询条件label = xxx
                    Predicate predicate = criteriaBuilder.equal(root.get("label").as(String.class), recruit.getLabel());
                    predicates.add(predicate);
                }
                if (!StringUtils.isEmpty(recruit.getContent1())) {
                    //创建查询条件content1 = xxx
                    Predicate predicate = criteriaBuilder.equal(root.get("content1").as(String.class), recruit.getContent1());
                    predicates.add(predicate);
                }
                if (!StringUtils.isEmpty(recruit.getContent2())) {
                    //创建查询条件content2 = xxx
                    Predicate predicate = criteriaBuilder.equal(root.get("content2").as(String.class), recruit.getContent2());
                    predicates.add(predicate);
                }
                Predicate[] parr = new Predicate[predicates.size()];

                return criteriaBuilder.and(predicates.toArray(parr));

            }
        };
    }


}
