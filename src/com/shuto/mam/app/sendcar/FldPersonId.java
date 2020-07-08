package com.shuto.mam.app.sendcar;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

import java.rmi.RemoteException;

/**
 * @PackageClassname com.shuto.mam.app.sendcar.FldPersonId
 * @Author: luxq
 * @Date: 2020/6/30 16:01:48
 * @Version: v1.0
 * @Description: 从人员表选择用车人
 **/
public class FldPersonId extends MAXTableDomain {

    public FldPersonId(MboValue mbv) {
        super(mbv);
        //获得当前字段名
        String thisAtt = getMboValue().getName();
        //设置要获取数据表的关系
        //获取数据表名|关系(一般不会系都用1=1,因为gitList设置关系更灵活)
        this.setRelationship("PERSON", "1=1");
        //目标字段名变量
        String[] strTo = {thisAtt};
        //获取值 字段名变更
        String[] strFrom = {"PERSONID"};
        //此方法名将获取到的值设置到目标字段 中
        setLookupKeyMapInOrder(strTo,strFrom);
    }


    @Override
    public MboSetRemote getList() throws MXException, RemoteException {
        String siteid = getMboValue().getMbo().getString("SITEID");
        System.out.println(siteid);
        return super.getList();
    }
}
