package jp.classmethod.sample.service;

import lombok.Data;

@Data
public class TotalResponse {
    private DmmRequest request;
    private DmmResponse result;
}
