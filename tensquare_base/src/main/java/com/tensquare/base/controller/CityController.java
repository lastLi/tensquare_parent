package com.tensquare.base.controller;

import com.tensquare.base.pojo.City;
import com.tensquare.base.service.CityService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/city")
public class CityController {
    @Resource
    private CityService cityService;

    /**
     *
     * @return 城市列表
     */
    @GetMapping
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", cityService.findAll());
    }

    /**
     * 根据编号查询城市
     * @param cityId 城市编号
     * @return 该城市结果
     */
    @GetMapping("/{cityId}")
    public Result findById(@PathVariable("cityId") String cityId) {
        return new Result(true, StatusCode.OK, "查询成功", cityService.findById(cityId));
    }

    /**
     * 添加一个城市
     * @param city 新的城市
     * @return 结果
     */
    @PostMapping
    public Result addCity(@RequestBody City city) {
        cityService.addCity(city);
        return new Result(true, StatusCode.OK, "添加成功");
    }

    /**
     * 根据编号修改城市
     * @param cityId 城市编号
     * @param city 城市实体
     * @return 结果
     */
    @PutMapping("/{cityId}")
    public Result updateById(@PathVariable("cityId") String cityId, @RequestBody City city) {
        cityService.updateById(cityId, city);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 根据编号删除城市
     * @param cityId 城市编号
     * @return 结果
     */
    @DeleteMapping("/{cityId}")
    public Result deleteById(@PathVariable("cityId") String cityId) {
        cityService.deleteById(cityId);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    /**
     * 根据城市条件查询城市
     * @param city 城市
     * @return 结果
     */
    @PostMapping("/search")
    public Result search(City city) {
        List<City> search = cityService.search(city);
        if (search.size() == 0) {
            return new Result(false, StatusCode.OK, "没有查到结果");
        }
        return new Result(true, StatusCode.OK, "查询成功", search);
    }

    /**
     *
     * @param page 页码
     * @param size 显示数据
     * @param city 实体
     * @return 分页结果
     */
    @PostMapping("/search/{page}/{size}")
    public Result findPage(@PathVariable("page") int page, @PathVariable("size") int size, @RequestBody City city) {
        Page<City> pageQuery = cityService.pageQuery(city, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<City>(pageQuery.getTotalElements(), pageQuery.getContent()));

    }
}
