package com.tensquare.user.controller;

import com.tensquare.user.pojo.User;
import com.tensquare.user.service.UserService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import util.JwtUtil;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


/**
 * 控制器层
 *
 * @author Administrator
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Resource
    private JwtUtil jwtUtil;

    /**
     * 更新粉丝数和关注数
     *
     * @param value    值
     * @param userId   用户编号
     * @param friendId 好友编号
     */
    @PutMapping("/{userId}/{friendId}/{value}")
    public void updateFanscountAndFollowcount(@PathVariable("value") int value,
                                              @PathVariable("userId") String userId,
                                              @PathVariable("friendId") String friendId) {

        userService.updateFanscountAndFollowcount(value, userId, friendId);
    }


    /**
     * 用户登录
     *
     * @param user 用户
     * @return 结果
     */
    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        user = userService.login(user.getMobile(), user.getPassword());
        if (user == null) {
            return new Result(false, StatusCode.LOGIN_ERROR, "登录失败");
        }
        //使得前端和后端可以通话的操作,采用JWT实现

        //生成JWT令牌
        String token = jwtUtil.createJWT(user.getId(), user.getMobile(), "user");
        Map<String, Object> map = new HashMap<>();
        //保存角色
        map.put("token", token);
        map.put("role", "user");
        return new Result(true, StatusCode.OK, "登录成功", map);

    }

    /**
     * 发送短信
     *
     * @param mobile 手机号
     * @return 结果
     */
    @PostMapping("/sendsms/{mobile}")
    public Result sendSms(@PathVariable String mobile) {
        userService.sendSms(mobile);
        return new Result(true, StatusCode.OK, "发送成功");
    }


    /**
     * 用户注册
     *
     * @param code 验证码
     * @param user 用户信息
     * @return 结果
     */
    @PostMapping("/register/{code}")
    public Result register(@PathVariable String code, @RequestBody User user) {
        //先得到缓存中的验证码
        String checkCodeCache = redisTemplate.opsForValue().get("checkCode_" + user.getMobile());
        if (Objects.requireNonNull(checkCodeCache).isEmpty()) {
            return new Result(false, StatusCode.ERROR, "请获取手机验证码");
        }
        if (!checkCodeCache.equals(code)) {
            return new Result(false, StatusCode.ERROR, "请输入正确的验证码");
        }
        userService.add(user);
        return new Result(true, StatusCode.OK, "注册成功");
    }


    /**
     * 查询全部数据
     *
     * @return 全部用户列表
     */
    @GetMapping
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", userService.findAll());
    }

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return 该ID的用户
     */
    @GetMapping("/{id}")
    public Result findById(@PathVariable String id) {
        return new Result(true, StatusCode.OK, "查询成功", userService.findById(id));
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
        Page<User> pageList = userService.findSearch(searchMap, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<User>(pageList.getTotalElements(), pageList.getContent()));
    }

    /**
     * 根据条件查询
     *
     * @param searchMap 请求映射实体
     * @return 该条件的用户
     */
    @PostMapping("/search")
    public Result findSearch(@RequestBody Map searchMap) {
        return new Result(true, StatusCode.OK, "查询成功", userService.findSearch(searchMap));
    }

    /**
     * 增加
     *
     * @param user 用户
     */
    @PostMapping
    public Result add(@RequestBody User user) {
        userService.add(user);
        return new Result(true, StatusCode.OK, "增加成功");
    }

    /**
     * 修改
     *
     * @param id   编号
     * @param user 用户
     */
    @PutMapping("/{id}")
    public Result update(@RequestBody User user, @PathVariable String id) {
        userService.update(user, id);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 删除,必须有admin角色才能删除
     *
     * @param id 编号
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable String id) {

        userService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

}
