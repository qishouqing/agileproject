package com.qishouqing.frame.dao;

import android.content.Context;

import org.greenrobot.greendao.AbstractDao;

import java.util.List;


public class DaoUtils<T> {
    private static final String TAG = DaoUtils.class.getSimpleName();
    private DaoManager mManager;

    public DaoUtils(Context context) {
        mManager = DaoManager.getInstance();
        mManager.init(context);
    }

    public <T> T getDao(Class<? extends Object> entityClass) {
        return (T) mManager.getDaoSession().getDao(entityClass);
    }

    /**
     * 插入一条或多条
     *
     * @param object
     * @return
     */
    public void insert(Object object) {
        Class cls;
        if (object instanceof List) {
            List listObject = (List) object;
            if (listObject.isEmpty()) {
                throw new IllegalArgumentException("List Object Not Allow Empty!");
            }
            cls = listObject.get(0).getClass();
            ((AbstractDao<Object, String>) getDao(cls)).insertInTx(listObject);
        } else {
            cls = object.getClass();
            ((AbstractDao<Object, String>) getDao(cls)).insert(object);
        }
    }

    /**
     * 插入或修改一条或多条
     *
     * @param object
     */

    public void insertOrUpdate(Object object) {
        Class cls;
        if (object instanceof List) {
            List listObject = (List) object;
            if (listObject.isEmpty()) {
                throw new IllegalArgumentException("List Object Not Allow Empty!");
            }
            cls = listObject.get(0).getClass();
            ((AbstractDao<Object, String>) getDao(cls)).insertOrReplaceInTx(listObject);
        } else {
            cls = object.getClass();
            ((AbstractDao<Object, String>) getDao(cls)).insertOrReplace(object);
        }
    }


    /**
     * 修改一条数据
     *
     * @return
     */
    public void update(Object object) {
        Class cls;
        if (object instanceof List) {
            List listObject = (List) object;
            if (listObject.isEmpty()) {
                throw new IllegalArgumentException("List Object Not Allow Empty!");
            }
            cls = listObject.get(0).getClass();
            ((AbstractDao<Object, String>) getDao(cls)).updateInTx(listObject);
        } else {
            cls = object.getClass();
            ((AbstractDao<Object, String>) getDao(cls)).update(object);
        }
    }


    /**
     * 删除一条记录
     *
     * @param object
     */
    public void delete(Object object) {
        Class cls;
        if (object instanceof List) {
            List listObject = (List) object;
            if (listObject.isEmpty()) {
                throw new IllegalArgumentException("List Object Not Allow Empty!");
            }
            cls = listObject.get(0).getClass();
            ((AbstractDao<Object, String>) getDao(cls)).deleteInTx(listObject);
        } else {
            cls = object.getClass();
            ((AbstractDao<Object, String>) getDao(cls)).delete(object);
        }
    }


    /**
     * 删除所有记录
     *
     * @return
     */
    public void deleteAll(Class<?> cls) {
        ((AbstractDao<Object, String>) getDao(cls)).deleteAll();
    }


    /**
     * 查询所有记录
     *
     * @return
     */
    public <T> List<T> quaryAll(Class<T> cls) {
        return ((AbstractDao<T, String>) getDao(cls)).loadAll();
    }


    /**
     * 根据主键id查询记录fitsSystemWindows
     *
     * @param key
     * @return
     */
    public <T> T quaryByKey(Class<T> cls, String key) {
        return ((AbstractDao<T, String>) getDao(cls)).load(key);
    }

    /**
     * 使用native sql进行查询操作
     */
    public <T> List<T> queryByNativeSql(Class<T> cls, String where, String... selectionArg) {
        return ((AbstractDao<T, String>) getDao(cls)).queryRaw(where, selectionArg);
    }



}
