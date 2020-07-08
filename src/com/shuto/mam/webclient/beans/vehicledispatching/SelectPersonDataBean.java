package com.shuto.mam.webclient.beans.vehicledispatching;

import com.shuto.mam.utils.GetEffectiveCount;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

import java.rmi.RemoteException;
import java.util.Vector;

/**
 * @PackageClassname com.shuto.mam.webclient.beans.vehicledispatching.SelectPersonDataBean
 * @Author: luxq
 * @Date: 2020/6/28 20:05:15
 * @Version: v1.0
 * @Description:
 **/
public class SelectPersonDataBean extends DataBean {


    /**
     * 点击按钮[选择人员]调用此方法,进行人员的过滤
     * @return
     * @throws MXException
     * @throws RemoteException
     */
    @Override
    protected MboSetRemote getMboSetRemote() throws MXException, RemoteException {
        MboSetRemote applicationnumber = app.getAppBean().getMbo().getMboSet("APPLICATIONNUMBER");
        MboRemote borLine = null;
        //如果选择了,就不在显示
        StringBuffer stringBuffer = new StringBuffer("'-1'");
        for (int i = 0; (borLine = applicationnumber.getMbo(i)) != null; i++) {
            stringBuffer.append(",'");
            stringBuffer.append(borLine.getString("PERSONNUMBER"));
            stringBuffer.append("'");
        }
        MboSetRemote returnMboSetRemote = super.getMboSetRemote();
        String s = stringBuffer.toString();
        System.err.println(s);
        //滤空,没有条件就全部显示
        if (!"'-1'".equals(s)) {
            returnMboSetRemote.setWhere("PERSONID not in (" + s + ")");
        }
        return returnMboSetRemote;
    }


    /**
     * 在dialog里面的确定按钮点击后调用此方法,让选择的人员在子表中显示
     * @return
     * @throws MXException
     * @throws RemoteException
     */
    @Override
    public synchronized int execute() throws MXException, RemoteException {

        Vector selection = getMboSet().getSelection();
        if (selection == null) {
            return 0;
        }
        //主表mbo通过关联关系获取字表
        MboSetRemote carPerson = app.getAppBean().getMbo().getMboSet("APPLICATIONNUMBER");
        for (Object mbo : selection) {
            MboRemote mboRemote = (MboRemote) mbo;
            MboRemote add = carPerson.add();
            add.setValue("PERSONNUMBER", mboRemote.getString("PERSONID"), 11L);
            add.setValue("PERSONNAME", mboRemote.getString("DISPLAYNAME"), 11L);
            //或者主主表编号赋值给子表关联
            add.setValue("APPLICATIONNUMBER", app.getAppBean().getMbo().getString("APPLICATIONNUMBER"), 11L);
        }
        //乘车人数赋值
        Integer applicationnumber = GetEffectiveCount.getCount(app.getAppBean().getMbo(), "APPLICATIONNUMBER");
        app.getAppBean().getMbo().setValue("CAPERSONNUM",applicationnumber+"",11L);
        this.app.getAppBean().refreshTable();
        this.app.getAppBean().reloadTable();
        return super.execute();
    }
}
