package xyz.dingxs.subscribe.sniff.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.dingxs.subscribe.sniff.service.SniffService;

@RestController
@RequestMapping("/sniff")
public class SniffController {

    @Autowired
    private SniffService sniffService;

    @GetMapping("/getSniffRes")
    public ResponseEntity<Boolean> getSniffRes(){
        return ResponseEntity.ok(sniffService.getSniffRes());
    }

}
