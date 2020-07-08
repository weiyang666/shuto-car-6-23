package com.shuto.mam.app.carparts;

import com.shuto.mam.app.carmaintain.CarMaintainMboSetRemote;
import psdi.mbo.Mbo;
import psdi.mbo.MboServerInterface;
import psdi.mbo.MboSet;
import psdi.mbo.custapp.CustomMboSet;
import psdi.util.MXException;

import java.rmi.RemoteException;

/**
 * @PackageClassname com.shuto.mam.app.carparts.CarPartsMboSet
 * @Author: luxq
 * @Date: 2020/7/6 15:33:21
 * @Version: v1.0
 * @Description:
 **/
public class CarPartsMboSet extends CustomMboSet implements CarPartsMboSetRemote {

    public CarPartsMboSet(MboServerInterface ms) throws RemoteException {
        super(ms);
    }

    @Override
    protected Mbo getMboInstance(MboSet ms) throws MXException, RemoteException {
        return new CarPartsMbo(ms);
    }
}
