package com.lgq.tortoise.practices.zk.publishsubscribe;

import com.lgq.tortoise.practices.zk.util.ZKclient;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

/**
 * @author lgq
 */
public class ZkWatcher {
    private static final String WORKER_PATH = "/test/listener/remoteNode";
    private static final String SUB_WORKER_PATH = "/test/listener/remoteNode/id-";

    @Test
    public void testWatcher() {
        CuratorFramework client = ZKclient.instance.getClient();

        //检查节点是否存在，没有则创建
        boolean isExist = ZKclient.instance.isNodeExist(WORKER_PATH);
        if (!isExist) {
            ZKclient.instance.createNode(WORKER_PATH, null);
        }

        try {
            Watcher w = new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    System.out.println("监听到的变化 watchedEvent = " + watchedEvent);
                }
            };

            byte[] content = client.getData().usingWatcher(w).forPath(WORKER_PATH);

            System.out.println("监听节点内容：" + new String(content));

            // 第一次变更节点数据
            client.setData().forPath(WORKER_PATH, "第1次更改内容".getBytes());

            // 第二次变更节点数据
            client.setData().forPath(WORKER_PATH, "第2次更改内容".getBytes());

            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testNodeCache() {
        //检查节点是否存在，没有则创建
        boolean isExist = ZKclient.instance.isNodeExist(WORKER_PATH);
        if (!isExist) {
            ZKclient.instance.createNode(WORKER_PATH, null);
        }

        CuratorFramework client = ZKclient.instance.getClient();
        try {
            NodeCache nodeCache = new NodeCache(client, WORKER_PATH, false);

            NodeCacheListener nodeCacheListener = new NodeCacheListener() {
                @Override
                public void nodeChanged() throws Exception {
                    ChildData childData = nodeCache.getCurrentData();
                    System.out.println("ZNode节点状态改变, path={}" + childData.getPath());
                    System.out.println("ZNode节点状态改变, data={}" + new String(childData.getData(), "Utf-8"));
                    System.out.println("ZNode节点状态改变, stat={}" + childData.getStat());
                }
            };
            nodeCache.getListenable().addListener(nodeCacheListener);
            nodeCache.start();

            // 第1次变更节点数据
            client.setData().forPath(WORKER_PATH, "第1次更改内容".getBytes());
            Thread.sleep(1000);

            // 第2次变更节点数据
            client.setData().forPath(WORKER_PATH, "第2次更改内容".getBytes());
            Thread.sleep(1000);

            // 第3次变更节点数据
            client.setData().forPath(WORKER_PATH, "第3次更改内容".getBytes());
            Thread.sleep(1000);

            // 第4次变更节点数据
            client.delete().forPath(WORKER_PATH);

            Thread.sleep(Integer.MAX_VALUE);
        } catch (Exception e) {
            System.out.println("创建NodeCache监听失败, path={}" + WORKER_PATH);
        }
    }

    @Test
    public void testPathChildrenCache() {
        //检查节点是否存在，没有则创建
        boolean isExist = ZKclient.instance.isNodeExist(WORKER_PATH);
        if (!isExist) {
            ZKclient.instance.createNode(WORKER_PATH, null);
        }

        CuratorFramework client = ZKclient.instance.getClient();
        try {
            PathChildrenCache cache = new PathChildrenCache(client, WORKER_PATH, true);

            PathChildrenCacheListener childrenCacheListener = (client1, event) -> {
                String logContent = null;
                try {
                    ChildData data = event.getData();
                    switch (event.getType()) {
                        case CHILD_ADDED:
                            logContent = String.format("子节点增加, path=%s, data=%s", data.getPath(), new String(data.getData(), "UTF-8"));
                            System.out.println(logContent);
                            break;
                        case CHILD_UPDATED:
                            logContent = String.format("子节点更新, path=%s, data=%s", data.getPath(), new String(data.getData(), "UTF-8"));
                            System.out.println(logContent);
                            break;
                        case CHILD_REMOVED:
                            logContent = String.format("子节点删除, path=%s, data=%s", data.getPath(), new String(data.getData(), "UTF-8"));
                            System.out.println(logContent);
                            break;
                        default:
                            break;
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            };

            cache.getListenable().addListener(childrenCacheListener);
            cache.start(PathChildrenCache.StartMode.BUILD_INITIAL_CACHE);

            Thread.sleep(1000);
            for (int i = 0; i < 3; i++) {
                ZKclient.instance.createNode(SUB_WORKER_PATH + i, null);
            }

            Thread.sleep(1000);
            for (int i = 0; i < 3; i++) {
                ZKclient.instance.deleteNode(SUB_WORKER_PATH + i);
            }
        } catch (Exception e) {
            System.out.println("PathCache监听失败, path=" + WORKER_PATH);
        }
    }

    @Test
    public void testTreeCache() {
        //检查节点是否存在，没有则创建
        boolean isExist = ZKclient.instance.isNodeExist(WORKER_PATH);
        if (!isExist) {
            ZKclient.instance.createNode(WORKER_PATH, null);
        }

        CuratorFramework client = ZKclient.instance.getClient();

        try {
            TreeCache treeCache = new TreeCache(client, WORKER_PATH);

            TreeCacheListener treeCacheListener = (client1, event) -> {
                try {
                    ChildData data = event.getData();
                    if (data == null) {
                        System.out.println("数据为空");
                        return;
                    }

                    String logContent = null;
                    switch (event.getType()) {
                        case NODE_ADDED:
                            logContent = String.format("[TreeCache]节点增加, path=%s, data=%s", data.getPath(), new String(data.getData(), "UTF-8"));
                            System.out.println(logContent);
                            break;
                        case NODE_UPDATED:
                            logContent = String.format("[TreeCache]节点更新, path=%s, data=%s", data.getPath(), new String(data.getData(), "UTF-8"));
                            System.out.println(logContent);
                            break;
                        case NODE_REMOVED:
                            logContent = String.format("[TreeCache]节点删除, path=%s, data=%s", data.getPath(), new String(data.getData(), "UTF-8"));
                            System.out.println(logContent);
                            break;
                        default:
                            break;
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            };

            treeCache.getListenable().addListener(treeCacheListener);
            treeCache.start();

            Thread.sleep(1000);
            for (int i = 0; i < 3; i++) {
                ZKclient.instance.createNode(SUB_WORKER_PATH + i, null);
            }
            Thread.sleep(1000);

            for (int i = 0; i < 3; i++) {
                ZKclient.instance.deleteNode(SUB_WORKER_PATH + i);
            }
            Thread.sleep(1000);

            ZKclient.instance.deleteNode(WORKER_PATH);
            Thread.sleep(Integer.MAX_VALUE);
        } catch (Exception e) {
            System.out.println("PathCache监听失败, path=" + WORKER_PATH);
        }
    }
}
