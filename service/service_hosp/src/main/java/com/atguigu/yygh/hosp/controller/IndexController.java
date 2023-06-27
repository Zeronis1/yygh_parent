package com.atguigu.yygh.hosp.controller;

import com.atguigu.yygh.common.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2023/6/26
 * @Author SLF
 * @Description:
 */
@Api(tags = "后台登录管理")
@RestController
@RequestMapping("/admin/hosp/index")
@CrossOrigin
public class IndexController {

    @ApiOperation("登录")
    @PostMapping("/login")
    public R login() {
        //直接登录成功，不走数据库
        return R.ok().data("token","admin-token");
    }

    @ApiOperation("获取登录信息")
    @GetMapping("/info")
    public R getInfo() {
        return R.ok().data("roles",new String[]{"admin"})
                .data("introduction","超级管理员登录后台")
                .data("avatar","https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fc-ssl.duitang.com%2Fuploads%2Fitem%2F201908%2F09%2F20190809214856_CeEme.thumb.400_0.gif&refer=http%3A%2F%2Fc-ssl.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1690355331&t=090d863bf61dd817e5469f362ae3fe9d")
                .data("name","超级管理员");
    }


    @ApiOperation("退出登录")
    @PostMapping("/logout")
    public R logout() {
        return R.ok();
    }
}
