package xyz.dingxs.subscribe.subscribe.req;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdatePortReq {

    @NotNull(message = "port不能为空")
    private Integer port;
}
