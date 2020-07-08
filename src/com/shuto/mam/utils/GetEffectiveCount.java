package com.shuto.mam.utils;


import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;

import java.rmi.RemoteException;

/**
 * 获取子表内有效数量的方法抽取
 * @author DuanPX
 * @Version 1.0
 * @Date 2020/6/18 9:05
 */
public class GetEffectiveCount {
    /**
     * 获取有效数量
     * @param mbo
     * @param relationshipName
     * @return
     * @throws RemoteException
     * @throws MXException
     */
    public static Integer getCount(MboRemote mbo, String relationshipName) throws RemoteException, MXException {
        //根据关系名拿到MBOSET
        MboSetRemote mboSet = mbo.getMboSet(relationshipName);
        //设置初始值为0
        Integer count = 0;
        //获取列表中没有删除的数量
        //如果set不为空
        if(!mboSet.isEmpty()) {
            MboRemote mboRemote = null;
            int i = 0;
            //迭代set
            while( (mboRemote = mboSet.getMbo(i++)) != null) {
                //如果数据不是被删除状态 数量++
                if (!mboRemote.toBeDeleted()) {
                    count++;
                }
            }
        }
        //返回集合中未被删除项的数量
        return count;
    }
}
