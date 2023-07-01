package com.atguigu.yygh.hosp.service;

import com.atguigu.yygh.model.hosp.HospitalSet;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Date 2023/6/25
 * @Author SLF
 * @Description:
 */
public interface HospitalSetService extends IService<HospitalSet> {
    String getSignKey(String hoscode);
}
