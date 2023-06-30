package com.atguigu.hospital.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.hospital.mapper.OrderInfoMapper;
import com.atguigu.hospital.mapper.ScheduleMapper;
import com.atguigu.hospital.model.OrderInfo;
import com.atguigu.hospital.model.Patient;
import com.atguigu.hospital.model.Schedule;
import com.atguigu.hospital.service.HospitalService;
import com.atguigu.hospital.util.ResultCodeEnum;
import com.atguigu.hospital.util.YyghException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class HospitalServiceImpl implements HospitalService {

	@Autowired
	private ScheduleMapper scheduleMapper;

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map<String, Object> submitOrder(Map<String, Object> paramMap) {
        log.info(JSONObject.toJSONString(paramMap));
        String hoscode = (String)paramMap.get("hoscode");
        String depcode = (String)paramMap.get("depcode");
        String hosScheduleId = (String)paramMap.get("hosScheduleId");
        String reserveDate = (String)paramMap.get("reserveDate");
        String reserveTime = (String)paramMap.get("reserveTime");
        String amount = (String)paramMap.get("amount");

        Schedule schedule = this.getSchedule(hosScheduleId);
        if(null == schedule) {
            throw new YyghException(ResultCodeEnum.DATA_ERROR);
        }

        if(!schedule.getHoscode().equals(hoscode)
                || !schedule.getDepcode().equals(depcode)
                || !schedule.getAmount().toString().equals(amount)) {
            throw new YyghException(ResultCodeEnum.DATA_ERROR);
        }

        //就诊人信息
        Patient patient = JSONObject.parseObject(JSONObject.toJSONString(paramMap), Patient.class);
        log.info(JSONObject.toJSONString(patient));
        //处理就诊人业务
        Long patientId = this.savePatient(patient);

        Map<String, Object> resultMap = new HashMap<>();
        int availableNumber = schedule.getAvailableNumber().intValue() - 1;
        if(availableNumber >= 0) {
            schedule.setAvailableNumber(availableNumber);
            scheduleMapper.updateById(schedule);

            //记录预约记录
            OrderInfo orderInfo = new OrderInfo();
            orderInfo.setPatientId(patientId);
            orderInfo.setScheduleId(Long.parseLong(hosScheduleId));
            int number = schedule.getReservedNumber().intValue() - schedule.getAvailableNumber().intValue();
            orderInfo.setNumber(number);
            orderInfo.setAmount(new BigDecimal(amount));
            String fetchTime = reserveDate+("0".equals(reserveTime) ? " 09:30前" : " 14:00前");
            orderInfo.setFetchTime(fetchTime);
            String fetchAddress = "一楼9号窗口";
            orderInfo.setFetchAddress(fetchAddress);
            //默认 未支付
            orderInfo.setOrderStatus(0);
            orderInfoMapper.insert(orderInfo);

            resultMap.put("resultCode","0000");
            resultMap.put("resultMsg","预约成功");
            //预约记录唯一标识（医院预约记录主键）
            resultMap.put("hosRecordId", orderInfo.getId());
            //预约号序
            resultMap.put("number", number);
            //取号时间
            resultMap.put("fetchTime", fetchTime);
            //取号地址
            resultMap.put("fetchAddress", fetchAddress);
            //排班可预约数
            resultMap.put("reservedNumber", schedule.getReservedNumber());
            //排班剩余预约数
            resultMap.put("availableNumber", schedule.getAvailableNumber());
        } else {
            throw new YyghException(ResultCodeEnum.ORDER_ERROR);
        }
        return resultMap;
    }

    @Override
    public void updatePayStatus(Map<String, Object> paramMap) {
        String hoscode = (String)paramMap.get("hoscode");
        String hosRecordId = (String)paramMap.get("hosRecordId");

        OrderInfo orderInfo = orderInfoMapper.selectById(hosRecordId);
        if(null == orderInfo) {
            throw new YyghException(ResultCodeEnum.DATA_ERROR);
        }
        //已支付
        orderInfo.setOrderStatus(1);
        orderInfo.setPayTime(new Date());
        orderInfoMapper.updateById(orderInfo);
    }

    @Override
    public void updateCancelStatus(Map<String, Object> paramMap) {
        String hoscode = (String)paramMap.get("hoscode");
        String hosRecordId = (String)paramMap.get("hosRecordId");

        OrderInfo orderInfo = orderInfoMapper.selectById(hosRecordId);
        if(null == orderInfo) {
            throw new YyghException(ResultCodeEnum.DATA_ERROR);
        }
        //更新订单状态-已取消
        orderInfo.setOrderStatus(-1);
        orderInfo.setQuitTime(new Date());
        orderInfoMapper.updateById(orderInfo);
        //更新排班数据-剩余预约数量+1
        Schedule schedule = scheduleMapper.selectById(orderInfo.getScheduleId());
        schedule.setAvailableNumber(schedule.getAvailableNumber()+1);
        scheduleMapper.updateById(schedule);
    }

    private Schedule getSchedule(String frontSchId) {
        return scheduleMapper.selectById(frontSchId);
    }

    /**
     * 医院处理就诊人信息
     * @param patient
     */
    private Long savePatient(Patient patient) {
        // 业务：略
        return 1L;
    }


}
