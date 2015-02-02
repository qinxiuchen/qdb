package com.kabam.qdb;

import java.net.InetSocketAddress;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kabam.qdb.storage.FSStorage;
import com.thimbleware.jmemcached.CacheImpl;
import com.thimbleware.jmemcached.LocalCacheElement;
import com.thimbleware.jmemcached.MemCacheDaemon;
import com.thimbleware.jmemcached.storage.CacheStorage;

/**
 * @author xcqin
 * @date 2013-01-07
 */

public class Qdb {

    private static final Log log = LogFactory.getLog(Qdb.class);

    public static void newInstance(int port) {
        InetSocketAddress addr = new InetSocketAddress("0.0.0.0", port);
        int idle = -1;
        boolean verbose = false;
        MemCacheDaemon.memcachedVersion = "0.1";
        final MemCacheDaemon<LocalCacheElement> daemon = new MemCacheDaemon<LocalCacheElement>();
        CacheStorage<String, LocalCacheElement> storage = new FSStorage();
        CacheImpl cacheImpl = new CacheImpl(storage);
        daemon.setCache(cacheImpl);
        daemon.setAddr(addr);
        daemon.setBinary(false);
        daemon.setIdleTime(idle);
        daemon.setVerbose(verbose);
        daemon.start();
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                if (daemon != null && daemon.isRunning())
                    daemon.stop();
                log.info("shutdown server");
            }
        }));
    }

}
