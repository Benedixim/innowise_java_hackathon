package com.ua.money24.client;

import com.ua.money24.model.request.ExecAsPublicRequest;
import com.ua.money24.model.response.ExecAsPublicResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        value = "money24Client",
        path = "api/v3/ticker/price",
        url = "${spring.cloud.openfeign.client.config.money24Client.url}" // Path without url doesn't work :(
)
public interface Money24Client {
    @PostMapping("")
    ExecAsPublicResponse execAsPublic(@RequestBody ExecAsPublicRequest request);
}
