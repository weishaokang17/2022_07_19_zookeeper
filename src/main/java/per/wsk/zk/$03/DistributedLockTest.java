package per.wsk.zk.$03;

import org.apache.zookeeper.KeeperException;

import java.io.IOException;

public class DistributedLockTest {


    public static void main(String[] args) throws InterruptedException, IOException, KeeperException {
        final DistributedLock lock1 = new DistributedLock();
        final DistributedLock lock2 = new DistributedLock();

        new Thread(()->{
            try {
                lock1.zkLock();
                System.out.println("线程1 启动，获取到锁");
                Thread.sleep(5*1000);

                lock1.unZkLock();
                System.out.println("线程1 释放锁");
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();


        new Thread(()->{
            try {
                lock2.zkLock();
                System.out.println("线程2 启动 ，获取到锁");
                Thread.sleep(5*1000);

                lock2.unZkLock();
                System.out.println("线程2 释放锁");
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
