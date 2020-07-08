package com.shuto.mam.app.sendcar;

import psdi.mbo.Mbo;
import psdi.mbo.MboServerInterface;
import psdi.mbo.MboSet;
import psdi.mbo.custapp.CustomMboSet;
import psdi.util.MXException;

import java.rmi.RemoteException;

/**
 * @PackageClassname com.shuto.mam.app.sendcar.SendCarMboSet
 * @Author: luxq
 * @Date: 2020/6/27 20:34:35
 * @Version: v1.0
 * @Description:
 **/
public class SendCarMboSet extends CustomMboSet implements SendCarMboSetRemote {

    public SendCarMboSet(MboServerInterface ms) throws RemoteException {
        super(ms);
    }

    /**
     * 返回mbo
     *
     * @param ms
     * @return
     * @throws MXException
     * @throws RemoteException
     */
    @Override
    protected Mbo getMboInstance(MboSet ms) throws MXException, RemoteException {
        return new SendCarMbo(ms);
    }
}
