package com.shuto.mam.webclient.beans.vehicledispatching;

import com.shuto.mam.utils.GetEffectiveCount;
import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
import psdi.webclient.system.beans.DataBean;

import java.rmi.RemoteException;

/**
 * @PackageClassname com.shuto.mam.webclient.beans.vehicledispatching.VehicleDispatchingAppBean
 * @Author: luxq
 * @Date: 2020/6/27 20:38:59
 * @Version: v1.0
 * @Description:
 **/
public class VehicleDispatchingAppBean extends AppBean {

    /**
     * 触发更改事件-子主表
     *
     * @param speaker
     */
    @Override
    public void fireDataChangedEvent(DataBean speaker) {
        try {
            //用车类型
            /*用车类型为接机、送机、接站、送站时必填，其它情况为只读*/
            String usercartype = getMbo().getString("USERCARTYPE");
            if (usercartype.equals("接机") || usercartype.equals("送机") || usercartype.equals("接站") || usercartype.equals("送站")) {
                this.getMbo().setFieldFlag("FLIGHT", MboConstants.READONLY, false);
                this.getMbo().setFieldFlag("FLIGHT", MboConstants.REQUIRED, true);
            } else {
                this.getMbo().setFieldFlag("FLIGHT", MboConstants.REQUIRED, false);
                this.getMbo().setValue("FLIGHT", "", 11L);
                this.getMbo().setFieldFlag("FLIGHT", MboConstants.READONLY, true);
            }
            /*用车类型为送机、送站时必填，其它情况为只读*/
            if (usercartype.equals("送站") || usercartype.equals("送机")) {
                this.getMbo().setFieldFlag("STARTAIRPORT", MboConstants.READONLY, false);
                this.getMbo().setFieldFlag("STARTAIRPORT", MboConstants.REQUIRED, true);

                this.getMbo().setFieldFlag("ARRIVEAIRPORT", MboConstants.READONLY, false);
                this.getMbo().setFieldFlag("ARRIVEAIRPORT", MboConstants.REQUIRED, true);

                this.getMbo().setFieldFlag("DEPARTUREDATE", MboConstants.READONLY, false);
                this.getMbo().setFieldFlag("DEPARTUREDATE", MboConstants.REQUIRED, true);

                this.getMbo().setFieldFlag("FALLDATE", MboConstants.READONLY, false);
                this.getMbo().setFieldFlag("FALLDATE", MboConstants.REQUIRED, true);
            } else {
                this.getMbo().setFieldFlag("STARTAIRPORT", MboConstants.REQUIRED, false);
                this.getMbo().setValue("STARTAIRPORT", "", 11L);
                this.getMbo().setFieldFlag("STARTAIRPORT", MboConstants.READONLY, true);

                this.getMbo().setFieldFlag("ARRIVEAIRPORT", MboConstants.REQUIRED, false);
                this.getMbo().setValue("ARRIVEAIRPORT", "", 11L);
                this.getMbo().setFieldFlag("ARRIVEAIRPORT", MboConstants.READONLY, true);

                this.getMbo().setFieldFlag("DEPARTUREDATE", MboConstants.REQUIRED, false);
                this.getMbo().setValue("DEPARTUREDATE", "", 11L);
                this.getMbo().setFieldFlag("DEPARTUREDATE", MboConstants.READONLY, true);

                this.getMbo().setFieldFlag("FALLDATE", MboConstants.REQUIRED, false);
                this.getMbo().setValue("FALLDATE", "", 11L);
                this.getMbo().setFieldFlag("FALLDATE", MboConstants.READONLY, true);
            }
            //乘车人数赋值
            Integer applicationnumber = GetEffectiveCount.getCount(app.getAppBean().getMbo(), "APPLICATIONNUMBER");
            app.getAppBean().getMbo().setValue("CAPERSONNUM", applicationnumber + "", 11L);
        } catch (MXException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        super.fireDataChangedEvent(speaker);
    }

    /**
     * 触发数据更改事件主表
     */
    @Override
    public void fireDataChangedEvent() {
        super.fireDataChangedEvent();
    }


    /**
     * 发送工作流调用此方法
     * 	流程在车辆调度时，车牌号、驾驶员、联系电话为必填
     *
     * @return
     * @throws MXException
     * @throws RemoteException 车牌号
     *                         驾驶员 	SENDCAR.LICENSEPLATE
     *                         表列：	 	SENDCAR.DRIVER
     *                         联系人       SENDCAR.PHONE
     */
    @Override
    public int ROUTEWF() throws MXException, RemoteException {
        String status = this.getMbo().getString("STATUS");

        if ("待车辆调度员调度".equals(status)) {
            MboRemote mbo = app.getAppBean().getMbo();
            mbo.setFieldFlag("LICENSEPLATE", MboConstants.REQUIRED, true);
            mbo.setFieldFlag("DRIVER", MboConstants.REQUIRED, true);
            if (mbo.getString("LICENSEPLATE").isEmpty() || mbo.getString("DRIVER.PHONE").isEmpty() || mbo.getString("DRIVER").isEmpty()) {
                throw new MXApplicationException("msg3", "dispatch");
            }
        }
        //状态为已完成将不能发流程
        if (getMbo().getString("STATUS").equals("已完成")) {
            throw new MXApplicationException("news2", "message3");
        }
        return super.ROUTEWF();
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
