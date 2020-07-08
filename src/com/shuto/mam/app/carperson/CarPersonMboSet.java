package com.shuto.mam.app.carperson;

import psdi.mbo.Mbo;
import psdi.mbo.MboServerInterface;
import psdi.mbo.MboSet;
import psdi.mbo.custapp.CustomMboSet;
import psdi.util.MXException;

import java.rmi.RemoteException;

/**
 * @PackageClassname com.shuto.mam.app.carperson.CarPersonMboSet
 * @Author: luxq
 * @Date: 2020/7/6 16:34:02
 * @Version: v1.0
 * @Description:
 **/
public class CarPersonMboSet extends CustomMboSet implements CarPersonMboSetRemote{
    public CarPersonMboSet(MboServerInterface ms) throws RemoteException {
        super(ms);
    }

    @Override
    protected Mbo getMboInstance(MboSet ms) throws MXException, RemoteException {
        return new CarPersonMbo(ms);
    }
}
