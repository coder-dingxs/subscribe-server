package xyz.dingxs.subscribe.subscribe.controller;

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
@RestController
@RequestMapping("/subscribe")
public class SubscribeController {

    @Autowired
    private SubscribeService subscribeService;

    @Autowired
    private TokenConfig tokenConfig;

    @GetMapping("/get")
    public String get() {
        return subscribeService.get();
    }

    @PostMapping("/set")
    public ResponseEntity<Boolean> set(@RequestBody @Valid SubscribeSetReq subscribeSetReq) {
        // 先校验token
        if (tokenConfig.getValue().equals(subscribeSetReq.getToken())) {
            return ResponseEntity.ok(subscribeService.set(subscribeSetReq.getUrl()));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}
