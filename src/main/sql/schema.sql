--  数据库初始化脚本
-- 创建数据库
DROP Database `seckill`;
CREATE DATABASE `seckill`;
-- 使用数据库
USE `seckill`;
-- 创建表

CREATE TABLE seckill(
    `seckill_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '商品库存id',
    `name` VARCHAR(120) NOT NULL COMMENT '商品名称',
    `number` INT NOT NULL COMMENT '库存数量',
    `start_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '开始时间',/*第一个TIMESTAMP字段会自动更新，要加上default*/
    `end_time` TIMESTAMP NOT NULL COMMENT '结束时间',
    `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (seckill_id),
    KEY idx_start_time(start_time),
    KEY idx_end_time(end_time)
)ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT ='秒杀明细表';

-- 初始化数据
insert into seckill(name, number, start_time, end_time)
    VALUES
      ('1000元秒杀iphone6',100,'2017-03-05 00:00:00','2017-03-06 00:00:00'),
      ('500元秒杀ipad2',200,'2017-03-05 00:00:00','2017-03-06 00:00:00'),
      ('300元秒杀小米3',300,'2017-03-05 00:00:00','2017-03-06 00:00:00'),
      ('200元秒杀红米note',400,'2017-03-05 00:00:00','2017-03-06 00:00:00');

-- 秒杀成功明细表
-- 用户登录认证相关的信息
CREATE TABLE `success_killed`(
  `seckill_id` BIGINT NOT NULL COMMENT '秒杀商品id',
  `user_phone` BIGINT NOT NULL COMMENT '用户手机号',
  `state` TINYINT NOT NULL DEFAULT 0 COMMENT '状态标识：-1：无效 0：成功 1：已付款',
  `create_time` TIMESTAMP NOT NULL COMMENT '创建时间',
  PRIMARY KEY (seckill_id,user_phone),  -- 联合主键
  KEY idx_create_time(create_time)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='秒杀成功明细表';
