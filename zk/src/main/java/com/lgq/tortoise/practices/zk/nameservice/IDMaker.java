package com.lgq.tortoise.practices.zk.nameservice;

import com.lgq.tortoise.practices.zk.util.ClientFactory;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;

/**
 * @author lgq
 */
public class IDMaker {
    private static final String ZK_ADDRESS = "127.0.0.1:2181";

    //Zk客户端
    CuratorFramework client = null;

    public void init() {
        //创建客户端
        client = ClientFactory.createSimple(ZK_ADDRESS);

        //启动客户端实例,连接服务器
        client.start();
    }

    public void destroy() {
        if (null != client) {
            client.close();
        }
    }

    private String createSeqNode(final String pathPrefix) {
        try {
            // 创建一个 ZNode 顺序节点
            return client.create()
                    .creatingParentsIfNeeded()
                    .withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
                    //避免zookeeper的顺序节点暴增，需要删除创建的持久化顺序节点
                    //.withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
                    .forPath(pathPrefix);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String makeId(final String nodeName) {
        String str = createSeqNode(nodeName);

        if (null == str) {
            return null;
        }

        int index = str.lastIndexOf(nodeName);
        if (index >= 0) {
            index += nodeName.length();
            return index <= str.length() ? str.substring(index) : "";
        }
        return str;
    }
}
