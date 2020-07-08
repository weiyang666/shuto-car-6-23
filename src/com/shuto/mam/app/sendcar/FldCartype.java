package com.shuto.mam.app.sendcar;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

import java.rmi.RemoteException;

/**
 * @PackageClassname com.shuto.mam.app.sendcar.FldCartype
 * @Author: luxq
 * @Date: 2020/7/7 20:03:01
 * @Version: v1.0
 * @Description:
 **/
public class FldCartype  extends MboValueAdapter {
    public FldCartype() {
    }

    public FldCartype(MboValue mbv) {
        super(mbv);
    }

    /**
     * 类型发生改变清楚下方车牌号
     * @throws MXException
     * @throws RemoteException
     */
    @Override
    public void action() throws MXException, RemoteException {
        getMboValue().getMbo().setValue("LICENSEPLATE","",11L);
        super.action();
    }
}
