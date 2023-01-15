package org.coastline.one.core.tool;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

/**
 * @author Jay.H.Zou
 * @date 2021/9/14
 */
public class HashTool {
    private static final HashFunction hashFunctionMurmur3_32 = Hashing.murmur3_32_fixed();
    private static final HashFunction hashFunctionMurmur3_128 = Hashing.murmur3_128();

    private HashTool(){}

    public static String hashMurmur3_32(String origin) {
        HashCode hashCode = hashFunctionMurmur3_32.hashString(origin, StandardCharsets.UTF_8);
        return hashCode.toString();
    }

    public static String hashMurmur3_32(String origin, int seed) {
        HashCode hashCode = Hashing.murmur3_32_fixed(seed).hashString(origin, StandardCharsets.UTF_8);
        return hashCode.toString();
    }

    public static String hashMurmur3_128(String origin) {
        HashCode hashCode = hashFunctionMurmur3_128.hashString(origin, StandardCharsets.UTF_8);
        return hashCode.toString();
    }

}
