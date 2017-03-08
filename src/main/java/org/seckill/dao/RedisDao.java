package org.seckill.dao;

import com.dyuproject.protostuff.*;
import com.dyuproject.protostuff.runtime.*;
import org.seckill.entity.*;
import org.slf4j.*;
import redis.clients.jedis.*;

/**
 * Created by pc on 2017/3/8.
 */
public class RedisDao {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final JedisPool jedisPool;

    private RuntimeSchema<Seckill> schema =RuntimeSchema.createFrom(Seckill.class);
    public RedisDao(String ip,int port){
        jedisPool = new JedisPool(ip, port);
    }

    public Seckill getSeckill(long seckillId){
        try{
            Jedis jedis=jedisPool.getResource();
            try {
                String key="seckill:"+seckillId;
                //没有实现内部序列化
                //get->byte[]->反序列化->Object(Seckill)
                //采用自定义序列化
                //protostuff:pojo
                byte[] bytes=jedis.get(key.getBytes());
                if(bytes!=null){
                    Seckill seckill=schema.newMessage();
                    ProtostuffIOUtil.mergeFrom(bytes,seckill,schema);
                    //seckill被反序列化
                    return seckill;
                }
            }finally {
                jedis.close();
            }
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
        return null;
    }

    public String putSeckill(Seckill seckill){
        //set Object(Seckill)->序列化->byte[]
        try{
            Jedis jedis=jedisPool.getResource();
            try {
                String key="seckill:"+seckill.getSeckillId();
                byte[] bytes=ProtostuffIOUtil.toByteArray(seckill, schema,
                        LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
                //超时缓存
                int timeout=60*60;
                String result=jedis.setex(key.getBytes(),timeout, bytes);
                return result;
            }finally {
                jedis.close();
            }
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
        return null;
    }
}
