package org.coastline.one.spring.service;

import org.coastline.one.core.annotation.Timer;
import org.springframework.stereotype.Service;

/**
 * @author Jay.H.Zou
 * @date 2021/1/24
 */
@Service
public class ItemService extends AbstractItemService {

    //@Timer(name = "bean_item")
    public String getItem(String key) {
        return this.getClass().getName();
    }
}
