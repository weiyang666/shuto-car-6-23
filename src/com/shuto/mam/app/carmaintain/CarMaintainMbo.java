package com.shuto.mam.app.carmaintain;

import com.shuto.mam.app.carincrease.CarIncreaseMboRemote;
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
 * @PackageClassname com.shuto.mam.app.carmaintain.CarMaintainMbo
 * @Author: luxq
 * @Date: 2020/6/26 17:46:12
 * @Version: v1.0
 * @Description: 车辆维护
 **/
public class CarMaintainMbo extends CustomMbo implements CarMaintainMboRemote {

    /**
     * 构造方法
     *
     * @param ms
     * @throws RemoteException
     */

    public CarMaintainMbo(MboSet ms) throws RemoteException {
        super(ms);
    }

    /**
     * 初始化调用此方法
     *
     * @throws MXException
     */
    @Override
    public void init() throws MXException {
        super.init();

        try {
            /*如果是新建则可以调用添加编号方法*/
            newNumber();
            //自动计算维护次数
            String numberplate = this.getString("NUMBERPLATE");
            MboSetRemote carmaintain = this.getMboSet("$CARMAINTAIN","CARMAINTAIN","NUMBERPLATE like '"+numberplate+"' and STATUS='已完成'");
            int count = carmaintain.count();
            this.setValue("MAINTAINBATCH",count+"",11L);
            this.setFieldFlag("MAINTAINBATCH",MboConstants.READONLY,true);
            //事故描述
            this.setFieldFlag("DESCRIBE",MboConstants.READONLY,true);
            //配件总费用 工时总费用 财务核定总费用  总费用
            this.setFieldFlag("PARTSTOTAL",MboConstants.READONLY,true);
            this.setFieldFlag("WORKINGTOTAL",MboConstants.READONLY,true);
            this.setFieldFlag("TOEXAMINESUM",MboConstants.READONLY,true);
            this.setFieldFlag("TOTALCOST",MboConstants.READONLY,true);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MXException e) {
            e.printStackTrace();
        }
        //如果是待财务审核就设置核定金额必填
        try {
//           主表 属性数组
            String[] propertyArr={"NUMBERPLATE","DESCRIBE","MAINTAINTYPE","CONTENT","MAINTAINDATE","PARTSTOTAL","WORKINGTOTAL","TOEXAMINESUM","TOTALCOST"};
            //子表
            String[] fittingProperty={"PARTSCONTENT","PARTSNAME","PARTSPRICE","PERTSNUMBER","UNITPRICE","WORKINGTIME","SUBTOTAL"};
            //获取状态
            String status = this.getString("STATUS");
            if (!"待财务审核".equals(status)) {
                MboSetRemote carnumber = this.getMboSet("CARNUMBER");
                MboRemote exp = null;
                for (int i = 0; (exp = carnumber.getMbo(i)) != null; i++) {
                    //财务核定金额必填设置只读
                    exp.setFieldFlag("TOEXAMINE",MboConstants.READONLY,true);

                }
            }
            if ("待财务审核".equals(status)) {
                this.setFieldFlag(propertyArr,MboConstants.READONLY,true);
                MboSetRemote carnumber = this.getMboSet("CARNUMBER");
                MboRemote exp = null;
                for (int i = 0; (exp = carnumber.getMbo(i)) != null; i++) {
                    exp.setFieldFlag(fittingProperty,MboConstants.READONLY,true);
                    //财务核定金额必填关闭只读
                    exp.setFieldFlag("TOEXAMINE",MboConstants.READONLY,false);

                }
            }
            /*获取状态,如果是草稿状态,或者不是新建,不可以修改 (只读)*/
            readOnly();

        }  catch (MXException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
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
        this.setFieldFlag("describe", MboConstants.READONLY, true);
    }

    /**
     * 保存时候调用
     *
     * @throws MXException
     * @throws RemoteException
     */
    @Override
    protected void save() throws MXException, RemoteException {
        //保存判断子表没有数据就不允许保存
        MboSetRemote carMaintainMbo = this.getMboSet("CARNUMBER");
        int count = carMaintainMbo.count();
        if (count < 1) {
            throw new MXApplicationException("news2", "message2");
        }
        super.save();
    }


    /**
     * 在新增车辆的时候添加编号,编码规则BY+yyyyMMdd+zzzz（例如：XZ201705090001 XZ+年月日+四位流水号，流水号每天从0001开始）
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
                MboSetRemote carIncrease = this.getMboSet("$CARMAINTAIN", "CARMAINTAIN",
                        " CARNUMBER LIKE '%" + formatDate + "%' ");
                int count = carIncrease.count() + 1;
                String serialNumber = "BY" + formatDate;
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
     * 	草稿状态下页面信息可以修改，
     * 在“待财务审核”时，财务人员需要必填子表中的财务核定金额，
     * 其它状态全部为只读
     *
     * @throws MXException
     * @throws RemoteException
     */
    private void readOnly() throws MXException, RemoteException {
        //获取状态
        String status = this.getString("STATUS");
        //如果是草稿状态,或者不是新建,不可以修改 (只读)
        if (!"草稿".equals(status) && !this.isNew() && !"待财务审核".equals(status)) {
            this.setFlag(MboConstants.READONLY, true);
        }
    }
}
