package com.shuto.mam.app.carinformation;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

import java.rmi.RemoteException;
import java.util.Date;

/**
 * @PackageClassname com.shuto.mam.app.carinformation.FldBuyingtime
 * @Author: luxq
 * @Date: 2020/7/7 9:46:07
 * @Version: v1.0
 * @Description:
 **/
public class FldBuyingtime extends MboValueAdapter {

    public FldBuyingtime(MboValue mbv) {
        super(mbv);
    }


    @Override
    public void validate() throws MXException, RemoteException {
        super.validate();
    }

    /**
     * 校验购车时间
     * @throws MXException
     * @throws RemoteException
     */
    @Override
    public void action() throws MXException, RemoteException {
        Date date = getMboValue().getDate();
        if (date.after(new Date())){
            throw new MXApplicationException("msg2","buyingtime");
        }
        super.action();
    }
}
