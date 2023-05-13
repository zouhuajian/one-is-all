package org.coastline.one.core.test.tool;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;
import org.junit.Test;

/**
 * @author Jay.H.Zou
 * @date 2023/5/12
 */
public class TestHashTool {

    @Test
    public void testMurmur_3() {
        HashFunction hashFunction = Hashing.murmur3_128(0);
        Hasher hasher = hashFunction.newHasher();
        byte[] bytes = hasher.hash().asBytes();
        System.out.println(new String(bytes));
    }
}
