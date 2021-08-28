package org.coastline.one.spring.service;

import org.springframework.stereotype.Service;

/**
 * @author Jay.H.Zou
 * @date 2021/1/24
 */
@Service
public class ItemService {

    public String getItem(String key) {
        return this.getClass().getName();
    }



}
