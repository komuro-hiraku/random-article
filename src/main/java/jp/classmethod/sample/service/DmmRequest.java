package jp.classmethod.sample.service;

import lombok.Data;

import java.util.Map;

@Data
public class DmmRequest {
    private Map<String, String> parameters;
}
