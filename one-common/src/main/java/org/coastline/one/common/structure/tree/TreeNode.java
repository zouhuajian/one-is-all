package org.coastline.one.common.structure.tree;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TreeNode {

    private int value;
    private TreeNode leftChild;
    private TreeNode rightChild;
}
