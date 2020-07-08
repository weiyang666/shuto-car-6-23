package com.shuto.mam.app.sendcar;

import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.mbo.MboSetRemote;
import psdi.mbo.custapp.CustomMbo;
import psdi.util.MXException;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @PackageClassname com.shuto.mam.app.sendcar.SendCarMbo
 * @Author: luxq
 * @Date: 2020/6/27 20:33:21
 * @Version: v1.0
 * @Description:
 **/
public class SendCarMbo extends CustomMbo implements SendCarMboRemote {

    public SendCarMbo(MboSet ms) throws RemoteException {
        super(ms);
    }


    /**
     * 初始化
     * 如果是新建则可以调用添加编号方法,
     * 标题只读，根据申请人填写的内容自动生成，生成规则 申请人+年份+“派车申请”
     *
     * @throws MXException
     */
    @Override
    public void init() throws MXException {
        try {
            /*如果是新建则可以调用添加编号方法*/
            newNumber();
            //只读
            readOnly();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        super.init();
        //航班出发机场到达机场前起飞时间到达时间都设置只读
        this.setFieldFlag("STARTAIRPORT", MboConstants.READONLY, true);
        this.setFieldFlag("FLIGHT", MboConstants.READONLY, true);
        this.setFieldFlag("ARRIVEAIRPORT", MboConstants.READONLY, true);
        this.setFieldFlag("DEPARTUREDATE", MboConstants.READONLY, true);
        this.setFieldFlag("FALLDATE", MboConstants.READONLY, true);
        //乘车人数
        this.setFieldFlag("CAPERSONNUM", MboConstants.READONLY, true);
        //标题只读，根据申请人填写的内容自动生成，生成规则 申请人+年份+“派车申请”
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
            String proposer = this.getString("PERSON.DISPLAYNAME");
            String format = sdf.format(new Date());
            this.setValue("TITLE", proposer + "-" + format + "-派车申请", 11L);
            String status = this.getString("STATUS");
            //车辆调度
            if ("待车辆调度员调度".equals(status)) {
                String[] str = {"USECARREASONS", "WEATHER", "FLIGHT", "STARTAIRPORT","USERCARTYPE", "PCPHONE", "CARTYPE",
                        "ARRIVEAIRPORT", "APPLICATIONPHONE", "STARTDATE", "DEPARTUREDATE", "PERSONID",
                        "PERSON.DISPLAYNAME", "ENDDATE", "FALLDATE"};
                this.setFieldFlag(str, MboConstants.READONLY, true);
                //开启联系人,电话,需要
                this.setFieldFlag("LICENSEPLATE", MboConstants.REQUIRED, true);
                this.setFieldFlag("DRIVER", MboConstants.REQUIRED, true);
                this.setFieldFlag("DRIVER.PHONE", MboConstants.REQUIRED, true);
            } else {
                //关闭需要
                this.setFieldFlag("LICENSEPLATE", MboConstants.REQUIRED, false);
                this.setFieldFlag("DRIVER", MboConstants.REQUIRED, false);
                this.setFieldFlag("DRIVER.PHONE", MboConstants.REQUIRED, false);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取状态,如果是草稿状态,或者不是新建,不可以修改 (只读)
     * they ignore you now but they'll need you later that's life
     * dont kill the dream -execute it
     *
     * @throws MXException
     * @throws RemoteException
     */
    private void readOnly() throws MXException, RemoteException {
        //获取状态
        String status = this.getString("STATUS");
        //如果是不是草稿状态,或者不是新建,不可以修改 (只读)
        if (!"草稿".equals(status) && !this.isNew() && !"待车辆调度员调度".equals(status)) {
            this.setFlag(MboConstants.READONLY, true);
        }
    }

    /**
     * 如果是新建则可以调用添加编号方法,
     *
     * @throws RemoteException
     * @throws MXException
     */
    private void newNumber() throws RemoteException, MXException {
        if (this.isNew()) {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String formatDate = sdf.format(date);
            try {
                MboSetRemote carIncrease = this.getMboSet("$SENDCAR", "SENDCAR",
                        " APPLICATIONNUMBER LIKE '%" + formatDate + "%' ");
                int count = carIncrease.count() + 1;
                String serialNumber = "PC" + formatDate;
                if (count < 10) {
                    serialNumber += "000" + count;
                } else if (count < 100) {
                    serialNumber += "00" + count;

                } else if (count < 1000) {
                    serialNumber += "0" + count;
                } else {
                    serialNumber += String.valueOf(count);
                }
                this.setValue("APPLICATIONNUMBER", serialNumber, 11L);
                //XZ2020 06 24 0001
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 新建
     *
     * @throws MXException
     * @throws RemoteException
     */
    @Override
    public void add() throws MXException, RemoteException {
        super.add();
        this.setFieldFlag("STARTAIRPORT", MboConstants.READONLY, true);
        this.setFieldFlag("FLIGHT", MboConstants.READONLY, true);
        this.setFieldFlag("ARRIVEAIRPORT", MboConstants.READONLY, true);
        this.setFieldFlag("DEPARTUREDATE", MboConstants.READONLY, true);
        this.setFieldFlag("FALLDATE", MboConstants.READONLY, true);
    }

    /**
     * 保存
     *
     * @throws MXException
     * @throws RemoteException
     */
    @Override
    protected void save() throws MXException, RemoteException {
        super.save();
    }
}
