package org.seckill.exception;


/**
 * 重复秒杀异常(运行期异常),Spring声明式事务只支持运行时异常
 * Created by pc on 2017/3/6.
 */
public class RepeatKillException extends SeckillException{
    public RepeatKillException(String message){
        super(message);
    }

    public RepeatKillException(String message,Throwable cause){
        super(message,cause);
    }

}
