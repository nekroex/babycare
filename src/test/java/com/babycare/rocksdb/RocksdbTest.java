package com.babycare.rocksdb;


import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;

import java.util.Arrays;
import java.util.Map;


/**
 * <p>
 * </p>
 * <p> Date: 2016-08-17 Time: 10:22 </p>
 *
 * @author jiyong.jy
 */
public class RocksdbTest {

    RocksDB db;

    @Before
    public void before() {
        RocksDB.loadLibrary();
        // the Options class contains a set of configurable DB options
        // that determines the behavior of a database.
        Options options = new Options().setCreateIfMissing(true);
        try {
            // a factory method that returns a RocksDB instance
            db = RocksDB.open(options, "rocksdb");
            // do something
        } catch (RocksDBException e) {
            // do some error handling
        }
    }

    @After
    public void after() throws RocksDBException {
        if(db != null) {
            db.enableFileDeletions(true);
            db.close();
        }
    }


    @Test
    public void getSet() throws RocksDBException {
        byte[] key = "getSet".getBytes();
        byte[] value = "getSet".getBytes();
        db.put(key, value);
        byte[] bytes = db.get(key);
        Assert.assertTrue(Arrays.equals(value, bytes));
    }

    @Test
    public void scan() throws RocksDBException {
        byte[] key1 = "scan1".getBytes();
        byte[] key2 = "scan2".getBytes();

        byte[] value1 = "scan1".getBytes();
        byte[] value2 = "scan2".getBytes();

        db.put(key1, value1);
        db.put(key2, value2);

        Map<byte[], byte[]> map = db.multiGet(Arrays.asList(key1, key2));

        Assert.assertEquals(2, map.size());

        for(Map.Entry<byte[], byte[]> entry : map.entrySet()) {
            byte[] key = entry.getKey();
            byte[] value = entry.getValue();
            if(Arrays.equals(key, key1)) {
                Assert.assertTrue(Arrays.equals(value, value1));
            } else if (Arrays.equals(key, key2)) {
                Assert.assertTrue(Arrays.equals(value, value2));
            }
        }
    }

}
