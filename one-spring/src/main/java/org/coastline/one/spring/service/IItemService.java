package org.coastline.one.spring.service;

import org.coastline.one.core.annotation.Timer;

/**
 * @author Jay.H.Zou
 * @date 2022/9/28
 */
public interface IItemService {

    @Timer(name = "interface_item")
    String getItem(String key);
}
