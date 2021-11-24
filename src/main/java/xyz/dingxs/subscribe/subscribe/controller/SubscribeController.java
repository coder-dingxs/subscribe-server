package xyz.dingxs.subscribe.subscribe.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @ApiOperation(value = "获取订阅")
    @GetMapping("/get")
    public String get() {
        return subscribeService.get();
    }

    @ApiOperation(value = "设置订阅")
    @ApiImplicitParam(name = "token", paramType = "header", required = true)
    @PostMapping("/set")
    public ResponseEntity<Boolean> set(@RequestBody @Valid SubscribeSetReq subscribeSetReq) {
        return ResponseEntity.ok(subscribeService.set(subscribeSetReq.getUrl()));
    }

}
