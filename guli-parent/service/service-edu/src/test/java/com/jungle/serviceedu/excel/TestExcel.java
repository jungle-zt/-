package com.jungle.serviceedu.excel;

import com.alibaba.excel.EasyExcel;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestExcel {
    @Test
    public  void readtest() {
        //实现excel写
        String filename = "E:\\write.xlsx";
        //调用EasyExcel的方法写文件
        EasyExcel.read (filename,DemoData.class,new ExcelListener ()).sheet ().doRead ();
    }
//    @Test
//    public  void test() {
//        //实现excel写
//        String filename = "E:\\write.xlsx";
//        //调用EasyExcel的方法写文件
//        EasyExcel.write (filename,DemoData.class).sheet ("学生列表").doWrite ( getData () );
//    }
    private static List<DemoData> getData(){
        ArrayList<DemoData> list = new ArrayList<> ();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData ();
            data.setSno ( i );
            data.setName ( "lucy"+i );
            list.add ( data );

        }
        return list;
    }
}
