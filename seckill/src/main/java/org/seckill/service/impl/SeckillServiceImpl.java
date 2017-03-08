package org.seckill.service.impl;

import org.apache.commons.collections.*;
import org.seckill.dao.*;
import org.seckill.dto.*;
import org.seckill.entity.*;
import org.seckill.enums.*;
import org.seckill.exception.*;
import org.seckill.service.*;
import org.slf4j.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import org.springframework.util.*;

import javax.annotation.*;
import java.util.*;

/**
 * Created by pc on 2017/3/5.
 */
@Service
public class SeckillServiceImpl implements SeckillService {

    private Logger logger=LoggerFactory.getLogger(this.getClass());
    @Resource
    private SeckillDao seckillDao;
    @Resource
    private SuccessKilledDao successKilledDao;
    @Resource
    private RedisDao redisDao;

    //md5盐值，用于混淆md5
    private final String sault="abjkdkddk*234*fdjfkslsdfj&-=fdfdk";

    public List<Seckill> getSeckillList() {

        return seckillDao.queryAll(0,4);
    }

    public Seckill getById(long seckillId) {

        return seckillDao.queryById(seckillId);
    }

    public Exposer exposeSeckillUrl(long seckillId) {
        //优化点：缓存优化
        //1.访问redis
        Seckill seckill = redisDao.getSeckill(seckillId);
        if(seckill==null){
            //2.访问数据库
            seckill=seckillDao.queryById(seckillId);
            if(seckill==null){
                return new Exposer(seckillId, false);
            }else{
                //3.放入redis
                redisDao.putSeckill(seckill);
            }


        }

        Date startTime=seckill.getStartTime();
        Date endTime=seckill.getEndTime();
        Date nowTime=new Date();

        if (nowTime.getTime() < startTime.getTime() || nowTime.getTime() > endTime.getTime()) {
            return new Exposer(seckillId, false, nowTime.getTime(), startTime.getTime(), endTime.getTime());
        }
        //转换特定字符串的过程，不可逆
        String md5=getMD5(seckillId);//TODO
        return new Exposer(true,md5,seckillId);
    }

    private String getMD5(long seckillId){
        String base=seckillId+"/"+sault;
        String md5= DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

    @Transactional
    /**
     * 使用注解控制事务方法的优点
     * 1.开发团队达成一致约定，明确标注事务方法的编程风格
     * 2.保证事务方法执行时间尽可能短（降低锁定时间），不要穿插其他网络操作RPC/HTTP或者剥离到方法外部
     * 3.不是所有的方法都需要事务，如只有一条修改操作，只读操作不需要事务控制
     */
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws SeckillException, RepeatKillException, SeckillCloseException {
        if(md5==null || !md5.equals(getMD5(seckillId))){
            return new SeckillExecution(seckillId,SeckillStateEnum.DATA_REWRITE);
        }
        //使用存储过程执行秒杀
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("seckillId", seckillId);
        map.put("userPhone", userPhone);
        map.put("killTime", new Date());
        map.put("result", null);
        try {
            seckillDao.killByProcedure(map);
            //获取result
            int result=MapUtils.getInteger(map,"result",-2);
            if(result==1){
                SuccessKilled successKilled = successKilledDao.
                        queryByIdWithSeckill(seckillId,userPhone);
                return new SeckillExecution(seckillId, SeckillStateEnum.SUCCESS, successKilled);
            }else{
                return new SeckillExecution(seckillId, SeckillStateEnum.stateOf(result));
            }
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return new SeckillExecution(seckillId, SeckillStateEnum.INNER_ERROR);
        }

//        //执行秒杀逻辑：减库存+记录购买行为
        //
//        //减库存
//        try{
//            //记录购买行为
//            int insertCount=successKilledDao.insertSuccessKilled(seckillId,userPhone);
//            if(insertCount<=0){
//                //重复秒杀
//                throw new RepeatKillException("seckill repeated");
//            }else{
//                //减库存，热点商品竞争，*先插入购买明细，再减库存，可以缩短行级锁时间
//                int updateCount=seckillDao.reduceNumber(seckillId,new Date());
//                if(updateCount<=0){
//                    //没有更新到记录,秒杀结束
//                    throw new SeckillCloseException("seckill is closed");
//                }else{
//                    SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
//                    return new SeckillExecution(seckillId, SeckillStateEnum.SUCCESS,successKilled);
//                }
//
//            }
//        }catch (SeckillCloseException e){
//            throw e;
//        }catch (RepeatKillException e){
//            throw e;
//        }catch(Exception e){
//            logger.error(e.getMessage(),e);
//            //所有编译期异常，转换为运行期异常
//            throw new SeckillException("seckill inner error:"+e.getMessage());
//        }
    }


}
