package org.seckill.dao;

import org.apache.ibatis.annotations.*;
import org.seckill.entity.*;

import java.util.*;

/**
 * Created by pc on 2017/3/5.
 */
public interface SeckillDao {
    /**减库存
     * @param seckillId
     * @param killTime
     * @return 影响行数
     */
    int reduceNumber(@Param("seckillId") long seckillId,@Param("killTime") Date killTime);

    /**根据id查询秒杀对象
     * @param seckillId
     * @return
     */
    Seckill queryById(long seckillId);

    /**根据偏移量查询秒杀商品列表
     * @param offset
     * @param limit
     * @return
     */
    List<Seckill> queryAll(@Param("offset") int offset,@Param("limit") int limit);

    /**
     * 使用存储过程执行秒杀
     * @param paramMap
     */
    void killByProcedure(Map<String,Object> paramMap);

}
