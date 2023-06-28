package com.atguigu.yygh.cmn.controller;

import com.atguigu.yygh.cmn.service.DictService;
import com.atguigu.yygh.common.utils.R;
import com.atguigu.yygh.model.cmn.Dict;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @Date 2023/6/27
 * @Author SLF
 * @Description:
 */
@Api(tags = "数据字典接口")
@RestController
@RequestMapping("/admin/cmn/dict")
@CrossOrigin
public class DictController {

    @Autowired
    private DictService dictService;

    @ApiOperation("根据id查询子数据列表")
    @GetMapping("/findChild/{id}")
    public R findChildData(@PathVariable Long id) {
        List<Dict> list = dictService.findChildData(id);
        return R.ok().data("list",list);
    }

    @ApiOperation("导出Excel")
    @GetMapping("/exportExcel")
    public void exportExcel(HttpServletResponse resp) throws IOException {
        dictService.exportExcel(resp);
    }

    @ApiOperation("导入Excel")
    @PostMapping("importExcel")
    public R importExcel(MultipartFile file) throws IOException {
        dictService.importExcel(file);
        return R.ok();
    }

}
