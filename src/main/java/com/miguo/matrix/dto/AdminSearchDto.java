package com.miguo.matrix.dto;

import lombok.Data;
import org.springframework.data.domain.Sort;

/**
 * @author Hocassian
 */
@Data
public class AdminSearchDto {
    private int page;
    private int size;
    private String keywords;
    private Sort.Direction direction;
    private String active;
}
