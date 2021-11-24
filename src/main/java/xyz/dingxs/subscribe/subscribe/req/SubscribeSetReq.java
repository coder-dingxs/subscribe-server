package xyz.dingxs.subscribe.subscribe.req;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SubscribeSetReq {

    @NotBlank(message = "url不可为空")
    private String url;
}
