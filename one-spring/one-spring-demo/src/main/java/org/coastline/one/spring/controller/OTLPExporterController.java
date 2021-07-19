package org.coastline.one.spring.controller;

import com.alibaba.fastjson.JSONObject;
import org.coastline.one.spring.model.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * OTLP HTTP
 *
 * @author Jay.H.Zou
 * @date 2021/7/16
 */
@RestController
public class OTLPExporterController {

    @PostMapping(value = "/v1/metrics")
    public Result<String> exporter(Object obj) {
        String s = JSONObject.toJSONString(obj);
        System.out.println(s);
        return Result.ofSuccess("success");
    }
}
