package org.coastline.one.core.compress;

import com.github.luben.zstd.Zstd;
import org.xerial.snappy.Snappy;

import java.io.IOException;

/**
 * @author Jay.H.Zou
 * @date 2022/5/16
 */
public class CompressorTool {

    private CompressorTool() {
    }

    public static byte[] compressSnappy(byte[] origin) throws IOException {
        return Snappy.compress(origin);
    }

    public static byte[] uncompressSnappy(byte[] origin) throws IOException {
        return Snappy.uncompress(origin);
    }

    public static byte[] compressZstd(byte[] origin) throws IOException {
        return Zstd.compress(origin);

    }

    public static byte[] uncompressZstd(byte[] origin) throws IOException {
        int size = (int) Zstd.decompressedSize(origin);
        return Zstd.decompress(origin, size);
    }

}
