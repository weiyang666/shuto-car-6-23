package com.shuto.mam.app.carperson;

import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.mbo.MboSetRemote;
import psdi.mbo.custapp.CustomMbo;
import psdi.util.MXException;

import java.rmi.RemoteException;

/**
 * @PackageClassname com.shuto.mam.app.carperson.CarPersonMbo
 * @Author: luxq
 * @Date: 2020/7/6 16:30:46
 * @Version: v1.0
 * @Description:
 **/
public class CarPersonMbo extends CustomMbo implements CarPersonMboRemote{

    public CarPersonMbo(MboSet ms) throws RemoteException {
        super(ms);
    }

    @Override
    public void init() throws MXException {
        try {
            MboRemote owner = this.getOwner();
            if (!owner.getString("STATUS").equals("草稿")){
                this.setFlag(MboConstants.READONLY,true);
            }else {
                this.setFlag(MboConstants.READONLY,false);
            }

        } catch (RemoteException e) {
            e.printStackTrace();
        }
        super.init();
    }

    @Override
    public void add() throws MXException, RemoteException {
        super.add();
    }

    @Override
    protected void save() throws MXException, RemoteException {
        super.save();
    }
}
