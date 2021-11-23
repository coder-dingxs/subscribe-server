package xyz.dingxs.subscribe.subscribe.req;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SubscribeSetReq {

    @NotBlank(message = "token不可为空")
    private String token;

    @NotBlank(message = "url不可为空")
    private String url;
}
