package com.miguo.matrix.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.miguo.matrix.utils.EnumSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;
import java.io.Serializable;

/**
 * @author Hocassian
 */
@ApiModel("统一返回Dto")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class Result<T> implements Serializable {

    @ApiModelProperty("响应码")
    @JsonSerialize(using = EnumSerializer.class)
    private HttpStatus code;

    @ApiModelProperty("描述")
    private String message;

    @ApiModelProperty("数据")
    private T data;

}
