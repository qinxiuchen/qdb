package com.kabam.qdb.storage;

import java.io.IOException;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kabam.qdb.util.Config;
import com.thimbleware.jmemcached.LocalCacheElement;
import com.thimbleware.jmemcached.protocol.exceptions.DatabaseException;
import com.thimbleware.jmemcached.storage.CacheStorage;

import org.iq80.leveldb.*;
import static org.fusesource.leveldbjni.JniDBFactory.*;
import java.io.*;


public class FSStorage implements CacheStorage<String, LocalCacheElement> {

	private final static Log log = LogFactory.getLog(FSStorage.class);
	/**
     * 数据存储路径
     */
    private final static String dbpath = Config.getSetting("path").trim();
	private DB db = null;
    
    public FSStorage(){
    	Options options = new Options();
		options.createIfMissing(true);
		try {
			this.db = factory.open(new File(dbpath + "/qdb"), options);
		} catch (IOException e) {
		}
    }
    
	public long getMemoryCapacity() {
		return 0;
	}

	public long getMemoryUsed() {
		return 0;
	}

	public int capacity() {
		return 0;
	}

	public void close() throws IOException {
        db.close();
    }

	public void clear() throws DatabaseException, Exception {
	}

	public LocalCacheElement remove(String key) throws DatabaseException,
			Exception {
		db.delete(bytes(key));
		return null;
	}

	public LocalCacheElement putIfAbsent(String keystring, LocalCacheElement e)
			throws DatabaseException, Exception {
		return null;
	}

	public LocalCacheElement get(String keystring) {
		if(keystring.isEmpty()) return null;
		String value = asString(this.db.get(bytes(keystring)));
		LocalCacheElement element = new LocalCacheElement(keystring, 0, 0, 0);
        element.setData(value.getBytes());
        return element;
	}

	public boolean replace(String keystring, LocalCacheElement old, LocalCacheElement prepend) throws DatabaseException, Exception {
		return false;
	}

	public LocalCacheElement put(String keystring, LocalCacheElement e) throws DatabaseException, Exception {
		this.db.put(bytes(keystring), bytes(new String(e.getData())));
		return null;
	}

	public long size() {
		return 0;
	}

	public Set<String> keySet() {
		return null;
	}

	public LocalCacheElement replace(String key, LocalCacheElement placeHolder)
			throws DatabaseException, Exception {
		return null;
	}
}
