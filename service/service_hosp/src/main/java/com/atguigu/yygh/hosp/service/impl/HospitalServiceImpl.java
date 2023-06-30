package com.atguigu.yygh.hosp.service.impl;

import com.atguigu.yygh.hosp.repository.HospitalRepository;
import com.atguigu.yygh.hosp.service.HospitalService;
import com.atguigu.yygh.model.hosp.Hospital;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

/**
 * @Date 2023/6/29
 * @Author SLF
 * @Description:
 */
@Service
public class HospitalServiceImpl implements HospitalService {

    @Autowired
    private HospitalRepository hospitalRepository;

    /**
     * 上传医院接口
     * @param hospital
     */
    @Override
    public void saveHosp(Hospital hospital) {

        //处理logoData图片数据的问题：经过HTTP请求传递后 "+"转为了" "
        //解决：将" "转回"+"
        hospital.setLogoData(hospital.getLogoData().replaceAll(" ","+"));

        //判断数据在mongoDB是否存在
        Hospital targetHospital = hospitalRepository.queryHospByHoscode(hospital.getHoscode());

        hospital.setStatus(targetHospital != null ? targetHospital.getStatus() : 0);
        hospital.setCreateTime(targetHospital != null ? targetHospital.getCreateTime() : Date.from(Instant.now()));
        hospital.setUpdateTime(Date.from(Instant.now()));
        hospital.setIsDeleted(0);
        hospital.setId(targetHospital != null ? targetHospital.getId() : null);

        //保存数据
        hospitalRepository.save(hospital);
    }

    /**
     * 查询医院接口
     * @param hoscode
     * @return
     */
    @Override
    public Hospital queryHospital(String hoscode) {
        return hospitalRepository.queryHospByHoscode(hoscode);
    }
}
