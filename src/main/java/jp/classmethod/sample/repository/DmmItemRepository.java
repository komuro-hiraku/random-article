package jp.classmethod.sample.repository;


import jp.classmethod.sample.service.DmmItem;

import java.util.Collection;
import java.util.List;

public interface DmmItemRepository {

    List<DmmItem> fetchDmmItems(int hits); // 指定のアイテム数をFetchする

    List<DmmItem> fetchAllItems();
}
