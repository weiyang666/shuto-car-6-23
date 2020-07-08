package com.shuto.mam.workflow.addcar;

import psdi.common.action.ActionCustomClass;
import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;

import java.rmi.RemoteException;
import java.util.Date;

/**
 * @PackageClassname com.shuto.mam.workflow.addcar.LastRecord
 * @Author: luxq
 * @Date: 2020/6/28 14:23:17 workflow.carmaintain
 * @Version: v1.0
 * @Description: 状态设置为已关闭, 车辆状态设为使用中, 信息记录到车辆台账中
 **/
public class LastRecord implements ActionCustomClass {

    @Override
    public void applyCustomAction(MboRemote mboRemote, Object[] objects) throws MXException, RemoteException {
        //当前mbo
        MboRemote mbo = mboRemote;

        //获取车辆信息mboSet
        MboSetRemote carMboSet = mbo.getMboSet("CARNUMBER");
        MboRemote carMbo = carMboSet.getMbo();

        //获取车辆台账mboset
        MboSetRemote ledgerMboSet = mbo.getMboSet("$CARPARAMETER","CARPARAMETER");
        mbo.setFlag(MboConstants.READONLY, false);
        //状态为已关闭
        mbo.setValue("STATUS","已关闭",11L);
        //车辆状态设置为使用中
        carMbo.setValue("CARSTATUS", "使用中",11L);
        //车辆信息赋值车辆台账信息
        //分类
        String carclassification = carMbo.getString("CARCLASSIFICATION");
        //todo 新建mbo,车辆台账
        MboRemote ledgerMbo = ledgerMboSet.add();
        ledgerMbo.setValue("CARTYPE", carclassification,11L);
        //车牌号
        ledgerMbo.setValue("NUMBERPLATE", carMbo.getString("NUMBERPLATE"),11L);
        //车辆品牌
        ledgerMbo.setValue("CARBRAND", carMbo.getString("CARBRAND"),11L);
        //车辆型号
        ledgerMbo.setValue("CARMBOLE", carMbo.getString("CARMODEL"),11L);
        //购车价格
        ledgerMbo.setValue("BUYPRICE", carMbo.getDouble("CARPRICE"),11L);
        //购车时间
        ledgerMbo.setValue("BUYDATE", carMbo.getDate("BUYINGTIME"),11L);
        //购车地址
        ledgerMbo.setValue("BUYADDRESS", carMbo.getString("ADDRESS"),11L);
        //生产厂家
        ledgerMbo.setValue("MANUFACTURER", carMbo.getString("MANUFACTOR"),11L);
        //发动机排量
        ledgerMbo.setValue("ENGINE", carMbo.getString("EMISSIONS"),11L);
        //车架号
        ledgerMbo.setValue("VIN", carMbo.getString("FRAMENUMBER"),11L);
        //车辆状态
        ledgerMbo.setValue("CARSTATUS", carMbo.getString("CARSTATUS"),11L);
        //车辆大小(类型-轿车-吉普......)
        ledgerMbo.setValue("CARSIZE", carMbo.getString("CARSIZE"),11L);
        //入库时间
        ledgerMbo.setValue("WAREHOUSE", new Date(),11L);
        mbo.setFlag(MboConstants.READONLY, true);
    }
}
