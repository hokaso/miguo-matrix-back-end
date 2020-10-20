package com.miguo.matrix.dto.staff;

import lombok.Data;

@Data
public class UpdatePasswordDto {
    // 原密码
    private String beforePassword;

    // 新密码
    private String afterPassword;
}
