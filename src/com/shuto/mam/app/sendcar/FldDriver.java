package com.shuto.mam.app.sendcar;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

import java.rmi.RemoteException;

/**
 * @PackageClassname com.shuto.mam.app.sendcar.FldDriver
 * @Author: luxq
 * @Date: 2020/6/29 22:06:41
 * @Version: v1.0
 * @Description:
 **/
public class FldDriver extends MAXTableDomain {

    public FldDriver(MboValue mbv) {
        super(mbv);
        //获得当前字段名
        String thisAtt = getMboValue().getName();
        //设置要获取数据表的关系
        //获取数据表名|关系(一般不会系都用1=1,因为gitList设置关系更灵活)
        this.setRelationship("DRIVERLEDGER", "1=1");
        //目标字段名变量
        String[] strTo = {thisAtt};
        //获取值 字段名变更
        String[] strFrom = {"DRIVERNUMBER"};
        //此方法名将获取到的值设置到目标字段 中
        setLookupKeyMapInOrder(strTo, strFrom);
    }

    /**
     * 过滤数据
     *
     * @return
     * @throws MXException
     * @throws RemoteException
     */
    @Override
    public MboSetRemote getList() throws MXException, RemoteException {
        //加where条件语句
        setListCriteria("STATUS='待派出'");
        return super.getList();
    }
}
