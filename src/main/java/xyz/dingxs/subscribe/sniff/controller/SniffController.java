package xyz.dingxs.subscribe.sniff.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.dingxs.subscribe.common.config.TokenConfig;
import xyz.dingxs.subscribe.sniff.service.SniffService;

@RestController
@RequestMapping("/sniff")
public class SniffController {

    @Autowired
    private SniffService sniffService;

    @Autowired
    private TokenConfig tokenConfig;

    @GetMapping("/getSniffRes")
    public ResponseEntity<Boolean> getSniffRes(@RequestParam String token) {
        // 先校验token
        if (tokenConfig.getValue().equals(token)) {
            return ResponseEntity.ok(sniffService.getSniffRes());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}
