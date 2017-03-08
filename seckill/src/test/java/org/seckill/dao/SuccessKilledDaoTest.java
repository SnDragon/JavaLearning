package org.seckill.dao;

import org.junit.*;
import org.junit.runner.*;
import org.seckill.entity.*;
import org.springframework.test.context.*;
import org.springframework.test.context.junit4.*;

import javax.annotation.*;

/**
 * Created by pc on 2017/3/5.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-dao.xml")
public class SuccessKilledDaoTest {
    @Resource
    private SuccessKilledDao successKilledDao;

    @Test
    public void insertSuccessKilled(){
        long seckillId=1000L;
        long userPhone=18826077187L;
        int updateCount=successKilledDao.insertSuccessKilled(seckillId,userPhone);
        System.out.println(updateCount);
    }

    @Test
    public void queryByIdWithSeckill(){

        SuccessKilled successKilled=successKilledDao.queryByIdWithSeckill(1000L,18826077187L);
        System.out.println(successKilled);
        System.out.println(successKilled.getSeckill());

    }
}
