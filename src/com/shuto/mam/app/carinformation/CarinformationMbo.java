package com.shuto.mam.app.carinformation;

import psdi.mbo.MboSet;
import psdi.mbo.custapp.CustomMbo;
import psdi.util.MXException;

import java.rmi.RemoteException;

/**
 * @PackageClassname com.shuto.mam.app.carinformation.CarinformationMbo
 * @Author: luxq
 * @Date: 2020/7/7 9:36:19
 * @Version: v1.0
 * @Description: 车辆信息mbo
 **/
public class CarinformationMbo extends CustomMbo implements CarinformationMboRemote {

    public CarinformationMbo(MboSet ms) throws RemoteException {
        super(ms);
    }

    @Override
    public void init() throws MXException {
        super.init();
    }

    @Override
    protected void save() throws MXException, RemoteException {
        super.save();
    }


}
