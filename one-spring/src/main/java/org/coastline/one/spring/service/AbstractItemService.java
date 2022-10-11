package org.coastline.one.spring.service;

import org.coastline.one.core.annotation.Timer;

/**
 * @author Jay.H.Zou
 * @date 2022/9/28
 */
public abstract class AbstractItemService implements IItemService {

    @Timer(name = "abstract_item")
    @Override
    public String getItem(String key) {
        return this.getClass().getName();
    }
}
