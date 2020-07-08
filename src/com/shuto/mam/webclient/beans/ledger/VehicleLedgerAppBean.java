package com.shuto.mam.webclient.beans.ledger;

import psdi.mbo.MboRemote;
import psdi.util.MXApplicationYesNoCancelException;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
import psdi.webclient.system.controller.WebClientEvent;

import java.rmi.RemoteException;

/**
 * @PackageClassname com.shuto.mam.webclient.beans.ledger.VehicleLedgerAppBean
 * @Author: luxq
 * @Date: 2020/6/30 17:18:56
 * @Version: v1.0
 * @Description:
 **/
public class VehicleLedgerAppBean extends AppBean {

    /**
     * 自定义按钮
     * 弹出窗口 提示是否按钮,进行逻辑控制
     * @return
     * @throws MXException
     * @throws RemoteException
     */
    public int SWITCHSTATE() throws MXException, RemoteException {
        WebClientEvent event = clientSession.getCurrentEvent();
        int msgRet = event.getMessageReturn();
        if (msgRet < 0) {
            // 弹出提示窗口问是否继续
            throw new MXApplicationYesNoCancelException("BMXAE0003E",
                    "CARMANAGE", "CARCHANGESTATUSERROR", new String[] { "是否将车辆状态变更为"+("使用中".equals(this.getMbo().getString("CARSTATUS"))?"作废":"使用中") });
        }
        if(msgRet == 8) {
            this.getMbo().setValue("CARSTATUS","使用中".equals(this.getMbo().getString("CARSTATUS"))?"作废":"使用中",11L);
        }
        app.getAppBean().refreshTable();
        app.getAppBean().reloadTable();
        return 0;
    }
}
