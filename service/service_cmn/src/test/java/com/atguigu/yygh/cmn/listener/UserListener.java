package com.atguigu.yygh.cmn.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.read.listener.ReadListener;
import com.atguigu.yygh.cmn.entity.User;

import java.util.Map;

/**
 * @Date 2023/6/28
 * @Author SLF
 * @Description:
 * 有个很重要的点 UserListener 不能被spring管理，
 * 要每次读取excel都要new,然后里面用到spring可以构造方法传进去
 */
public class UserListener implements ReadListener<User> {

    @Override
    public void invokeHead(Map<Integer, ReadCellData<?>> headMap, AnalysisContext context) {
        //读取第一行时执行一次 (只会执行一次)
        String id = headMap.get(0).getStringValue();
        String name = headMap.get(1).getStringValue();
        String age = headMap.get(2).getStringValue();

        System.out.println("invokeHead>>>>>>>>>>>>>>");
        System.out.println("id = " + id);
        System.out.println("name = " + name);
        System.out.println("age = " + age);
        System.out.println("invokeHead>>>>>>>>>>>>>>");
    }

    @Override
    public void invoke(User user, AnalysisContext analysisContext) {
        //读取到正文内容时(第一行以下的内容)，执行一次   (正文内容有几行则执行几次)
        System.out.println("user = " + user);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        //读完以后，执行一次 （只会执行一次）
        System.out.println("读完了！！！");
    }
}
