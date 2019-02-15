package com.tensquare.base.service;

import com.tensquare.base.dao.LabelDao;
import com.tensquare.base.pojo.Label;
import entity.PageResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 标签业务层接口
 */
@Service
@Transactional //事物注解
public class LabelService {

    @Resource
    private LabelDao labelDao;

    @Resource
    private IdWorker idWorker;

    /**
     * @return 所有的标签列表
     */
    public List<Label> findAll() {
        return labelDao.findAll();
    }

    /**
     * 根据ID查询
     *
     * @param id 编号
     * @return 标签数据
     */
    public Label findById(String id) {
        return labelDao.findById(id).get();
    }

    /**
     * 新增一个标签
     *
     * @param label 标签
     */
    public void save(Label label) {
        //生成ID
        label.setId(idWorker.nextId() + "");
        labelDao.save(label);
    }

    /**
     * 根据编号,修改一个标签
     *
     * @param id    编号
     * @param label 标签
     */
    public void updateById(String id, Label label) {
        //给这个实体加上要被修改的ID值
        label.setId(id);
        labelDao.save(label);
    }

    /**
     * 根据编号删除一个标签
     *
     * @param id 编号
     */
    public void deleteById(String id) {
        labelDao.deleteById(id);
    }

    /**
     * @return 所有的推荐标签列表
     */
    public List<Label> findAllByRecommendIsTrue() {

        return labelDao.recommendedTagList();

    }


    /**
     * 根据条件查询
     *
     * @param label 标签
     * @return 结果集
     */
    public List<Label> findSearch(Label label) {

        return labelDao.findAll(queryByCondition(label));
    }


    /**
     * @param label 实体
     * @param page  页码
     * @param size  每页显示的数据量
     * @return 分页数据
     */
    public Page<Label> pageQuery(Label label, int page, int size) {
        if (page < 0 || page == 0) {
            page = 1;
        }
        //分页开始 公式:  limit 起始页page(当前页-1)*size,每页显示的数量 size
        Pageable pageable = PageRequest.of(page - 1, size);
        return labelDao.findAll(queryByCondition(label), pageable);
    }

    /**
     * 查找state为1的标签
     *
     * @return 结果集
     */
    public List<Label> findAValidLabel() {

        return labelDao.validTagList();

    }
    /**
     * 根据实体类条件来封装查询语句
     *
     * @param label 条件
     * @return 结果
     */
    private Specification<Label> queryByCondition(Label label) {
        return new Specification<Label>() {
            /**
             *
             * @param root 表名的意思
             * @param criteriaQuery 封装查询关键字
             * @param criteriaBuilder 封装条件对象
             * @return 返回一个SQL语句查询的谓语
             */
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (!StringUtils.isEmpty(label.getLabelname())) {
                    //创建查询条件labelname like %xxx%
                    Predicate predicate = criteriaBuilder.like(root.get("labelname").as(String.class), "%" + label.getLabelname() + "%");
                    predicates.add(predicate);
                }
                if (!StringUtils.isEmpty(label.getRecommend())) {
                    //创建查询条件 recommend='xxx'
                    Predicate predicate = criteriaBuilder.equal(root.get("recommend").as(String.class), label.getRecommend());
                    predicates.add(predicate);
                }
                if (!StringUtils.isEmpty(label.getState())) {
                    //创建查询条件state='xxx'
                    Predicate predicate = criteriaBuilder.equal(root.get("state").as(String.class), label.getState());
                    predicates.add(predicate);
                }
                if (label.getCount() != null) {
                    //创建查询条件count='xxx'
                    Predicate predicate = criteriaBuilder.equal(root.get("count").as(Long.class), label.getCount());
                    predicates.add(predicate);
                }
                if (label.getFans() != null) {
                    //创建查询条件count='xxx'
                    Predicate predicate = criteriaBuilder.equal(root.get("fans").as(Long.class), label.getFans());
                    predicates.add(predicate);
                }
                Predicate[] parr = new Predicate[predicates.size()];

                //等同于select * from label where labelname like %xxx% and recommend='x' and state='x' and count=x and fans=x
                return criteriaBuilder.and(predicates.toArray(parr));

            }
        };
    }



}
