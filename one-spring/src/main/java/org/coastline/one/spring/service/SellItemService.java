package org.coastline.one.spring.service;

import org.springframework.stereotype.Component;

/**
 * @author Jay.H.Zou
 * @date 2020/11/10
 */
@Component
public class SellItemService implements IItemService {
    @Override
    public String buy() {
        System.out.println("buy something on sell");
        return "success";
    }
}
