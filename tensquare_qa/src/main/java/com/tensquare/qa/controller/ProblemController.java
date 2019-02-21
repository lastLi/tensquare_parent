package com.tensquare.qa.controller;

import com.tensquare.qa.client.BaseClient;
import com.tensquare.qa.pojo.Problem;
import com.tensquare.qa.service.ProblemService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 控制器层
 *
 * @author Administrator
 */
@RestController
@CrossOrigin
@RequestMapping("/problem")
public class ProblemController {

    @Resource
    private ProblemService problemService;

    @Resource
    private HttpServletRequest request;

    @Resource
    private BaseClient baseClient;

    @GetMapping("/label/{labelId}")
    public Result findByLabelId(@PathVariable("labelId") String labelId){

        return baseClient.findByLabelId(labelId);
    }
    /**
     * 最新问答列表
     *
     * @param labelId 标签ID
     * @param page    页码
     * @param size    最多显示页数
     * @return entity.Result
     */
    @GetMapping("/newlist/{labelId}/{page}/{size}")
    public Result newList(@PathVariable("labelId") String labelId,
                          @PathVariable("page") int page,
                          @PathVariable("size") int size) {
        Page<Problem> problems = problemService.newList(labelId, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<Problem>(problems.getTotalElements(), problems.getContent()));
    }

    /**
     * 等待问答列表
     *
     * @param labelId 标签ID
     * @param page    页码
     * @param size    最多显示页数
     * @return entity.Result
     */
    @GetMapping("/waitlist/{labelId}/{page}/{size}")
    public Result waitList(@PathVariable("labelId") String labelId,
                           @PathVariable("page") int page,
                           @PathVariable("size") int size) {
        Page<Problem> problems = problemService.waitList(labelId, page, size);


        return new Result(true, StatusCode.OK, "查询成功", new PageResult<Problem>(problems.getTotalElements(), problems.getContent()));
    }

    /**
     * 热门问答列表
     *
     * @param labelId 标签ID
     * @param page    页码
     * @param size    最多显示页数
     * @return entity.Result
     */
    @GetMapping("/hotlist/{labelId}/{page}/{size}")
    public Result hotList(@PathVariable("labelId") String labelId,
                          @PathVariable("page") int page,
                          @PathVariable("size") int size) {
        Page<Problem> problems = problemService.hotList(labelId, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<Problem>(problems.getTotalElements(), problems.getContent()));
    }


    /**
     * 查询全部数据
     *
     * @return
     */
    @GetMapping
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", problemService.findAll());
    }

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return
     */
    @GetMapping("/{id}")
    public Result findById(@PathVariable String id) {
        return new Result(true, StatusCode.OK, "查询成功", problemService.findById(id));
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
        Page<Problem> pageList = problemService.findSearch(searchMap, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<Problem>(pageList.getTotalElements(), pageList.getContent()));
    }

    /**
     * 根据条件查询
     *
     * @param searchMap
     * @return
     */
    @PostMapping("/search")
    public Result findSearch(@RequestBody Map searchMap) {
        return new Result(true, StatusCode.OK, "查询成功", problemService.findSearch(searchMap));
    }

    /**
     * 增加 问题
     *
     * @param problem  问题
     */
    @PostMapping
    public Result add(@RequestBody Problem problem) {
        String token = (String) request.getAttribute("claims_user");
        if (StringUtils.isEmpty(token)) {
            return new Result(false, StatusCode.ACCESS_ERROR, "权限不足");
        }
        problemService.add(problem);
        return new Result(true, StatusCode.OK, "增加成功");
    }

    /**
     * 修改
     *
     * @param problem
     */
    @PutMapping("/{id}")
    public Result update(@RequestBody Problem problem, @PathVariable String id) {
        problem.setId(id);
        problemService.update(problem);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 删除
     *
     * @param id
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable String id) {
        problemService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

}
