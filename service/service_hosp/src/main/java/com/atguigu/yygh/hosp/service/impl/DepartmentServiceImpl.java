package com.atguigu.yygh.hosp.service.impl;

import com.atguigu.yygh.hosp.repository.DepartmentRepository;
import com.atguigu.yygh.hosp.service.DepartmentService;
import com.atguigu.yygh.model.hosp.Department;
import com.atguigu.yygh.vo.hosp.DepartmentQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

/**
 * @Date 2023/6/30
 * @Author SLF
 * @Description:
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    /**
     * 上传科室
     * @param department
     */
    @Override
    public void saveDepartment(Department department) {
        //根据医院编号和科室编号查询
        Department departmentExist = Optional.ofNullable(departmentRepository
                .queryByHoscodeAndDepcode(department.getHoscode(),department.getDepcode()))
                        .orElse(new Department());

        //判断并保存
        departmentExist.setUpdateTime(new Date());
        departmentExist.setIsDeleted(0);
        departmentExist.setCreateTime(departmentExist.getCreateTime() != null ?
                departmentExist.getCreateTime() : new Date());
        departmentRepository.save(department);
    }

    /**
     * 查询科室接口
     * @param queryVo
     * @param page
     * @param limit
     * @return
     */
    @Override
    public Page<Department> queryPageDepartment(DepartmentQueryVo queryVo, Integer page, Integer limit) {
        //创建查询条件,设置当前页和每页记录数
        Department department = new Department();
        department.setHoscode(queryVo.getHoscode());
        Example<Department> example = Example.of(department);

        //分页参数 页面0为第一页
        PageRequest pageRequest = PageRequest.of(page - 1, limit);
        return departmentRepository.findAll(example,pageRequest);
    }

    /**
     * 删除科室接口
     * @param hoscode
     * @param depcode
     */
    @Override
    public void removeDepartment(String hoscode, String depcode) {
        //根据医院编号和科室编号查询
        Department department = departmentRepository.queryByHoscodeAndDepcode(hoscode, depcode);
        if (department != null) {
            //调用方法删除
            departmentRepository.deleteById(department.getId());
        }
    }

}
