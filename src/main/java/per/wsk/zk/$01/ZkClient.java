package per.wsk.zk.$01;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class ZkClient {

    //链接地址
    private String connectString="CentOS7.9_02:2181,CentOS7.9_02:2181,CentOS7.9_02:2181";
//    private String connectString="wwww";
    //超时时间
    private int sessionTimeout=2000;

    private ZooKeeper zkClient;

    /**
     * 连接上zookeeper
     * @throws IOException
     */
    @Before
    public void init() throws IOException {

        zkClient = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                System.out.println("----------------------------");
                List<String> children = null;
                try {
                    children = zkClient.getChildren("/", true);
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                children.forEach(System.out::println);
            }
        });
    }


    /**
     * 在zookeeper集群中，创建节点
     * @throws KeeperException
     * @throws InterruptedException
     */
    @Test
    public void create() throws KeeperException, InterruptedException {
        //参数1表示zookeeper的节点路径，参数2是数据，参数3是表示该节点的访问权限，参数4是表示该节点是 持久或临时节点、有序或无序节点
        String node = zkClient.create("/nba", "celtics".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }


    @Test
    public void getChildren() throws KeeperException, InterruptedException {
        //第一个参数，是查看路径，第二个参数，是是否监听
        List<String> children = zkClient.getChildren("/", false);
        children.forEach(System.out::println);
    }



    @Test
    public void getChildren2() throws KeeperException, InterruptedException {
        // 延时
        Thread.sleep(Long.MAX_VALUE);
    }


    /**
     * 查看节点是否存在
     * @throws KeeperException
     * @throws InterruptedException
     */
    @Test
    public void exist() throws KeeperException, InterruptedException {

        Stat stat = zkClient.exists("/b", false);

        System.out.println(stat==null? "not exist " : "exist");
    }


}
