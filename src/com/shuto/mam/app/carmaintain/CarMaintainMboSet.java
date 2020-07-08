package com.shuto.mam.app.carmaintain;

import psdi.mbo.Mbo;
import psdi.mbo.MboServerInterface;
import psdi.mbo.MboSet;
import psdi.mbo.custapp.CustomMboSet;
import psdi.util.MXException;

import java.rmi.RemoteException;

/**
 * @PackageClassname com.shuto.mam.app.carmaintain.CarMaintainMboSet
 * @Author: luxq
 * @Date: 2020/6/26 17:49:48
 * @Version: v1.0
 * @Description:
 **/
public class CarMaintainMboSet extends CustomMboSet implements CarMaintainMboSetRemote {
    /**
     * 构造
     *
     * @param ms
     * @throws RemoteException
     */
    public CarMaintainMboSet(MboServerInterface ms) throws RemoteException {
        super(ms);
    }

    /**
     * 返回mbo
     * @param ms
     * @return
     * @throws MXException
     * @throws RemoteException
     */
    @Override
    protected Mbo getMboInstance(MboSet ms) throws MXException, RemoteException {
        return new CarMaintainMbo(ms);
    }
}
