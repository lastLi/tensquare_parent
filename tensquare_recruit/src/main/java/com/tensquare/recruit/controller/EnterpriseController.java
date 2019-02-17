package com.tensquare.recruit.controller;

import com.tensquare.recruit.pojo.Enterprise;
import com.tensquare.recruit.service.EnterpriseService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
@CrossOrigin //跨域请求
@RestController
@RequestMapping("/enterprise")
public class EnterpriseController {


    @Resource
    private EnterpriseService enterpriseService;

    @GetMapping
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", enterpriseService.findAll());
    }

    @GetMapping("/{enterpriseId}")
    public Result findById(@PathVariable("enterpriseId") String enterpriseId) {
        return new Result(true, StatusCode.OK, "查询成功", enterpriseService.findById(enterpriseId));
    }

    @PostMapping
    public Result addEnterprise(@RequestBody Enterprise enterprise) {
        enterpriseService.add(enterprise);
        return new Result(true, StatusCode.OK, "添加成功");
    }

    @PutMapping("/{enterpriseId}")
    public Result updateById(@PathVariable("enterpriseId") String enterpriseId, @RequestBody Enterprise enterprise) {
        enterprise.setId(enterpriseId);
        enterpriseService.update(enterprise);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    @DeleteMapping("/{enterpriseId}")
    public Result deleteById(@PathVariable("enterpriseId") String enterpriseId) {
        enterpriseService.deleteById(enterpriseId);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    /**
     * 根据条件查询
     *
     * @param searchMap
     * @return
     */
    @PostMapping("/search")
    public Result search(@RequestBody Map searchMap) {
        return new Result(true, StatusCode.OK, "查询成功", enterpriseService.findSearch(searchMap));
    }


    /**
     * @return 热门企业
     */
    @GetMapping("/search/hotlist")
    public Result hotList() {
        List<Enterprise> enterprises = enterpriseService.isHost();
        return new Result(true, StatusCode.OK, "查询成功", enterprises);
    }

    /**
     * 分页+多条件查询
     *
     * @param searchMap 查询条件封装
     * @param page      页码
     * @param size      页大小
     * @return 分页结果
     */
    @PostMapping("/search/{page}/{size}")
    public Result findSearch(@RequestBody Map searchMap, @PathVariable int page, @PathVariable int size) {
        Page<Enterprise> pageList = enterpriseService.findSearch(searchMap, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<Enterprise>(pageList.getTotalElements(), pageList.getContent()));
    }
}
