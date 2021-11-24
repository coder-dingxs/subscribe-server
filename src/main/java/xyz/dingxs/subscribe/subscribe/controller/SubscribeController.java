package xyz.dingxs.subscribe.subscribe.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.dingxs.subscribe.common.config.TokenConfig;
import xyz.dingxs.subscribe.subscribe.req.SubscribeSetReq;
import xyz.dingxs.subscribe.subscribe.service.SubscribeService;


/**
 * 订阅服务Controller
 *
 * @author dingxs
 */
@Api(value = "/subscribe", tags = {"订阅"})
@RestController
@RequestMapping("/subscribe")
public class SubscribeController {

    @Autowired
    private SubscribeService subscribeService;

    @Autowired
    private TokenConfig tokenConfig;

    @ApiOperation(value = "获取订阅")
    @GetMapping("/get")
    public String get() {
        return subscribeService.get();
    }

    @ApiOperation(value = "设置订阅")
    @PostMapping("/set")
    public ResponseEntity<Boolean> set(@RequestBody @Valid SubscribeSetReq subscribeSetReq) {
        // 先校验token
        if (tokenConfig.getValue().equals(subscribeSetReq.getToken())) {
            return ResponseEntity.ok(subscribeService.set(subscribeSetReq.getUrl()));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}
