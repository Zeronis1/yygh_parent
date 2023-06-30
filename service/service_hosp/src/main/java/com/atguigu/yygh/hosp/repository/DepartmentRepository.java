package com.atguigu.yygh.hosp.repository;

import com.atguigu.yygh.model.hosp.Department;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @Date 2023/6/30
 * @Author SLF
 * @Description:
 */
@Repository
public interface DepartmentRepository extends MongoRepository<Department,String> {

    Department queryByHoscodeAndDepcode(String hoscode, String depcode);
}
