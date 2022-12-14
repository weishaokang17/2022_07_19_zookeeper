package per.wsk.zk.$04;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class CuratorLockTest {


    public static void main(String[] args) {
        // 创建分布式锁1
        InterProcessMutex lock1 = new InterProcessMutex(getCuratorFramework(), "/locks");
        //创建分布式锁2
        InterProcessMutex lock2 = new InterProcessMutex(getCuratorFramework(), "/locks");

        new Thread(()->{
            try {

                lock1.acquire();
                System.out.println("线程1获得到锁");

                lock1.acquire();
                System.out.println("线程1再次获得到锁");

                Thread.sleep(5*1000);

                lock1.release();
                System.out.println("线程1释放锁");

                lock1.release();
                System.out.println("线程1 再次释放锁");


            } catch (Exception e) {
                e.printStackTrace();
            }

        }).start();


        new Thread(()->{
            try {

                lock2.acquire();
                System.out.println("线程2获得到锁");

                lock2.acquire();
                System.out.println("线程2再次获得到锁");

                Thread.sleep(5*1000);

                lock2.release();
                System.out.println("线程2释放锁");

                lock2.release();
                System.out.println("线程2 再次释放锁");


            } catch (Exception e) {
                e.printStackTrace();
            }

        }).start();
    }



    private static CuratorFramework getCuratorFramework(){
        ExponentialBackoffRetry policy = new ExponentialBackoffRetry(3000, 3);

        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString("CentOS7.9_02:2181,CentOS7.9_03:2181,CentOS7.9_04:2181")
                .sessionTimeoutMs(2000)
                .retryPolicy(policy)
                .build();

        //启动客户端
        client.start();

        System.out.println("zookeeper 启动成功");

        return client;
    }

}
