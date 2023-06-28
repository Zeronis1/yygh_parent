package com.atguigu.yygh.cmn.service;

import com.atguigu.yygh.model.cmn.Dict;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @Date 2023/6/27
 * @Author SLF
 * @Description:
 */
public interface DictService extends IService<Dict> {

    /**
     * 根据id查询子数据列表
     * @param id
     * @return
     */
    List<Dict> findChildData(Long id);

    /**
     * 导出Excel
     * @param resp
     */
    void exportExcel(HttpServletResponse resp) throws IOException;

    /**
     * 导入Excel
     * @param file
     */
    void importExcel(MultipartFile file) throws IOException;
}
