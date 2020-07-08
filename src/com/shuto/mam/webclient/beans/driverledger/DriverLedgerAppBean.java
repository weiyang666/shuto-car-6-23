package com.shuto.mam.webclient.beans.driverledger;

import psdi.mbo.MboConstants;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;

import java.rmi.RemoteException;

/**
 * @PackageClassname com.shuto.mam.webclient.beans.driverledger.DriverLedgerAppBean
 * @Author: luxq
 * @Date: 2020/6/28 8:58:37
 * @Version: v1.0
 * @Description: 驾驶人台账
 **/
public class DriverLedgerAppBean extends AppBean {

    /**
     * 触发数据更改事件
     * 判断事件如果是在今天之前可以,但是不能再今天之后
     */
    @Override
    public void fireDataChangedEvent() {
        super.fireDataChangedEvent();

    }

    /**
     * addrow方法
     * 驾驶员的唯一标识,编码规则JSY-ZZ（例如：JSY-01 JSY-两位流水号，流水号从01开始）
     *
     * @return
     * @throws MXException
     */
    @Override
    public int addrow() throws MXException {
        super.addrow();
        try {
            int count = this.getMboSet().count();
            if (count < 10) {
                this.setValue("DRIVERNUMBER", "JSY-0" + count,11L);
                this.getMbo().setFieldFlag("DRIVERNUMBER", MboConstants.READONLY,true);
            } else {
                this.getMbo().setValue("DRIVERNUMBER", "JSY-" + count);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return 0;
    }


    /**
     * 保存
     *
     * @return
     * @throws MXException
     * @throws RemoteException
     */
    @Override
    public int SAVE() throws MXException, RemoteException {
        return super.SAVE();
    }
}
