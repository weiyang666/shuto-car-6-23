package com.shuto.mam.workflow.sendcar;

import psdi.common.action.ActionCustomClass;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;

import java.rmi.RemoteException;

/**
 * @PackageClassname com.shuto.mam.workflow.sendcar.Complete
 * @Author: luxq
 * @Date: 2020/7/7 10:22:16
 * @Version: v1.0
 * @Description:
 **/
public class Complete implements ActionCustomClass {

    @Override
    public void applyCustomAction(MboRemote mboRemote, Object[] objects) throws MXException, RemoteException {
        MboSetRemote driver = mboRemote.getMboSet("DRIVER");
        MboRemote mbo=null;
        for (int i=0;(mbo=driver.getMbo(i))!=null;i++){
            mbo.setValue("STATUS","已派出",11L);
        }
        mboRemote.setValue("STATUS","已完成",11L);
    }

}
