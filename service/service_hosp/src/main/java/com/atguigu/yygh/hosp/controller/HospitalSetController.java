package com.atguigu.yygh.hosp.controller;

import com.atguigu.yygh.common.exception.YyghException;
import com.atguigu.yygh.common.util.MD5;
import com.atguigu.yygh.common.utils.R;
import com.atguigu.yygh.hosp.service.HospitalSetService;
import com.atguigu.yygh.model.hosp.HospitalSet;
import com.atguigu.yygh.vo.hosp.HospitalQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * @Date 2023/6/25
 * @Author SLF
 * @Description:
 */
@Api(tags = "医院设置接口")
@RestController
@RequestMapping("/admin/hosp/hospitalSet")
@CrossOrigin
public class HospitalSetController {

    @Autowired
    private HospitalSetService hospitalSetService;

    /**
     * 查询所有医院设置
     * @return list
     */
    @ApiOperation("查询所有设置")
    @GetMapping("/findAll")
    public R findAll() {
        List<HospitalSet> list = hospitalSetService.list();
        return R.ok().data("list",list);
    }

    /**
     * 删除指定设置
     * @param id
     * @return
     */
    @ApiOperation("根据id删除设置")
    @DeleteMapping("/remove/{id}")
    public R remove(@ApiParam("医院设置ID") @PathVariable Long id) {
       hospitalSetService.removeById(id);
       return R.ok();
    }

    @ApiOperation("分页设置列表")
    @GetMapping("/{pageNum}/{pageSize}")
    public R queryPageList(
            @ApiParam(name = "pageNum",value = "当前页码",required = true)
            @PathVariable Long pageNum,

            @ApiParam(name = "pageSize",value = "每页条数",required = true)
            @PathVariable Long pageSize,

            @ApiParam(name = "queryVo",value = "查询条件")
            HospitalQueryVo queryVo) {
        Page<HospitalSet> pageParam = new Page<>(pageNum, pageSize);

        QueryWrapper<HospitalSet> queryWrapper = Optional.ofNullable(queryVo)
                .map(vo -> {
                    QueryWrapper<HospitalSet> wrapper = Wrappers.query();
                    if (StringUtils.hasText(vo.getHosname())) {
                        wrapper.lambda().like(HospitalSet::getHosname,vo.getHosname());
                    }
                    if (StringUtils.hasText(vo.getHoscode())) {
                        wrapper.lambda().eq(HospitalSet::getHoscode,vo.getHoscode());
                    }
                    return wrapper;
                })
                .orElse(Wrappers.emptyWrapper());

        Page<HospitalSet> result = hospitalSetService.page(pageParam, queryWrapper);
        List<HospitalSet> records = result.getRecords();
        return R.ok().data("result",result).data("rows",records);
    }

    @ApiOperation("添加设置")
    @PostMapping("/add")
    public R add(@ApiParam("医院设置对象") @RequestBody HospitalSet hospitalSet) {
        hospitalSet.setSignKey(MD5.encrypt(System.currentTimeMillis() + "" +
                new Random().nextInt(1000)));
        hospitalSet.setStatus(1);
        return hospitalSetService.save(hospitalSet) ? R.ok() : R.error();
    }

    @ApiOperation("根据ID查询设置")
    @GetMapping("/{id}")
    public R querySet(@ApiParam("医院设置ID") @PathVariable Long id) {
        //int a = 10 / 0;
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        if (id < 0) {
            throw new YyghException(20001,"id不能为负数");
        }
        return R.ok().data("item",hospitalSet);
    }

    @ApiOperation("根据ID修改设置")
    @PutMapping("/update")
    public R update(@ApiParam("医院设置ID") @RequestBody HospitalSet hospitalSet) {
        return hospitalSetService.updateById(hospitalSet) ? R.ok() : R.error();
    }

    @ApiOperation("批量删除设置")
    @DeleteMapping("/batchRemove")
    public R batchRemove(@ApiParam("医院设置ID列表") @RequestBody List<Long> idList) {
        return hospitalSetService.removeByIds(idList) ? R.ok() : R.error();
    }

    @ApiOperation("锁定与解锁")
    @PutMapping("/updateStatus/{id}/{status}")
    public R lock(@ApiParam("医院设置ID") @PathVariable Long id,
                  @ApiParam("状态") @PathVariable Integer status) {
        //执行修改动作
        boolean update = hospitalSetService.lambdaUpdate()
                .eq(HospitalSet::getId, id)
                .set(HospitalSet::getStatus, status)
                .update();
        return update ? R.ok() : R.error();
    }
}
