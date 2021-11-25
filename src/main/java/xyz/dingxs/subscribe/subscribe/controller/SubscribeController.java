package xyz.dingxs.subscribe.subscribe.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.dingxs.subscribe.subscribe.req.SubscribeSetReq;
import xyz.dingxs.subscribe.subscribe.req.UpdatePortReq;
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
    @ApiImplicitParam(name = "token", dataType = "java.lang.String", paramType = "header", required = true)
    @PostMapping("/set")
    public ResponseEntity<Boolean> set(@RequestBody @Valid SubscribeSetReq subscribeSetReq) {
        return ResponseEntity.ok(subscribeService.set(subscribeSetReq.getUrl()));
    }

    @ApiOperation(value = "获取订阅配置")
    @ApiImplicitParam(name = "token", dataType = "java.lang.String", paramType = "header", required = true)
    @PostMapping("/getConfig")
    public ResponseEntity<String> getConfig() {
        return ResponseEntity.ok(subscribeService.getConfig());
    }

    @ApiOperation(value = "设置新端口")
    @ApiImplicitParam(name = "token", dataType = "java.lang.String", paramType = "header", required = true)
    @PostMapping("/updatePort")
    public ResponseEntity<Boolean> updatePort(@RequestBody UpdatePortReq req) {
        return ResponseEntity.ok(subscribeService.changePort(req.getPort()));
    }

}
