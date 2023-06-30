package com.atguigu.yygh.hosp.repository;

import com.atguigu.yygh.model.hosp.Hospital;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @Date 2023/6/29
 * @Author SLF
 * @Description:
 */
@Repository
public interface HospitalRepository extends MongoRepository<Hospital,String> {
    Hospital queryHospByHoscode(String hoscode);
}
