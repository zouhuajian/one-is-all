package org.coastline.one.spring.controller;

import org.coastline.one.spring.model.Item;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1")
@RestController
public class ItemController {

    @GetMapping(value = "/items/{id}")
    public ResponseEntity<Item> getList(@PathVariable("id") Integer id) {
        Item item = new Item();
        return ResponseEntity.ok(item);
    }

    @PostMapping(value = "/items")
    public ResponseEntity<String> setKey(@RequestParam("key") String id, @RequestParam("value") String name) {
        return ResponseEntity.ok(name);
    }

}