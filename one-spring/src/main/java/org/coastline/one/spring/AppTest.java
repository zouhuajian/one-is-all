package org.coastline.one.spring;

import org.coastline.one.spring.service.ItemService;
import org.coastline.one.spring.service.SellItemService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author Jay.H.Zou
 * @date 2020/11/10
 */
public class AppTest {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        SellItemService sellItemService = context.getBean("sellItemService", SellItemService.class);
        //ItemService itemService = (ItemService) context.getBean("itemService");
        sellItemService.buy();
        //itemService.buy();
    }

}
