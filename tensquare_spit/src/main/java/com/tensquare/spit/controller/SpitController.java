package com.tensquare.spit.controller;

import com.tensquare.spit.pojo.Spit;
import com.tensquare.spit.service.SpitService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@CrossOrigin
@RestController
@RequestMapping("/spit")
public class SpitController {

    @Resource
    private SpitService spitService;

    @Resource
    private RedisTemplate<String, Integer> redisTemplate;


    /**
     * 添加一个吐槽
     *
     * @param spit 吐槽
     * @return 结果信息
     */
    @PostMapping
    public Result addSpit(@RequestBody Spit spit) {
        spitService.addSpit(spit);
        return new Result(true, StatusCode.OK, "添加成功");
    }

    /**
     * @return 全部吐槽列表
     */
    @GetMapping
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", spitService.findAll());
    }

    /**
     * 根据编号查询吐槽
     *
     * @param spitId 吐槽编号
     * @return 该编号的吐槽
     */
    @GetMapping("/{spitId}")
    public Result findById(@PathVariable("spitId") String spitId) {
        return new Result(true, StatusCode.OK, "查询成功", spitService.findById(spitId));
    }

    /**
     * 根据编号删除吐槽
     *
     * @param spitId 吐槽编号
     * @return 结果
     */
    @DeleteMapping("/{spitId}")
    public Result deleteById(@PathVariable("spitId") String spitId) {
        spitService.deleteById(spitId);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    /**
     * 根据编号修改吐槽
     *
     * @param spitId 吐槽编号
     * @param spit   吐槽
     * @return 结果
     */
    @PutMapping("/{spitId}")
    public Result updateById(@PathVariable("spitId") String spitId, @RequestBody Spit spit) {
        spitService.updateById(spitId, spit);
        return new Result(true, StatusCode.OK, "添加成功");
    }

    /**
     *  根据父级编号查找吐槽
     * @param parentId 父级编号
     * @param page 开始页
     * @param size 每页大小
     * @return 分页结果
     */
    @GetMapping("/comment/{parentId}/{page}/{size}")
    public Result findByParentId(@PathVariable("parentId") String parentId,
                                 @PathVariable("page") int page,
                                 @PathVariable("size") int size) {
        Page<Spit> byParentId = spitService.findByParentId(parentId, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<Spit>(byParentId.getTotalElements(), byParentId.getContent()));
    }

    /**
     *  点赞
     * @param spitId 吐槽编号
     * @return 结果
     */
    @PutMapping("/thumbup/{spitId}")
    public Result thumbUp(@PathVariable("spitId") String spitId){
        //判断当前用户是否已经点赞,在没有做用户认证之前,先把userID写死
        String userid = "111";
        if(redisTemplate.opsForValue().get("thumbup_"+userid)!=null){
            return new Result(true, StatusCode.OK, "不能重复点赞");
        }
      spitService.thumbUp(spitId);
        redisTemplate.opsForValue().set("thumbup_"+userid,1);
      return new Result(true, StatusCode.OK, "点赞成功");
    }

}
