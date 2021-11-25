package xyz.dingxs.subscribe.subscribe.req;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdatePortReq {

    @NotNull(message = "port不能为空")
    private Integer port;
}
