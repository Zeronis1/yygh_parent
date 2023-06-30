package com.atguigu.yygh.hosp.service;

import com.atguigu.yygh.model.hosp.Hospital;


/**
 * @Date 2023/6/29
 * @Author SLF
 * @Description:
 */
public interface HospitalService {

    /**
     * 上传医院接口
     * @param hospital
     */
    void saveHosp(Hospital hospital);

    /**
     * 查询医院接口
     * @param hoscode
     * @return
     */
    Hospital queryHospital(String hoscode);
}
