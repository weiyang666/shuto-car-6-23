package com.shuto.mam.app.sendcar;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.Mbo;
import psdi.mbo.MboValue;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

import java.rmi.RemoteException;
import java.util.Date;

/**
 * @PackageClassname com.shuto.mam.app.sendcar.FldDepartureDate
 * @Author: luxq
 * @Date: 2020/7/6 11:38:43
 * @Version: v1.0
 * @Description: 起飞发车时间
 **/
public class FldDepartureDate extends MAXTableDomain {

    public FldDepartureDate(MboValue mbv) {
        super(mbv);
    }

    @Override
    public void action() throws MXException, RemoteException {
        Mbo mbo = mboValue.getMbo();
        Date departuredate = mbo.getDate("DEPARTUREDATE");
        if (departuredate.before(new Date())){
            throw new MXApplicationException("msg2","startdate1");
        }
        super.action();
    }
}
