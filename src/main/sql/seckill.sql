-- 执行秒杀存储过程
DELIMITER $$
-- 定义存储过程
-- 参数：in输入参数 out输出参数
-- row_count()返回上一条修改语句sql（update,insert,delete)影响的行数
-- row_count:0未修改行数，>0:表示影响的行数 <0:sql错误/未执行修改sql
-- 存储过程内部声明的变量不能是@开头
CREATE PROCEDURE `seckill`.`execute_seckill`(
  in v_seckill_id bigint,in v_seckill_phone BIGINT,in v_seckill_time TIMESTAMP,
  out r_result int)
  BEGIN
    DECLARE insert_count int DEFAULT 0;
    START TRANSACTION;
    INSERT IGNORE INTO `success_killed`(seckill_id,user_phone,create_time)
    values(v_seckill_id,v_seckill_phone,v_seckill_time);
    SELECT row_count() INTO insert_count;
    IF(insert_count = 0) THEN
      ROLLBACK;
      SET r_result=-1;-- 重复秒杀
    ELSEIF(insert_count<0) THEN
      ROLLBACK;
      SET r_result=-2;
    ELSE
      UPDATE seckill SET number=number-1
      WHERE seckill_id=v_seckill_id
            AND seckill.start_time < v_seckill_time
            AND seckill.end_time >v_seckill_time
            AND seckill.number>0;
      SELECT row_count() into insert_count;
      IF (insert_count=0) THEN
        ROLLBACK;
        SET r_result=0;
      ELSEIF (insert_count<0) THEN
        ROLLBACK;
        SET r_result=-2;
      ELSE
        COMMIT;
        SET r_result=1;
      END IF ;
    END IF;
  END;
$$
-- 存储过程定义结束
DELIMITER ;
SET @r_result=-3;
-- 执行存储过程
CALL seckill.execute_seckill(1003, 18826077111, now(), @r_result);
-- 获取结果
SELECT @r_result;


-- 存储过程
-- 存储过程优化：事务行级锁持有的时间
-- 不要过度依赖存储过程
-- 简单的逻辑可以应用存储过程
-- QPS:一个秒杀单6000/QPS