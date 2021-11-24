package xyz.dingxs.subscribe.sniff.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.dingxs.subscribe.sniff.service.SniffService;

@Api(value = "/sniff", tags = {"嗅探"})
@RestController
@RequestMapping("/sniff")
public class SniffController {

    @Autowired
    private SniffService sniffService;

    @ApiOperation(value = "获取嗅探状态")
    @ApiImplicitParam(name = "token", paramType = "header", required = true)
    @GetMapping("/getSniffRes")
    public ResponseEntity<Boolean> getSniffRes() {
        return ResponseEntity.ok(sniffService.getSniffRes());
    }

}
