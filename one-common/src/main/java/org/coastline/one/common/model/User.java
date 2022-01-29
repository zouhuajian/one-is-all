package org.coastline.one.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jay.H.Zou
 * @date 2022/1/29
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String name;
    private Integer age;
    private Integer sex;
}
