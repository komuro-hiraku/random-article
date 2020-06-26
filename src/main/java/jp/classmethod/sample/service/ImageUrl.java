package jp.classmethod.sample.service;

import lombok.Data;

/**
 * Imageの各種URLを格納するEntity
 */
@Data
public class ImageUrl {

    private String list;
    private String small;
    private String large;
}
