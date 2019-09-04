package org.mc.spring.zookeeper.demo;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @auther chao.ma06@ucarinc.com
 * @date 2019/9/4 15:53
 */


public class ZookeeperConnect implements Watcher {

    private static CountDownLatch countDownLatch=new CountDownLatch(1);

    public static void createSession() throws IOException, InterruptedException {
        ZooKeeper zookeeper=new ZooKeeper("10.104.115.44:5181,10.104.115.45:5181,10.104.115.46:5181",
                5000,new ZookeeperConnect());
        System.out.println("zk状态："+zookeeper.getState());
        countDownLatch.await();
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println("接收watch通知："+watchedEvent);
        if(Event.KeeperState.SyncConnected==watchedEvent.getState()){
            countDownLatch.countDown();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        createSession();
    }


}
