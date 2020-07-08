package com.shuto.mam.app.sendcar;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.Mbo;
import psdi.mbo.MboValue;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

import java.rmi.RemoteException;
import java.util.Date;

/**
 * @PackageClassname com.shuto.mam.app.sendcar.FldFallDate
 * @Author: luxq
 * @Date: 2020/7/6 11:39:57
 * @Version: v1.0
 * @Description: 降落到站时间
 **/
public class FldFallDate extends MAXTableDomain {

    public FldFallDate(MboValue mbv) {
        super(mbv);
    }

    /**
     * 降落到站时间不能再起飞发车时间之前
     * @throws MXException
     * @throws RemoteException
     */
    @Override
    public void action() throws MXException, RemoteException {
        Mbo mbo = mboValue.getMbo();
        Date departuredate = mbo.getDate("DEPARTUREDATE");
        Date falldate = mbo.getDate("FALLDATE");
        if (departuredate.after(falldate)){
            throw new MXApplicationException("msg2","startdate");
        }
        super.action();
    }
}
