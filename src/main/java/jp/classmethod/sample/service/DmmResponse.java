package jp.classmethod.sample.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Collection;

@Data
public class DmmResponse {

    private int status;
    private int resultCount;
    private int firstPosition;

    private Collection<DmmItem> items;
}
