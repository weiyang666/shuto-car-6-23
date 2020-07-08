package com.shuto.mam.app.carmaintain;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboValue;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

import java.rmi.RemoteException;
import java.util.Date;

/**
 * @PackageClassname com.shuto.mam.app.carmaintain.FldMaintainDate
 * @Author: luxq
 * @Date: 2020/7/6 10:21:09
 * @Version: v1.0
 * @Description: 维保时间不能再今天之后
 **/
public class FldMaintainDate extends MAXTableDomain {

    public FldMaintainDate(MboValue mbv) {
        super(mbv);
    }

    /**
     * 字段值发生改变调用
     * 维保时间不能再今天之后
     * @throws MXException
     * @throws RemoteException
     */
    @Override
    public void action() throws MXException, RemoteException {
        //维保时间不能再今天之后
        Date maintaindate = mboValue.getMbo().getDate("MAINTAINDATE");
        if (maintaindate.after(new Date())){
            throw new MXApplicationException("msg1:","maintenanceTime");
        }
        super.action();
    }
}
