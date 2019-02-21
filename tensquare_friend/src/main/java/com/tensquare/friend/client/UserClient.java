package com.tensquare.friend.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient("tensquare-user")
public interface UserClient {

    @PutMapping("/user/{userId}/{friendId}/{value}")
     void updateFanscountAndFollowcount(@PathVariable("value") int value,
                                              @PathVariable("userId") String userId,
                                              @PathVariable("friendId") String friendId);
}
