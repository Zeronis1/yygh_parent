package com.atguigu.yygh.hosp.service;

import com.atguigu.yygh.model.hosp.Department;
import com.atguigu.yygh.vo.hosp.DepartmentQueryVo;
import org.springframework.data.domain.Page;

/**
 * @Date 2023/6/30
 * @Author SLF
 * @Description:
 */
public interface DepartmentService {

    /**
     * 上传科室
     * @param department
     */
    void saveDepartment(Department department);


    /**
     * 查询科室接口
     * @param queryVo
     * @param page
     * @param limit
     * @return
     */
    Page<Department> queryPageDepartment(DepartmentQueryVo queryVo, Integer page, Integer limit);

    /**
     * 删除科室接口
     * @param hoscode
     * @param depcode
     */
    void removeDepartment(String hoscode, String depcode);
}
