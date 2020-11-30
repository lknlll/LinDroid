package com.example.lindroidcode.greendaoatoz.common;

import android.content.Context;
import android.util.Log;

import com.example.lindroidcode.greendaoatoz.beans.AudioBean;
import com.example.lindroidcode.greendaoatoz.beans.UserBean;
import com.example.lindroidcode.greendaoatoz.entity.AudioBeanDao;
import com.example.lindroidcode.greendaoatoz.entity.UserBeanDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

public class UserBeanDaoUtils {
    private static final String TAG = UserBeanDaoUtils.class.getSimpleName();
    private DaoManager mManager;

    public UserBeanDaoUtils(Context context){
        mManager = DaoManager.getInstance();
        mManager.init(context);
    }

    /**
     * 完成userBean记录的插入，如果表未创建，先创建UserBean表
     * @param userBean
     * @return
     */
    public boolean insertUserBean(UserBean userBean){
        boolean flag = false;
        flag = mManager.getDaoSession().getUserBeanDao().insert(userBean) == -1 ? false : true;
        Log.i(TAG, "insert UserBean :" + flag + "-->" + userBean.toString());
        return flag;
    }

    public boolean insertAudioBean(AudioBean audioBean){
        return mManager.getDaoSession().getAudioBeanDao().insert(audioBean) == -1;
    }

    /**
     * 插入多条数据，在子线程操作
     * @param userBeanList
     * @return
     */
    public boolean insertMultiUserBean(final List<UserBean> userBeanList) {
        boolean flag = false;
        try {
            mManager.getDaoSession().runInTx(new Runnable() {
                @Override
                public void run() {
                    for (UserBean userBean : userBeanList) {
                        mManager.getDaoSession().insertOrReplace(userBean);
                    }
                }
            });
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 修改一条数据
     * @param userBean
     * @return
     */
    public boolean updateUserBean(UserBean userBean){
        boolean flag = false;
        try {
            mManager.getDaoSession().update(userBean);
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 删除单条记录
     * @param userBean
     * @return
     */
    public boolean deleteUserBean(UserBean userBean){
        boolean flag = false;
        try {
            //按照id删除
            mManager.getDaoSession().delete(userBean);
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    public boolean deleteAudioBean(AudioBean audioBean){
        boolean flag = false;
        try {
            //按照id删除
            mManager.getDaoSession().delete(audioBean);
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 删除所有记录
     * @return
     */
    public boolean deleteAll(){
        boolean flag = false;
        try {
            //按照id删除
            mManager.getDaoSession().deleteAll(UserBean.class);
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 查询所有记录
     * @return
     */
    public List<UserBean> queryAllUserBean(){
        return mManager.getDaoSession().loadAll(UserBean.class);
    }


    public List<AudioBean> queryAllAudioBean(){
        return mManager.getDaoSession().loadAll(AudioBean.class);
    }

    /**
     * 根据主键id查询记录
     * @param key
     * @return
     */
    public UserBean queryUserBeanById(long key){
        return mManager.getDaoSession().load(UserBean.class, key);
    }
    public List<AudioBean> queryAudioBeanByPath(String path){
        return mManager.getDaoSession().getAudioBeanDao().queryBuilder().where(AudioBeanDao.Properties.LocalPath.eq(path)).list();
    }

    /**
     * 使用native sql进行查询操作
     */
    public List<UserBean> queryUserBeanByNativeSql(String sql, String[] conditions){
        return mManager.getDaoSession().queryRaw(UserBean.class, sql, conditions);
    }

    /**
     * 使用queryBuilder进行查询
     * @return
     */
    public List<UserBean> queryUserBeanByQueryBuilder(long id){
        QueryBuilder<UserBean> queryBuilder = mManager.getDaoSession().queryBuilder(UserBean.class);
        return queryBuilder.where(UserBeanDao.Properties._id.eq(id)).list();
    }
}
