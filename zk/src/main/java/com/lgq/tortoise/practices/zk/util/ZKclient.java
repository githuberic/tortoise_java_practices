package com.lgq.tortoise.practices.zk.util;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.utils.CloseableUtils;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

/**
 * @author lgq
 */
public class ZKclient {
    private CuratorFramework client;

    //Zk集群地址
    private static final String ZK_ADDRESS = "127.0.0.1:2181";

    public static ZKclient instance = null;

    static {
        instance = new ZKclient();
        instance.init();
    }

    private ZKclient() {
    }

    public CuratorFramework getClient() {
        return client;
    }

    public void init() {
        if (null != client) {
            return;
        }

        //创建客户端
        client = ClientFactory.createSimple(ZK_ADDRESS);

        //启动客户端实例,连接服务器
        client.start();
    }

    public void destroy() {
        CloseableUtils.closeQuietly(client);
    }

    /**
     * 创建节点
     */
    public void createNode(String zkPath, String data) {
        try {
            // 创建一个 ZNode 节点节点的数据为 payload
            byte[] payload = "zk_content".getBytes("UTF-8");
            if (data != null) {
                payload = data.getBytes("UTF-8");
            }

            client.create()
                    .creatingParentsIfNeeded()
                    .withMode(CreateMode.PERSISTENT)
                    .forPath(zkPath, payload);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除节点
     */
    public void deleteNode(String zkPath) {
        try {
            if (!isNodeExist(zkPath)) {
                return;
            }
            client.delete().forPath(zkPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 检查节点
     */
    public boolean isNodeExist(String zkPath) {
        try {
            Stat stat = client.checkExists().forPath(zkPath);
            if (null == stat) {
                System.out.println("节点不存在:{}" + zkPath);
                return false;
            } else {
                System.out.println("节点存在 stat is:{}" + stat.toString());
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 创建 临时 顺序 节点
     */
    public String createEphemeralSeqNode(String srcpath) {
        try {
            // 创建一个 ZNode 节点
            return client.create()
                    .creatingParentsIfNeeded()
                    .withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
                    .forPath(srcpath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
