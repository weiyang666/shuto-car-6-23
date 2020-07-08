package com.shuto.mam.app.carincrease;

import psdi.mbo.Mbo;
import psdi.mbo.MboServerInterface;
import psdi.mbo.MboSet;
import psdi.mbo.custapp.CustomMboSet;
import psdi.util.MXException;

import java.rmi.RemoteException;

/**
 * @PackageClassname  com.shuto.mam.app.carincrease.CarIncreaseMboSet
 * @Author: luxq
 * @Date: 2020/6/23 17:52:46
 * @Version: v1.0
 * @Description:
 **/
public class CarIncreaseMboSet extends CustomMboSet implements CarIncreaseMboSetRemote {

    /**
     * 构造
     * @param ms
     * @throws RemoteException
     */
    public CarIncreaseMboSet(MboServerInterface ms) throws RemoteException {
         super(ms);
    }

    @Override
    protected Mbo getMboInstance(MboSet ms) throws MXException, RemoteException {
        return new CarIncreaseMbo(ms);
    }
}
