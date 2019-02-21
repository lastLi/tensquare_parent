package com.tensquare.recruit.controller;


import com.tensquare.recruit.pojo.Recruit;
import com.tensquare.recruit.service.RecruitService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
@RefreshScope  //支持上线,自定义配置文件的热部署
@CrossOrigin //跨域请求
@RestController
@RequestMapping("/recruit")
public class RecruitController {
    @Resource
    private RecruitService recruitService;

    /**
     * @return 推荐职位
     */
    @GetMapping("/search/recommend")
    public Result recommend() {
        return new Result(true, StatusCode.OK, "查询成功", recruitService.recommend());
    }

    /**
     * @return 最新职位
     */
    @GetMapping("/search/newList")
    public Result newList() {
        return new Result(true, StatusCode.OK, "查询成功", recruitService.newList());
    }


    /**
     * 查询全部数据
     *
     * @return
     */
    @GetMapping
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", recruitService.findAll());
    }

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return
     */
    @GetMapping("/{id}")
    public Result findById(@PathVariable String id) {
        return new Result(true, StatusCode.OK, "查询成功", recruitService.findById(id));
    }


    /**
     * 分页+多条件查询
     *
     * @param recruit 查询条件封装
     * @param page    页码
     * @param size    页大小
     * @return 分页结果
     */
    @PostMapping("/search/{page}/{size}")
    public Result findSearch(@RequestBody Recruit recruit, @PathVariable("page") int page, @PathVariable("size") int size) {
        Page<Recruit> pageList = recruitService.pageQuery(recruit, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<Recruit>(pageList.getTotalElements(), pageList.getContent()));
    }

    /**
     * 根据条件查询
     *
     * @param recruit
     * @return
     */
    @PostMapping("/search")
    public Result findSearch(@RequestBody Recruit recruit) {
        return new Result(true, StatusCode.OK, "查询成功", recruitService.search(recruit));
    }

    /**
     * 增加
     *
     * @param recruit
     */
    @PostMapping
    public Result add(@RequestBody Recruit recruit) {
        recruitService.addRecruit(recruit);
        return new Result(true, StatusCode.OK, "增加成功");
    }

    /**
     * 修改
     *
     * @param recruit
     */
    @PutMapping("/{id}")
    public Result update(@RequestBody Recruit recruit, @PathVariable String id) {
        recruitService.updateById(id, recruit);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 删除
     *
     * @param id
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable String id) {
        recruitService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }




}
