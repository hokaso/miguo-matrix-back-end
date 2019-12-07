package com.miguo.matrix.dto;

import lombok.Data;

@Data
public class SearchDto {
    private int page;
    private int size;
    private String keywords;
}
