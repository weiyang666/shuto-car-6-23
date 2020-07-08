package com.shuto.mam.app.sendcar;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

import java.rmi.RemoteException;

/**
 * @PackageClassname com.shuto.mam.app.sendcar.FldLicensePlate
 * @Author: luxq
 * @Date: 2020/6/29 14:26:38
 * @Version: v1.0
 * @Description:
 **/
public class FldLicensePlate extends MAXTableDomain {


    public FldLicensePlate(MboValue mbv) {
        super(mbv);
        //获得当前字段名
        String thisAtt = getMboValue().getName();
        //设置要获取数据表的关系
        //获取数据表名|关系(一般不会系都用1=1,因为gitList设置关系更灵活)
        this.setRelationship("CARPARAMETER", "1=1");
        //目标字段名变量
        String[] strTo = {thisAtt};
        //获取值 字段名变更
        String[] strFrom = {"NUMBERPLATE"};
        //此方法名将获取到的值设置到目标字段 中
        setLookupKeyMapInOrder(strTo, strFrom);
    }


    /**
     * 过滤
     *以车辆类型(轿车,吉普车...)进行过滤
     * @return
     * @throws MXException
     * @throws RemoteException
     */
    @Override
    public MboSetRemote getList() throws MXException, RemoteException {
        //获取当前车辆类型
        String cartype = mboValue.getMbo().getString("CARTYPE");
        if (cartype.equals("")){
            setListCriteria("CARSTATUS='使用中'");
        }else {
            //加where条件语句
            setListCriteria("CARSIZE='" + cartype + "' and CARSTATUS='使用中'");
            //如果是使用中则可以选择
        }
        return super.getList();
    }
}
