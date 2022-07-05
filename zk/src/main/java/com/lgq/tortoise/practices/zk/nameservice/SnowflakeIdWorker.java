package com.lgq.tortoise.practices.zk.nameservice;

import com.lgq.tortoise.practices.zk.util.ZKclient;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;

/**
 * @author lgq
 */
public class SnowflakeIdWorker {
    //Zk客户端
    transient private CuratorFramework zkClient = null;

    /**
     * 工作节点的路径
     */
    private static final String PATH_PREFIX = "/test/IDMaker/worker-";

    private String pathRegistered = null;

    public static SnowflakeIdWorker instance = new SnowflakeIdWorker();

    private SnowflakeIdWorker() {
        this.zkClient = ZKclient.instance.getClient();
        this.init();
    }

    /**
     * 在zookeeper中创建临时节点并写入信息
     */
    public void init() {
        // 创建一个 ZNode 节点
        // 节点的 payload 为当前worker 实例
        try {
            byte[] payload = PATH_PREFIX.getBytes();
            pathRegistered = zkClient.create()
                    .creatingParentsIfNeeded()
                    .withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
                    .forPath(PATH_PREFIX, payload);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public long getId() {
        String sid = null;
        if (null == pathRegistered) {
            throw new IllegalArgumentException("节点注册失败");
        }

        int index = pathRegistered.lastIndexOf(PATH_PREFIX);
        if (index >= 0) {
            index += PATH_PREFIX.length();
            sid = index <= pathRegistered.length() ? pathRegistered.substring(index) : null;
        }

        if (null == sid) {
            throw new RuntimeException("节点ID生成失败");
        }

        return Long.parseLong(sid);
    }
}
