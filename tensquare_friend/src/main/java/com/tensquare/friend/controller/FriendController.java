package com.tensquare.friend.controller;

import com.tensquare.friend.client.UserClient;
import com.tensquare.friend.service.FriendService;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/friend")
public class FriendController {

    @Resource
    private HttpServletRequest httpServletRequest;

    @Resource
    private FriendService friendService;

    @Resource
    private UserClient userClient;


    /**
     * 添加好友或者添加非好友
     *
     * @param friendId 好友编号
     * @param type     类型  1 添加好友  2拉黑
     * @return 结果
     */
    @PutMapping("/like/{friendId}/{type}")
    public Result addFriend(@PathVariable("friendId") String friendId,
                            @PathVariable("type") String type) {
        //验证是否登录,并且拿到当前登录的用户编号
        Claims claims = (Claims) httpServletRequest.getAttribute("claims_user");
        if (claims == null) {
            //说明不是User角色
            new Result(false, StatusCode.ACCESS_ERROR, "权限不足");
        }


        //得到当前用户的编号
        String userId = claims.getId();
        if (userId.equals(friendId)) {
            return new Result(false, StatusCode.ERROR, "不能对自己操作");
        }

        //判断是添加好友还是添加非好友
        if (type != null) {
            switch (type) {
                case "1":
                    //添加好友
                    int flag = friendService.addFriend(userId, friendId);
                    if (flag == 0) {
                        return new Result(false, StatusCode.ERROR, "不能重复添加");
                    }
                    if (flag == 1) {
                        userClient.updateFanscountAndFollowcount(1, userId, friendId);
                        return new Result(true, StatusCode.OK, "添加成功");
                    }
                case "2":
                    //添加非好友
                    int noFlag = friendService.addNoFriend(userId, friendId);
                    if (noFlag == 0) {
                        return new Result(false, StatusCode.ERROR, "不能重复添加非好友");
                    }
                    if (noFlag == 1) {
                        return new Result(true, StatusCode.OK, "添加成功");
                    }
                default:
                    //参数异常
                    return new Result(false, StatusCode.ERROR, "参数异常");
            }
        } else {
            return new Result(false, StatusCode.ERROR, "参数异常");
        }
    }

    /**
     * 删除好友
     *
     * @param friendId 好友编号
     * @return 结果
     */
    @PutMapping("/{friendId}")
    public Result deleteFriend(@PathVariable("friendId") String friendId) {
        //验证是否登录,并且拿到当前登录的用户编号
        Claims claims = (Claims) httpServletRequest.getAttribute("claims_user");
        if (claims == null) {
            //说明不是User角色
            new Result(false, StatusCode.ACCESS_ERROR, "权限不足");
        }
        //得到当前用户的编号
        String userId = claims.getId();
        if (userId.equals(friendId)) {
            return new Result(false, StatusCode.ERROR, "不能对自己操作");
        }
        friendService.deleteFriend(userId,friendId);
        userClient.updateFanscountAndFollowcount(-1,userId,friendId);
        return new Result(true,StatusCode.OK,"删除成功");

    }
}
