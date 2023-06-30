package com.atguigu.yygh.hosp.service.impl;

import com.atguigu.yygh.hosp.repository.ScheduleRepository;
import com.atguigu.yygh.hosp.service.ScheduleService;
import com.atguigu.yygh.model.hosp.Schedule;
import com.atguigu.yygh.vo.hosp.ScheduleQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Date 2023/6/30
 * @Author SLF
 * @Description:
 */
@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    /**
     * 上传排班接口
     * @param schedule
     */
    @Override
    public void saveSchedule(Schedule schedule) {
        //根据医院编号 和 排班编号查询
        Schedule scheduleExit = scheduleRepository
                .queryScheduleByHoscodeAndHosScheduleId(schedule.getHoscode(),schedule.getHosScheduleId());

        //判断
        schedule = scheduleExit != null ? scheduleExit : schedule;

        schedule.setCreateTime(schedule.getCreateTime() != null ? schedule.getCreateTime() : new Date());
        schedule.setUpdateTime(new Date());
        schedule.setIsDeleted(0);
        schedule.setStatus(1);

        scheduleRepository.save(schedule);
    }

    /**
     * 查询排班接口
     * @param queryVo
     * @param page
     * @param limit
     * @return
     */
    @Override
    public Page<Schedule> queryPageSchedule(ScheduleQueryVo queryVo, Integer page, Integer limit) {
        //创建查询条件，设置当前页和每页记录数
        Schedule schedule = new Schedule();
        schedule.setHoscode(queryVo.getHoscode());
        Example<Schedule> example = Example.of(schedule);

        //分页参数，页面0为第一页
        PageRequest pageRequest = PageRequest.of(page - 1, limit);
        return scheduleRepository.findAll(example,pageRequest);
    }

    /**
     * 删除排班接口
     * @param hoscode
     * @param hosScheduleId
     */
    @Override
    public void removeSchedule(String hoscode, String hosScheduleId) {
        Schedule schedule = scheduleRepository
                .queryScheduleByHoscodeAndHosScheduleId(hoscode, hosScheduleId);
        if (schedule != null) {
            scheduleRepository.deleteById(schedule.getId());
        }
    }
}
