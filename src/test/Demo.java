package test;

import static com.shuto.mam.utils.CompareDate.compareDate;

/**
 * @PackageClassname test.Demo
 * @Author: luxq
 * @Date: 2020/7/7 9:11:52
 * @Version: v1.0
 * @Description:
 **/
public class Demo {
    public static void main(String[] args) {
        compareDate("2009-7-7", null, 0);//比较天
        compareDate("2009-7-7", null, 1);//比较月
        compareDate("2009-7-7", null, 2);//比较年
    }
}
