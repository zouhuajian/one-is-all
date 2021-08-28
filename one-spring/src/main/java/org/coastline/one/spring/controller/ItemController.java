package org.coastline.one.spring.controller;

import org.coastline.one.spring.model.Result;
import org.coastline.one.spring.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/item")
@RestController
public class ItemController {

    @Autowired
    private ItemService itemService;

    @PostMapping(value = "/set")
    public Result<String> setKey(@RequestParam("key") String key, @RequestParam("value") String value) {
        return Result.ofSuccess(value);
    }

    @GetMapping(value = "/get")
    public Result<String> getList(@RequestParam("key") String key) {
        String item = itemService.getItem(key);
        return Result.ofSuccess(item);
    }

}