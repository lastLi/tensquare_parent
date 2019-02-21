package com.tensquare.friend.service;

import com.tensquare.friend.dao.FriendDao;
import com.tensquare.friend.dao.NoFriendDao;
import com.tensquare.friend.pojo.Friend;
import com.tensquare.friend.pojo.NoFriend;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class FriendService {
    @Resource
    private FriendDao friendDao;

    @Resource
    private NoFriendDao noFriendDao;

    /**
     * 添加好友
     *
     * @param userId   用户编号
     * @param friendId 好友编号
     */
    public int addFriend(String userId, String friendId) {
        //先判断userId到FriendId是否有数据,有就是重复添加好友
        Friend friend = friendDao.findByUseridAndFriendid(userId, friendId);
        if (friend != null) {
            return 0;
        }
        //直接好友,让好友表UserId到FriendId放心的type为0
        friend = new Friend();
        friend.setUserid(userId);
        friend.setFriendid(friendId);
        friend.setIslike("0");
        friendDao.save(friend);

        //判断从friendId到userId是否有数据,如果有,双方状态都改为1.
        if (friendDao.findByUseridAndFriendid(friendId, userId) != null) {
            //把双方的isLike都改成1
            friendDao.updateIsLike("1", userId, friendId);
            friendDao.updateIsLike("1", friendId, userId);
        }
        return 1;
    }

    /**
     * 添加非好友
     *
     * @param userId   用户ID
     * @param friendId 非好友ID
     * @return 结果
     */
    public int addNoFriend(String userId, String friendId) {

        //先判断是否已经是非好友
        NoFriend noFriend = noFriendDao.findByUseridAndFriendid(userId, friendId);
        if (noFriend != null) {
            return 0;
        }
        noFriend = new NoFriend();
        noFriend.setUserid(userId);
        noFriend.setFriendid(friendId);
        noFriendDao.save(noFriend);
        return 1;
    }

    /**
     *  删除好友
     * @param userId  自己的ID
     * @param friendId 好友的ID
     */
    public void deleteFriend(String userId, String friendId) {
        //删除好友表中userId到friendId这条数据
        friendDao.deleteFriend(userId,friendId);
        //更新friendId到userId的isLike为0
        friendDao.updateIsLike("0",friendId,userId);
        //非好友表中添加数据
        NoFriend noFriend = new NoFriend();
        noFriend.setUserid(userId);
        noFriend.setFriendid(friendId);
        noFriendDao.save(noFriend);
    }
}
