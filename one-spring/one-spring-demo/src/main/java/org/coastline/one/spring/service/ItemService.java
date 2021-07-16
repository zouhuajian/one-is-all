package org.coastline.one.spring.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Jay.H.Zou
 * @date 2021/1/24
 */
@Service
public class ItemService {

    @Transactional(rollbackFor = Exception.class)
    public void updateItem() {
        System.out.println("");
    }

}
