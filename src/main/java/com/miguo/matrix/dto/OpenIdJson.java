package com.miguo.matrix.dto;

import lombok.Data;

/**
 * 功能描述：
 *
 * @author Hocassian
 * @date 2020-03-01 16:58
 */
@Data
public class OpenIdJson {
    private String openid;
    private String session_key;
    private String unionid;
}
