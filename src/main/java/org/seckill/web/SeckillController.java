package org.seckill.web;

import org.seckill.dto.*;
import org.seckill.entity.*;
import org.seckill.enums.*;
import org.seckill.exception.*;
import org.seckill.service.*;
import org.slf4j.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.*;
import java.util.*;

/**
 * Created by pc on 2017/3/7.
 */
@Controller
@RequestMapping("/seckill")
public class SeckillController {
    private Logger logger=LoggerFactory.getLogger(this.getClass());
    @Resource
    private SeckillService seckillService;

    @RequestMapping(value = "/list",method=RequestMethod.GET)
    public String list(Model model){
        List<Seckill> list=seckillService.getSeckillList();
        model.addAttribute("list",list);
        return "list";
    }

    @RequestMapping(value = "/{seckillId}/detail",method = RequestMethod.GET)
    public String detail(@PathVariable("seckillId")Long seckillId,Model model){
        if(seckillId==null){
            return "redirect:/seckill/list";
        }
        Seckill seckill=seckillService.getById(seckillId);
        if(seckill==null){
            return "forward:/seckill/list";
        }
        model.addAttribute("seckill",seckill);
        return "detail";
    }
//ajax json
    @RequestMapping(value = "{seckillId}/exposer",
            method = RequestMethod.GET,
            produces={"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<Exposer> exposer(@PathVariable("seckillId")Long seckillId){
        SeckillResult<Exposer> result=null;
        try{
            Exposer exposer=seckillService.exposeSeckillUrl(seckillId);
            result=new SeckillResult<Exposer>(true,exposer);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            result =new SeckillResult<Exposer>(false,e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "{seckillId}/{md5}/execution")
    @ResponseBody
    public SeckillResult<SeckillExecution> execute(@PathVariable("seckillId") Long seckillId,
                                    @PathVariable("md5") String md5,
                                    @CookieValue(value = "userPhone", required = false) Long userPhone) {
        if(userPhone==null){
            return new SeckillResult<SeckillExecution>(false, "未登录");
        }
        SeckillResult<SeckillExecution> result=null;
        try{
            SeckillExecution execution = seckillService.executeSeckill(seckillId, userPhone, md5);
            return new SeckillResult<SeckillExecution>(true,execution);
        }catch(SeckillCloseException e){
            SeckillExecution seckillExecution=new SeckillExecution(seckillId, SeckillStateEnum.END);
            return new SeckillResult<SeckillExecution>(true,seckillExecution);
        }catch(RepeatKillException e){
            SeckillExecution seckillExecution=new SeckillExecution(seckillId, SeckillStateEnum.REPEAT_KILL);
            return new SeckillResult<SeckillExecution>(true,seckillExecution);
        }
        catch (Exception e){
            logger.error(e.getMessage(),e);
            SeckillExecution seckillExecution=new SeckillExecution(seckillId, SeckillStateEnum.INNER_ERROR);
            result = new SeckillResult<SeckillExecution>(true,seckillExecution);
            return result;
        }
    }

    @RequestMapping(value = "/time/now",method = RequestMethod.GET)
    @ResponseBody
    public SeckillResult<Long> time(){
        Date date=new Date();
        return new SeckillResult<Long>(true,date.getTime());
    }
}
