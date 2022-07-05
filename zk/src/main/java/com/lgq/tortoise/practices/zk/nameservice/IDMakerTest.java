package com.lgq.tortoise.practices.zk.nameservice;

import org.junit.Test;

/**
 * @author lgq
 */
public class IDMakerTest {
    @Test
    public void testMakeId() {
        IDMaker idMaker = new IDMaker();
        idMaker.init();

        String nodeName = "/test/IDMaker/ID-";
        for (int i = 0; i < 10; i++) {
            String id = idMaker.makeId(nodeName);
            System.out.println("第" + i + "个创建的id为:" + id);
        }

        idMaker.destroy();
    }
}
