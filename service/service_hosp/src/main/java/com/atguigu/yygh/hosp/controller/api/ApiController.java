package com.atguigu.yygh.hosp.controller.api;

import com.atguigu.yygh.common.result.Result;
import com.atguigu.yygh.hosp.service.DepartmentService;
import com.atguigu.yygh.hosp.service.HospitalService;
import com.atguigu.yygh.hosp.service.ScheduleService;
import com.atguigu.yygh.model.hosp.Department;
import com.atguigu.yygh.model.hosp.Hospital;
import com.atguigu.yygh.model.hosp.Schedule;
import com.atguigu.yygh.vo.hosp.DepartmentQueryVo;
import com.atguigu.yygh.vo.hosp.ScheduleQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @Date 2023/6/29
 * @Author SLF
 * @Description:
 */
@Api(tags = "医院管理API接口")
@RestController
@RequestMapping("/api/hosp")
public class ApiController {

    @Autowired
    private HospitalService hospitalService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private ScheduleService scheduleService;

    @ApiOperation("上传医院接口")
    @PostMapping("/saveHospital")
    public Result saveHosp(Hospital hospital) {

        //调用service的方法
        hospitalService.saveHosp(hospital);
        return Result.ok();
    }

    @ApiOperation("查询医院接口")
    @PostMapping("/hospital/show")
    public Result queryHospital(String hoscode) {
        //调用service的方法
        Hospital hospital = hospitalService.queryHospital(hoscode);
        return Result.ok(hospital);
    }

    @ApiOperation("上传科室接口")
    @PostMapping("/saveDepartment")
    public Result saveDepartment(Department department) {
        departmentService.saveDepartment(department);
        return Result.ok();
    }

    @ApiOperation("查询科室接口")
    @PostMapping("/department/list")
    public Result departmentList(Integer page, Integer limit, DepartmentQueryVo queryVo) {
        Page<Department> departments = departmentService.queryPageDepartment(queryVo,page,limit);
        return Result.ok(departments);
    }

    @ApiOperation("删除科室接口")
    @PostMapping("/department/remove")
    public Result removeDepartment(DepartmentQueryVo queryVo) {
        departmentService.removeDepartment(queryVo.getHoscode(),queryVo.getDepcode());
        return Result.ok();
    }

    @ApiOperation("上传排班接口")
    @PostMapping("/saveSchedule")
    public Result saveSchedule(Schedule schedule) {
        scheduleService.saveSchedule(schedule);
        return Result.ok();
    }

    @ApiOperation("查询排班接口")
    @PostMapping("/schedule/list")
    public Result querySchedule(ScheduleQueryVo queryVo,Integer page, Integer limit) {
        Page<Schedule> schedules = scheduleService.queryPageSchedule(queryVo,page,limit);
        return Result.ok(schedules);
    }

    @ApiOperation("删除排班接口")
    @PostMapping("/schedule/remove")
    public Result removeSchedule(Schedule schedule) {
        scheduleService.removeSchedule(schedule.getHoscode(),schedule.getHosScheduleId());
        return Result.ok();
    }
}
