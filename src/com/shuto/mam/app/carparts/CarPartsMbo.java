package com.shuto.mam.app.carparts;

import psdi.mbo.MboConstants;
import psdi.mbo.MboSet;
import psdi.mbo.custapp.CustomMbo;
import psdi.util.MXException;

import java.rmi.RemoteException;

/**
 * @PackageClassname com.shuto.mam.app.carparts.CarPartsMbo
 * @Author: luxq
 * @Date: 2020/7/6 15:32:54
 * @Version: v1.0
 * @Description:
 **/
public class CarPartsMbo extends CustomMbo implements CarPartsMboRemote {

    public CarPartsMbo(MboSet ms) throws RemoteException {
        super(ms);
    }

    @Override
    public void init() throws MXException {
        super.init();
    }

    @Override
    public void add() throws MXException, RemoteException {
        this.setFieldFlag("TOEXAMINE", MboConstants.READONLY,true);
        super.add();
    }
}
