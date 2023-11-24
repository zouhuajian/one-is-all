package org.coastline.one.common.rocksdb;

import org.coastline.one.common.model.User;
import org.coastline.one.core.codec.ProtostuffCodec;
import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;
import org.rocksdb.WriteOptions;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * @author Jay.H.Zou
 * @date 2023/8/4
 */
public class TestRocksDB {
    static ProtostuffCodec<User> codec = ProtostuffCodec.create(User.class);
    private final static String NAME = "first-db";
    static File dbDir;
    static RocksDB db;

    public static void main(String[] args) throws IOException, RocksDBException {
        initialize();
        String key = "one";
       // User user = new User(key, 18, 0);
        // put(user);
        get(key);

        //User user2 = new User(key, 21, 0);
        //put(user2);
        //get(key);

        close();
    }

    private static void put(User user) throws RocksDBException {
        byte[] key = user.getName().getBytes();
        WriteOptions writeOptions = new WriteOptions();
        writeOptions.disableWAL();
        db.put(writeOptions, key, codec.encode(user));
    }

    private static void get(String key) throws RocksDBException {
        byte[] bytes = db.get(key.getBytes());
        User user = codec.decode(bytes);
        System.out.println(user);
    }

    private static void initialize() throws IOException, RocksDBException {
        RocksDB.loadLibrary();
        final Options options = new Options();
        options.setCreateIfMissing(true);
        dbDir = new File("/tmp/rocks-db", NAME);
        Files.createDirectories(dbDir.getParentFile().toPath());
        Files.createDirectories(dbDir.getAbsoluteFile().toPath());
        db = RocksDB.open(options, dbDir.getAbsolutePath());
    }

    private static void close() {
        db.close();
    }
}
