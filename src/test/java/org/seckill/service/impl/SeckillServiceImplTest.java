package org.seckill.service.impl;

import org.junit.*;
import org.junit.runner.*;
import org.seckill.dto.*;
import org.seckill.entity.*;
import org.seckill.exception.*;
import org.seckill.service.*;
import org.slf4j.*;
import org.springframework.test.context.*;
import org.springframework.test.context.junit4.*;

import javax.annotation.*;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by pc on 2017/3/6.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath:spring/spring-dao.xml",
        "classpath:spring/spring-service.xml"
})
public class SeckillServiceImplTest {
    Logger logger=LoggerFactory.getLogger(this.getClass());
    @Resource
    private SeckillService seckillService;

    @Test
    public void testGetSeckillList() throws Exception {
        List<Seckill> seckillList=seckillService.getSeckillList();
        logger.info("list={}",seckillList);
    }

    @Test
    public void testGetById() throws Exception {
        Seckill seckill=seckillService.getById(1000L);
        logger.info("seckill={}",seckill);

    }
    //集成测试代码完整逻辑，注意可重复执行
    @Test
    public void testSeckillLogic() throws Exception {
        //exposer=Exposer{exposed=true, md5='ae8c840b6c3a4891c593ec70b9dc2c06',
        // seckillId=1000, now=0, start=0, end=0}
        long id=1001L;
        Exposer exposer=seckillService.exposeSeckillUrl(id);
        logger.info("exposer={}",exposer);
        if(exposer!=null){
            //用try-catch包围才能单元测试通过，否则抛出异常视为不通过
            try{
                long userPhone=18826077189L;
                SeckillExecution seckillExecution=seckillService.executeSeckill(id, userPhone, exposer.getMd5());
                logger.info("seckillExecution={}",seckillExecution);
            }catch(RepeatKillException e){
                logger.error(e.getMessage());
            }catch (SeckillCloseException e){
                logger.error(e.getMessage());
            }catch (SeckillException e){
                logger.error(e.getMessage());
            }
        }else{
            logger.warn("exposer={}",exposer);
        }
    }

    @Test
    public void testProcedure(){
        long seckillId=1001L;
        long userPhone=18826077188L;
        Exposer exposer = seckillService.exposeSeckillUrl(seckillId);
        if(exposer.isExposed()){
            String md5=exposer.getMd5();
            SeckillExecution execution=seckillService.executeSeckill(seckillId, userPhone, md5);
            logger.info(execution.getStateInfo());
        }
    }
}