package org.coastline.one.spring.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author Jay.H.Zou
 * @date 2021/1/24
 */
@Service
public class ItemService implements IItemService {

    //@Timer(name = "bean_item")
    public String getItem(String key) {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
        }
        return key + " from " + this.getClass().getName();
    }
}
