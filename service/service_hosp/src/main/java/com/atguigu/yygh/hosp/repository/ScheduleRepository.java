package com.atguigu.yygh.hosp.repository;

import com.atguigu.yygh.model.hosp.Schedule;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @Date 2023/6/30
 * @Author SLF
 * @Description:
 */
@Repository
public interface ScheduleRepository extends MongoRepository<Schedule,String> {

    Schedule queryScheduleByHoscodeAndHosScheduleId(String hoscode, String hosScheduleId);
}