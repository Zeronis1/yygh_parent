package com.atguigu.yygh.cmn.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.yygh.cmn.listener.DictListener;
import com.atguigu.yygh.cmn.mapper.DictMapper;
import com.atguigu.yygh.cmn.service.DictService;
import com.atguigu.yygh.model.cmn.Dict;
import com.atguigu.yygh.vo.cmn.DictEeVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Date 2023/6/27
 * @Author SLF
 * @Description:
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {

    /**
     * 根据id查询子数据列表
     * @param id
     * @return
     */
    @Cacheable(value = "dict",key = "'selectIndexList'+#id")
    @Override
    public List<Dict> findChildData(Long id) {
        //根据数据id查询子数据列表
        List<Dict> dictList = lambdaQuery().eq(Dict::getParentId, id).list();
        dictList.forEach(dict -> {
            dict.setHasChildren(this.isChildren(dict.getId()));
        });
        return dictList;
    }

    /**
     * 导出Excel
     * @param resp
     */
    @Override
    public void exportExcel(HttpServletResponse resp) throws IOException {
        //查询所有数据
        List<Dict> list = this.list();

        //转换数据为EasyExcel的实体类
        List<DictEeVo> dictEeVoList = list.stream()
                .map(dict -> {
                    DictEeVo dictEeVo = new DictEeVo();
                    BeanUtils.copyProperties(dict, dictEeVo);
                    return dictEeVo;
                })
                .collect(Collectors.toList());

        resp.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        resp.setCharacterEncoding("utf-8");

        String fileName = URLEncoder.encode("数据字典","UTF-8");

        resp.setHeader("Content-disposition","attachment;filename*=utf-8''" + fileName + ".xlsx");
        EasyExcel.write(resp.getOutputStream(), DictEeVo.class).sheet("模板").doWrite(dictEeVoList);
    }

    /**
     * 导入Excel
     * @param file
     */
    @Override
    public void importExcel(MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), DictEeVo.class, new DictListener(this))
                .sheet().doRead();
    }

    //判断id下面是否有子节点
    private boolean isChildren(Long id) {
        return lambdaQuery().eq(Dict::getParentId,id).count() > 0;
    }

}
