package jp.classmethod.sample.service;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Imageの各種URLを格納するEntity
 */
@Data
@AllArgsConstructor
public class ImageUrl {

    private String list;
    private String small;
    private String large;
}
