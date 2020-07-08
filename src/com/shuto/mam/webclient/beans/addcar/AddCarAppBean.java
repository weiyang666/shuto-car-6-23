package com.shuto.mam.webclient.beans.addcar;

import com.shuto.mam.utils.GetEffectiveCount;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;

import java.rmi.RemoteException;

/**
 * @PackageClassname com.shuto.mam.webclient.beans.addcar.AddCarAppBean
 * @Author: luxq
 * @Date: 2020/6/24 14:12:02
 * @Version: v1.0
 **/
public class AddCarAppBean extends AppBean {

    /**
     * 保存调用此方法
     * @return
     * @throws MXException
     * @throws RemoteException
     */
    @Override
    public int SAVE() throws MXException, RemoteException {
        return super.SAVE();
    }

    /**
     * 发送工作流调用此方法
     * @return
     * @throws MXException
     * @throws RemoteException
     */
    @Override
    public int ROUTEWF() throws MXException, RemoteException {
        //调用工具类判断是否子表有数据
        Integer carnumber = GetEffectiveCount.getCount(getMbo(), "CARNUMBER");
        if (carnumber<1){
            throw new MXApplicationException("news","message");
        }
        //状态为已完成将不能发流程
        if ("已关闭".equals(getMbo().getString("STATUS"))){
            throw new MXApplicationException("news2","message3");
        }
        return super.ROUTEWF();
    }


}
