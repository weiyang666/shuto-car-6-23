package com.shuto.mam.app.carinformation;

import psdi.mbo.Mbo;
import psdi.mbo.MboServerInterface;
import psdi.mbo.MboSet;
import psdi.mbo.custapp.CustomMboSet;
import psdi.util.MXException;

import java.rmi.RemoteException;

/**
 * @PackageClassname com.shuto.mam.app.carinformation.CarinformationMboSet
 * @Author: luxq
 * @Date: 2020/7/7 9:36:34
 * @Version: v1.0
 * @Description:
 **/
public class CarinformationMboSet extends CustomMboSet implements CarinformationMboSetRemote{

    public CarinformationMboSet(MboServerInterface ms) throws RemoteException {
        super(ms);
    }

    @Override
    protected Mbo getMboInstance(MboSet ms) throws MXException, RemoteException {
        return new CarinformationMbo(ms);
    }
}
