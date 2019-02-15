package com.tensquare.base.service;


import com.tensquare.base.dao.CityDao;
import com.tensquare.base.pojo.City;
import com.tensquare.base.pojo.Label;
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

@Service
@Transactional
public class CityService {

    @Resource
    private CityDao cityDao;

    @Resource
    private IdWorker idWorker;

    /**
     * @return 所有的城市列表
     */
    public List<City> findAll() {
        return cityDao.findAll();
    }

    /**
     * 返回当前编号的城市
     *
     * @param cid 城市编号
     * @return 返回当前编号的城市
     */
    public City findById(String cid) {
        return cityDao.findById(cid).get();
    }

    /**
     * 添加一个城市
     *
     * @param city 城市
     */
    public void addCity(City city) {
        if (!cityDao.findByName(city.getName())) {
            city.setId(idWorker.nextId() + "");
            cityDao.save(city);
        } else {
            throw new RuntimeException("城市重名,拒绝添加");
        }


    }

    /**
     * 根据编号删除城市
     *
     * @param cid 城市编号
     */
    public void deleteById(String cid) {
        cityDao.deleteById(cid);
    }

    /**
     * 根据条件查询
     *
     * @param city 城市
     * @return 结果集
     */
    public List<City> search(City city) {
        return cityDao.findAll(queryByCondition(city));
    }

    /**
     * 分页显示城市
     *
     * @param city 城市
     * @param page 页码
     * @param size 每页显示数据量
     * @return 分页数据
     */
    public Page<City> pageQuery(City city, int page, int size) {
        if (page < 0 || page == 0) {
            page = 1;
        }
        //分页开始 公式:  limit 起始页page(当前页-1)*size,每页显示的数量 size
        Pageable pageable = PageRequest.of(page - 1, size);
        return cityDao.findAll(queryByCondition(city), pageable);
    }

    /**
     * 根据实体类条件来封装查询语句
     *
     * @param city 条件
     * @return 结果
     */
    private Specification<City> queryByCondition(City city) {
        return new Specification<City>() {
            /**
             *
             * @param root 表名的意思
             * @param criteriaQuery 封装查询关键字
             * @param criteriaBuilder 封装条件对象
             * @return 返回一个SQL语句查询的谓语
             */
            @Override
            public Predicate toPredicate(Root<City> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (!StringUtils.isEmpty(city.getName())) {
                    //创建查询条件labelname like %xxx%
                    Predicate predicate = criteriaBuilder.like(root.get("name").as(String.class), "%" + city.getName() + "%");
                    predicates.add(predicate);
                }
                if (!StringUtils.isEmpty(city.getIshot())) {
                    //创建查询条件labelname like %xxx%
                    Predicate predicate = criteriaBuilder.equal(root.get("ishot").as(String.class), city.getIshot());
                    predicates.add(predicate);
                }

                Predicate[] parr = new Predicate[predicates.size()];

                //等同于select * from tb_city where name like %xxx% and ishot='x'
                return criteriaBuilder.and(predicates.toArray(parr));

            }
        };
    }

    /**
     * 根据编号修改城市
     *
     * @param cid  城市编号
     * @param city 城市实体
     */
    public void updateById(String cid, City city) {
        city.setId(cid);
        cityDao.save(city);
    }

}
