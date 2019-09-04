package org.mc.spring.zookeeper.demo;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @auther chao.ma06@ucarinc.com
 * @date 2019/9/4 16:17
 */
public class CreateNode implements Watcher {

    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void createNode() throws IOException, InterruptedException, KeeperException {
        ZooKeeper zookeeper = new ZooKeeper("10.104.115.44:5181,10.104.115.45:5181,10.104.115.46:5181",
                5000, new ZookeeperConnect());
        System.out.println("zk状态：" + zookeeper.getState());
        countDownLatch.await();

        String path1 = zookeeper.create("/temp1", "temp123".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        System.out.println("返回路径 path1: "+path1);

        String path2 = zookeeper.create("/temp2", "temp456".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println("返回路径 path2: "+path2);


    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println("接收watch通知："+watchedEvent);
        if(Watcher.Event.KeeperState.SyncConnected==watchedEvent.getState()){
            countDownLatch.countDown();
        }
    }

    public static void main(String[] args) throws InterruptedException, IOException, KeeperException {
        createNode();
    }


}
