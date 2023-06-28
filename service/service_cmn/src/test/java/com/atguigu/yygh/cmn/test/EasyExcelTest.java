package com.atguigu.yygh.cmn.test;

import com.alibaba.excel.EasyExcel;
import com.atguigu.yygh.cmn.entity.User;
import com.atguigu.yygh.cmn.listener.UserListener;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2023/6/28
 * @Author SLF
 * @Description:
 */
public class EasyExcelTest {

    /**
     * 写Excel的操作
     * 将数据输出到一个excel文件中
     */
    @Test
    public void test01() {
        List<User> list = new ArrayList<>();
        list.add(new User(1L,"张三",23));
        list.add(new User(2L,"李四",24));
        list.add(new User(3L,"王五",25));

        //文件路径
        String fileName = "D:/Data/测试.xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则 传入excelType参数即可
        EasyExcel.write(fileName, User.class).sheet("模板").doWrite(list);
    }

    @Test
    public void test02() {
        //文件路径
        String fileName = "D:/Data/测试.xlsx";

        //读取文件内容
        EasyExcel.read(fileName, User.class, new UserListener()).sheet().doRead();
    }
}
