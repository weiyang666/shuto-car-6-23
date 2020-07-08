package com.shuto.mam.workflow.carmaintain;

import psdi.common.action.ActionCustomClass;
import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;

import java.rmi.RemoteException;

/**
 * @PackageClassname com.shuto.mam.workflow.carmaintain.FinancialAudit
 * @Author: luxq
 * @Date: 2020/6/29 9:21:56
 * @Version: v1.0
 * @Description:
 **/
public class FinancialAudit  implements ActionCustomClass {

    @Override
    public void applyCustomAction(MboRemote mboRemote, Object[] objects) throws MXException, RemoteException {
        //当前mbo
        MboRemote mbo = mboRemote;
        MboRemote owner = mbo.getOwner();
        owner.setValue("CARNUMBER",mbo.getString("CARNUMBER"),11L);
    }
}
