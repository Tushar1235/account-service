package com.swiftpay.account_service.feignclient;

import com.swiftpay.account_service.configuration.FeignClientConfig;
import com.swiftpay.account_service.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service" , url = "http://localhost:8080",
configuration = FeignClientConfig.class)
public interface UserClient {

    @GetMapping("/api/users/profile/{username}")
    public UserDto getUser(@PathVariable("username") String username);
}
