package com.example.aaron.greendaoexample.db;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import com.example.aaron.greendaoexample.db.User;
import com.example.aaron.greendaoexample.db.PhoneList;

import com.example.aaron.greendaoexample.db.UserDao;
import com.example.aaron.greendaoexample.db.PhoneListDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig userDaoConfig;
    private final DaoConfig phoneListDaoConfig;

    private final UserDao userDao;
    private final PhoneListDao phoneListDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        userDaoConfig = daoConfigMap.get(UserDao.class).clone();
        userDaoConfig.initIdentityScope(type);

        phoneListDaoConfig = daoConfigMap.get(PhoneListDao.class).clone();
        phoneListDaoConfig.initIdentityScope(type);

        userDao = new UserDao(userDaoConfig, this);
        phoneListDao = new PhoneListDao(phoneListDaoConfig, this);

        registerDao(User.class, userDao);
        registerDao(PhoneList.class, phoneListDao);
    }
    
    public void clear() {
        userDaoConfig.getIdentityScope().clear();
        phoneListDaoConfig.getIdentityScope().clear();
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public PhoneListDao getPhoneListDao() {
        return phoneListDao;
    }

}
