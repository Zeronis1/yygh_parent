package com.atguigu.yygh.hosp.service;

import com.atguigu.yygh.model.hosp.Schedule;
import com.atguigu.yygh.vo.hosp.ScheduleQueryVo;
import org.springframework.data.domain.Page;

/**
 * @Date 2023/6/30
 * @Author SLF
 * @Description:
 */
public interface ScheduleService {

    /**
     * 上传排班接口
     * @param schedule
     */
    void saveSchedule(Schedule schedule);

    /**
     * 查询排班接口
     * @param queryVo
     * @param page
     * @param limit
     * @return
     */
    Page<Schedule> queryPageSchedule(ScheduleQueryVo queryVo, Integer page, Integer limit);

    /**
     * 删除排班接口
     * @param hoscode
     * @param hosScheduleId
     */
    void removeSchedule(String hoscode, String hosScheduleId);
}
