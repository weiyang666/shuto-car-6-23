package com.shuto.mam.app.sendcar;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.Mbo;
import psdi.mbo.MboValue;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

import java.rmi.RemoteException;
import java.util.Date;

/**
 * @PackageClassname com.shuto.mam.app.sendcar.FldEndDate
 * @Author: luxq
 * @Date: 2020/7/6 11:36:58
 * @Version: v1.0
 * @Description:  结束时间
 **/
public class FldEndDate extends MAXTableDomain {

    public FldEndDate(MboValue mbv) {
        super(mbv);
    }
    /**
     * 界面结束时间规范(结束时间不能再开始时间之前)
     * @throws MXException
     * @throws RemoteException
     */
    @Override
    public void action() throws MXException, RemoteException {
        Mbo mbo = mboValue.getMbo();
        Date startdate = mbo.getDate("STARTDATE");
        Date enddate = mbo.getDate("ENDDATE");
        if (startdate.after(enddate)){
            throw new MXApplicationException("msg2","startdate");
        }
        super.action();
    }
}
