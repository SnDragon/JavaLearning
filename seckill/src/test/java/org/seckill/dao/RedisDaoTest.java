package org.seckill.dao;

import org.junit.*;
import org.junit.runner.*;
import org.seckill.entity.*;
import org.springframework.test.context.*;
import org.springframework.test.context.junit4.*;

import javax.annotation.*;

import static org.junit.Assert.*;

/**
 * Created by pc on 2017/3/8.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-dao.xml")
public class RedisDaoTest {
    private long id=1001L;

    @Resource
    private RedisDao redisDao;
    @Resource
    private SeckillDao seckillDao;


    @Test
    public void testSeckill() throws Exception {
        Seckill seckill=redisDao.getSeckill(id);
        if(seckill==null){
            seckill = seckillDao.queryById(id);
            if(seckill!=null){
               String result = redisDao.putSeckill(seckill);
                System.out.println(result);
                seckill = redisDao.getSeckill(id);
                System.out.println(seckill);
            }
        }
    }

}