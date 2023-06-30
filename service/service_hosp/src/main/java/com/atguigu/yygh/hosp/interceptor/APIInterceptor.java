package com.atguigu.yygh.hosp.interceptor;

import com.atguigu.yygh.common.exception.YyghException;
import com.atguigu.yygh.common.helper.HttpRequestHelper;
import com.atguigu.yygh.common.result.ResultCodeEnum;
import com.atguigu.yygh.hosp.service.HospitalSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Date 2023/6/30
 * @Author SLF
 * @Description:
 */
@Component
public class APIInterceptor implements HandlerInterceptor {

    @Autowired
    private HospitalSetService hospitalSetService;

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
        //验证时间戳
        String parameter = req.getParameter("timestamp");
        long start = Long.parseLong(parameter);
        long end = System.currentTimeMillis();
        if (end - start > 1000) {
            throw new YyghException(ResultCodeEnum.REQUEST_TIMEOUT);
        }
        //验证签名
        String signHosp = req.getParameter("sign");
        Map<String, String[]> requestMap = req.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);
        String hoscode = req.getParameter("hoscode");
        String signKey = hospitalSetService.getSignKey(hoscode);
        String signKeyMD5 = HttpRequestHelper.getSign(paramMap,signKey);
        if (!signHosp.equals(signKeyMD5)) {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }
        //放行-交给Controller处理请求
        return true;
    }
}
