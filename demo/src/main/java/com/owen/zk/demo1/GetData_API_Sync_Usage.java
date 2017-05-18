package com.owen.zk.demo1;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;

/**
 * ZooKeeper API 获取节点数据内容，使用同步sync接口
 * 
 * @author owen
 *
 */
public class GetData_API_Sync_Usage implements Watcher {

    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);

    private static ZooKeeper      zk                 = null;

    private static Stat           stat               = new Stat();

    public static void main(String[] args) throws Exception {
        String path = "/zk-book";
        zk = new ZooKeeper("127.0.0.1:2181", 5000, new GetData_API_Sync_Usage());
        connectedSemaphore.await();
        zk.create(path, "testData".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        System.out.println("first:"+new String(zk.getData(path, true, stat)));
        System.out.println(stat.getCzxid() + "," + stat.getMzxid() + "," + stat.getVersion());
        zk.setData(path, "testData1".getBytes(), -1);
        Thread.sleep(Integer.MAX_VALUE);
    }

    public void process(WatchedEvent event) {
        if (KeeperState.SyncConnected == event.getState()) {
            if (EventType.None == event.getType() && null == event.getPath()) {
                connectedSemaphore.countDown();
                System.out.println("common");
            } else if(EventType.NodeDataChanged == event.getType()){
                try {
                    System.out.println("second:"+new String(zk.getData(event.getPath(), true, stat)));
                    System.out.println(stat.getCzxid() + "," + stat.getMzxid() + "," + stat.getVersion());
                } catch (Exception e) {
                    System.out.println("exception...");
                }
            }
        }
    }

}
