package org.coastline.one.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jay.H.Zou
 * @date 2023/10/18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class INode {
    private long id;
    private long parentId;
    private String name;
}
