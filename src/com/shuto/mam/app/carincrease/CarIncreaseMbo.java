package com.shuto.mam.app.carincrease;

import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.mbo.MboSetRemote;
import psdi.mbo.custapp.CustomMbo;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @PackageClassname mam.app.carAdministration.CarMbo
 * @Author: luxq
 * @Date: 2020/6/23 17:44:39
 * @Version: v1.0
 * @Description: 汽车管理mbo
 **/
public class CarIncreaseMbo extends CustomMbo implements CarIncreaseMboRemote {

    /**
     * 构造方法
     *
     * @param ms
     * @throws RemoteException
     */
    public CarIncreaseMbo(MboSet ms) throws RemoteException {
        super(ms);
    }

    /**
     * 初始化调用此方法
     *
     * @throws MXException
     */
    @Override
    public void init() throws MXException {
        try {
            /*如果是新建则可以调用添加编号方法*/
            newNumber();
            /*调用方法获取状态,如果是草稿状态,或者不是新建,不可以修改 (只读)*/
            readOnly();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        super.init();
    }

    /**
     * 在新增车辆的时候添加编号,编码规则XZ+yyyyMMdd+zzzz（例如：XZ201705090001 XZ+年月日+四位流水号，流水号每天从0001开始）
     *
     * @throws MXException
     */
    private void newNumber() throws RemoteException, MXException {
        /*如果是新建则可以调用添加编号方法*/
        if (this.isNew()) {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String formatDate = sdf.format(date);
            try {
                MboSetRemote carIncrease = this.getMboSet("$CARINCREASE", "CARINCREASE",
                        " CARNUMBER LIKE '%" + formatDate + "%' ");
                int count = carIncrease.count() + 1;
                String serialNumber = "XZ" + formatDate;
                if (count < 10) {
                    serialNumber += "000" + count;
                } else if (count < 100) {
                    serialNumber += "00" + count;

                } else if (count < 1000) {
                    serialNumber += "0" + count;
                } else {
                    serialNumber += String.valueOf(count);
                }
                this.setValue("CARNUMBER", serialNumber, 11L);
                //XZ2020 06 24 0001
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取状态,如果是草稿状态,或者不是新建,不可以修改 (只读)
     *
     * @throws MXException
     * @throws RemoteException
     */
    private void readOnly() throws MXException, RemoteException {
        //获取状态
        String status = this.getString("STATUS");
        //如果是草稿状态,或者不是新建,不可以修改 (只读)
        if (!"草稿".equals(status) && !this.isNew()) {
            this.setFlag(MboConstants.READONLY, true);
        }
    }


    /**
     * 保存时候调用
     *
     * @throws MXException
     * @throws RemoteException
     */
    @Override
    protected void save() throws MXException, RemoteException {
        //保存时候调用,把项目名称赋值到项目名称数据库字段
        // String string = this.getString("PROJECT.PROJECTNAME");
        // this.setValue("PROJECTNAME",string,11L);
        super.save();
    }
}
