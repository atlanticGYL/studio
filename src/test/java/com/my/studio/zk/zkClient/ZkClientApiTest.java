package com.my.studio.zk.zkClient;

import org.I0Itec.zkclient.ZkClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ZkClientApiTest {

    @Autowired
    private ZkClient zkClient;

    @Test
    public void zkTest() {
        System.out.println(zkClient.exists("/zookeeper"));
    }
}
