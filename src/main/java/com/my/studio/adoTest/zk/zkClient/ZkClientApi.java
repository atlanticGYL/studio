package com.my.studio.adoTest.zk.zkClient;

import com.my.studio.adoTest.zk.ZKConfig;
import org.I0Itec.zkclient.ZkClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ZkClientApi {

    @Autowired
    private ZKConfig zkConfig;

    @Bean(name = "zkClient")
    public ZkClient getZkClient() {
        return new ZkClient(zkConfig.getAddress());
    }

}
