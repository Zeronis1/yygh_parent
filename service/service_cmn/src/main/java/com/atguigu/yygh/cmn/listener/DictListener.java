package com.atguigu.yygh.cmn.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.atguigu.yygh.cmn.service.DictService;
import com.atguigu.yygh.cmn.service.impl.DictServiceImpl;
import com.atguigu.yygh.model.cmn.Dict;
import com.atguigu.yygh.vo.cmn.DictEeVo;
import org.springframework.beans.BeanUtils;

/**
 * @Date 2023/6/28
 * @Author SLF
 * @Description:
 * 有个很重要的点 UserListener 不能被spring管理，
 * 要每次读取excel都要new,然后里面用到spring可以构造方法传进去
 */
public class DictListener implements ReadListener<DictEeVo> {

    private DictService dictService;

    public DictListener() {

    }

    public DictListener(DictServiceImpl dictService) {
        this.dictService = dictService;
    }

    @Override
    public void invoke(DictEeVo dictEeVo, AnalysisContext analysisContext) {
        //每读取一行(第一行以下的内容)，执行一次
        //dictEeVo转换为dict表对应的实体类Dict
        Dict dictDB = new Dict();
        BeanUtils.copyProperties(dictEeVo,dictDB);

        Long id = dictEeVo.getId();
        //查询数据是否存在
        Dict dict = dictService.getById(id);
        if (dict == null) {
            //不存在>执行添加
            dictService.save(dictDB);
        } else {
            //存在>执行修改
            dictService.updateById(dictDB);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        //读完后执行一次
    }
}
