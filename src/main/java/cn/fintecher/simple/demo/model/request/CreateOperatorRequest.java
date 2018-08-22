package cn.fintecher.simple.demo.model.request;

import lombok.Data;

/**
 * Created by ChenChang on 2018/8/16.
 */
@Data
public class CreateOperatorRequest {
    private String cn;
    private String sn;
    private String userPassword;
}
