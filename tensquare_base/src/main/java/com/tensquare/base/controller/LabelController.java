package com.tensquare.base.controller;

import com.tensquare.base.pojo.Label;
import com.tensquare.base.service.LabelService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
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
import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * Label控制类
 * 标签
 */
@CrossOrigin
@RestController
@RequestMapping("/label")
public class LabelController {

    @Resource
    private LabelService labelService;

    @Resource
    private HttpServletRequest request;

    /**
     * 查询所有标签
     *
     * @return 所有的标签列表
     */
    @GetMapping
    public Result findAll() {
        request.getHeader("Authorization");
        List<Label> all = labelService.findAll();
        return new Result(true, StatusCode.OK, "查询成功", all);
    }

    /**
     * 根据ID查询标签
     *
     * @param labelId 标签ID
     * @return 返回这个标签的ID
     */
    @GetMapping("/{labelId}")
    public Result findByLabelId(@PathVariable("labelId") String labelId) {
        return new Result(true, StatusCode.OK, "查询成功", labelService.findById(labelId));
    }

    /**
     * 根据ID修改标签
     *
     * @param labelId 标签ID
     * @return 返回这个ID的标签
     */
    @PutMapping("/{labelId}")
    public Result updateByLabelId(@RequestBody Label label, @PathVariable("labelId") String labelId) {
        labelService.updateById(labelId, label);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 查询推荐标签列表
     *
     * @return 所有的推荐标签列表
     */
    @GetMapping("/toplist")
    public Result topList() {
        List<Label> allByRecommendIsTrue = labelService.findAllByRecommendIsTrue();
        if (allByRecommendIsTrue.size() == 0) {
            return new Result(false, StatusCode.OK, "当前没有推荐标签");
        }

        return new Result(true, StatusCode.OK, "查询成功", labelService.findAllByRecommendIsTrue());
    }

    /**
     * 添加一个标签
     *
     * @param label 标签Json对象,不带编号
     * @return 执行后成功的信息
     */
    @PostMapping
    public Result addLabel(@RequestBody Label label) {
        labelService.save(label);
        return new Result(true, StatusCode.OK, "添加成功");
    }

    /**
     * 根据ID删除标签
     *
     * @param labelId 标签ID
     * @return 返回这个标签的ID
     */
    @DeleteMapping("/{labelId}")
    public Result deleteByLabelId(@PathVariable String labelId) {
        labelService.deleteById(labelId);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    /**
     * 从查到的标签数据开始分页展示
     *
     * @param label 标签体
     * @param page  当前页面
     * @param size  页面显示数据大小
     * @return 该标签下的
     */
    @PostMapping("/search/{page}/{size}")
    public Result pageLabel(@RequestBody Label label,
                            @PathVariable int page,
                            @PathVariable int size) {
        Page<Label> pageQuery = labelService.pageQuery(label, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<Label>(pageQuery.getTotalElements(), pageQuery.getContent()));
    }


    /**
     * 条件查询标签
     *
     * @param label 标签实体
     * @return 结果集
     */
    @PostMapping("/search")
    public Result findSearch(@RequestBody Label label) {
        List<Label> list = labelService.findSearch(label);
        if (list.size() == 0) {
            return new Result(false, StatusCode.OK, "没有查到结果");
        }
        return new Result(true, StatusCode.OK, "查询成功", list);
    }

    /**
     * @return 找到有效的标签
     */
    @GetMapping("/list")
    public Result findAValidLabel() {

        List<Label> list = labelService.findAValidLabel();
        if (list.size() == 0) {
            return new Result(false, StatusCode.OK, "现在没有上架的标签");
        }
        return new Result(true, StatusCode.OK, "查询成功", list);
    }
}
