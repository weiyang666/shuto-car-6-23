package com.shuto.mam.webclient.beans.clby;

import com.shuto.mam.utils.GetEffectiveCount;
import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
import psdi.webclient.system.beans.DataBean;

import java.rmi.RemoteException;
import java.util.Date;

/**
 * @PackageClassname com.shuto.mam.webclient.beans.clby.CLBYAppBean
 * @Author: luxq
 * @Date: 2020/6/26 17:43:32
 * @Version: v1.0
 * @Description: 车辆保养应用程序
 **/
public class CLBYAppBean extends AppBean {

    /**
     * 触发数据更改事件 主子表
     *
     * @param speaker
     */
    @Override
    public void fireDataChangedEvent(DataBean speaker) {
        try {
            MboRemote mbo = this.getMbo();
            MboSetRemote carnumber = mbo.getMboSet("CARNUMBER");
            if (carnumber.getMbo() != null) {
                //配件总费用
                double totalCostOfAccessories = 0;
                //总工时费用
                double totalWorkingHours = 0;
                //获得配件总费用和工时总费用
                MboRemote temp = null;
                for (int i = 0; (temp = carnumber.getMbo(i)) != null; i++) {
                    //单价
                    double partsprice = temp.getDouble("PARTSPRICE");
                    //数量
                    double pertsnumber = temp.getDouble("PERTSNUMBER");
                    totalCostOfAccessories += partsprice * pertsnumber;
                    //工时单价
                    double unitprice = temp.getDouble("UNITPRICE");
                    //额定工时
                    double workingtime = temp.getDouble("WORKINGTIME");
                    totalWorkingHours = unitprice * workingtime;
                    //赋值小计
                    temp.setValue("SUBTOTAL", partsprice * pertsnumber + unitprice * workingtime, 11L);
                }
                //赋值配件总金额
                mbo.setValue("PARTSTOTAL", totalCostOfAccessories, 11L);
                //赋值
                mbo.setValue("WORKINGTOTAL", totalWorkingHours, 11L);
                //总费用
                double totalCost = totalCostOfAccessories + totalWorkingHours;
                //赋值
                mbo.setValue("TOTALCOST", totalCost, 11L);
                //财务总费用,合计方法
                double totalFinancialExpenses = carnumber.sum("TOEXAMINE");
                mbo.setValue("TOEXAMINESUM", totalFinancialExpenses, 11L);
            }

            //----------------------------------------------------------------------------
            //如果维保种类为事故维修则此字段必填，其它情况只读
            String maintaintype = mbo.getString("MAINTAINTYPE");
            if (maintaintype.equals("事故维修")) {
                mbo.setFieldFlag("describe", MboConstants.READONLY, false);
                mbo.setFieldFlag("describe", MboConstants.REQUIRED, true);
            } else {
                mbo.setValue("describe","",11L);
                mbo.setFieldFlag("describe", MboConstants.REQUIRED, false);
                mbo.setFieldFlag("describe", MboConstants.READONLY, true);
            }
        } catch (MXException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        super.fireDataChangedEvent(speaker);
    }



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
     * 如果流程在待财务审核,就得必填财务核定金额
     *
     * @return
     * @throws MXException
     * @throws RemoteException
     */
    @Override
    public int ROUTEWF() throws MXException, RemoteException {

        //调用工具类判断是否子表有数据
        Integer carnumber = GetEffectiveCount.getCount(getMbo(), "CARNUMBER");
        if (carnumber < 1) {
            throw new MXApplicationException("news", "message");
        }

        String status = this.getMbo().getString("STATUS");
        if ("待财务审核".equals(status)) {
            MboSetRemote mboSet = app.getAppBean().getMbo().getMboSet("CARNUMBER");
            MboRemote remote = null;
            int i = 0;
            while ((remote = mboSet.getMbo(i++)) != null) {
                String toexamine = remote.getString("TOEXAMINE");

                if (toexamine.length() == 0) {
                    throw new MXApplicationException("CARMANAGE", "NOTOEXAMINEERROR");
                }
            }

        }
        //状态为已完成将不能发流程
        if (getMbo().getString("STATUS").equals("已完成")) {
            throw new MXApplicationException("news2", "message3");
        }
        return super.ROUTEWF();
    }
}
