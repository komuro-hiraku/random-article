package jp.classmethod.sample.repository;


import jp.classmethod.sample.service.DmmItem;

import java.util.List;

public interface DmmItemRepository {

    List<DmmItem> fetchDmmItem(int hits); // 指定のアイテム数をFetchする
}
