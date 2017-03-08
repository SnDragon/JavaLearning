package org.seckill.dao;

import org.junit.*;
import org.junit.runner.*;
import org.seckill.entity.*;
import org.springframework.test.context.*;
import org.springframework.test.context.junit4.*;

import javax.annotation.*;
import java.util.*;

/**
 * Created by pc on 2017/3/5.
 */
//配置spring和junit整合，这样junit在启动时就会加载spring容器
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-dao.xml")
public class SeckillDaoTest {
    //注入Dao实现类依赖
    @Resource
    private SeckillDao seckillDao;

    @Test
    public void queryById(){
        long seckillId=1001;
        Seckill seckill=seckillDao.queryById(seckillId);
        System.out.println(seckill.getName());
        System.out.println(seckill);
    }

    @Test
    public void queryAll(){
        List<Seckill> seckillList=seckillDao.queryAll(0,100);
        for(Seckill seckill:seckillList){
            System.out.println(seckill);
        }
    }

    @Test
    public void reduceNumber(){
        long id=1000;
        int updateCount=seckillDao.reduceNumber(1000, new Date());
        System.out.println(updateCount);
    }
}

