package com.shuto.mam.app.sendcar;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.Mbo;
import psdi.mbo.MboValue;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @PackageClassname com.shuto.mam.app.sendcar.FldStartDate
 * @Author: luxq
 * @Date: 2020/7/6 11:35:55
 * @Version: v1.0
 * @Description: 出发时间:出发时间不能大于结束时间
 **/
public class FldStartDate extends MAXTableDomain {

    public FldStartDate(MboValue mbv) {
        super(mbv);
    }

    /**
     * 界面出发时间改变调用
     * 出发时间不能在今天之前
     *
     * @throws MXException
     * @throws RemoteException
     */
    @Override
    public void action() throws MXException, RemoteException {
        Mbo mbo = mboValue.getMbo();
        Date startdate = mbo.getDate("STARTDATE");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (sdf.format(startdate).equals(sdf.format(new Date()))) {

        }else {
            if (startdate.before(new Date())) {
                throw new MXApplicationException("msg2", "startdate1");
            }
        }
        super.action();
    }
}
