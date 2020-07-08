package com.shuto.mam.app.driverledger;

import com.shuto.mam.utils.CompareDate;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.shuto.mam.utils.CompareDate.compareDate;

/**
 * @PackageClassname com.shuto.mam.app.driverledger.FldDrivingLicenceDate
 * @Author: luxq
 * @Date: 2020/6/28 10:37:46
 * @Version: v1.0
 * @Description: 判断时间是否在之后
 **/
public class FldDrivingLicenceDate extends MboValueAdapter {
    /**
     * 构造
     */
    public FldDrivingLicenceDate() {
    }

    public FldDrivingLicenceDate(MboValue mbv) {
        super(mbv);
    }

    /**
     * 校验方法
     * 校验驾龄是?年
     * @throws MXException
     * @throws RemoteException
     */
    @Override
    public void validate() throws MXException, RemoteException {
        super.validate();
        Date newDate = null;
        Date drivinglicencedate = null;
        try {
            //获取字段时间
            drivinglicencedate = this.getMboValue().getDate();
            if (drivinglicencedate != null) {
                //创建当前时间0000-00-00 00:00:00
                newDate = new Date();
                //创建时间格式化0000-00-00
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                //时间格式化获取时间为字符串0000-00-00
                String format = sdf.format(drivinglicencedate);
                //时间格式化当前时间为字符串0000-00-00
                String newFormat = sdf.format(newDate);
                //时间格式化字符串获取时间为时间格式0000-00-00
                Date parse = sdf.parse(format);
                //时间格式化字符串当前时间为时间格式0000-00-00
                Date newParse = sdf.parse(newFormat);
                //判断是否为当前时间如果是直接跳过判断以前时间
                if (parse.equals(newParse)) {

                } else {
                    //判断以前时间
                    if (drivinglicencedate.after(newDate)) {
                        //异常弹框处理                     消息名称     关键字
                        throw new MXApplicationException("prompt2", "applyForADriverSLicense");
                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //判断以前时间
        if (drivinglicencedate.after(newDate)) {
            //异常弹框处理                     消息名称     关键字
            throw new MXApplicationException("prompt2", "applyForADriverSLicense");
        }
    }

    /**
     * 变更
     *
     * @throws MXException
     * @throws RemoteException
     */
    @Override
    public void action() throws MXException, RemoteException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //时间格式化获取时间为字符串0000-00-00
        String format = sdf.format(this.getMboValue().getDate());
        //计算驾龄
        this.getMboValue().getMbo().setValue("DRIVINGYEARS", compareDate(format, null, 2) + "年");
        super.action();
    }
}
