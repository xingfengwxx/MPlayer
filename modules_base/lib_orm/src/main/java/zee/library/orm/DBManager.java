package zee.library.orm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;
import java.util.Map;

import zee.library.orm.annotation.DbTable;
import zee.library.orm.core.BaseDao;

public class DBManager {

    private static DBManager instance;

    private static final int SCHEMA_VERSION = 1;

    private SQLiteDatabase mDatabase;

    private Map<Class<?>, BaseDao> daoMap = new HashMap<>();

    public static DBManager getInstance() {
        if (instance == null) {
            synchronized (DBManager.class) {
                if (instance == null) {
                    instance = new DBManager();
                }
            }
        }
        return instance;
    }

    /*private DaoMaster() {
        String dbPath = "data/data/com.dn.mplayer/databases/mplayer.db";
        mDatabase = SQLiteDatabase.openOrCreateDatabase(dbPath, null);
    }*/

    public void init(Context context, String dbName) {
        // 1.获取OpenHelper
        MDbOpenHelper openHelper = new MDbOpenHelper(context, dbName);
        // 2.创建数据库，获取SQLiteDatabase引用
        mDatabase = openHelper.getWritableDatabase();
    }

    /**
     * 根据指定的实体类型的class对象，获取对应的BaseDao
     *
     * @param entityClass
     * @param <T>
     * @return
     */
    public <T> BaseDao<T> getBaseDao(Class<T> entityClass) {
        try {
            if (daoMap.get(entityClass) != null) {
                return daoMap.get(entityClass);
            }
            BaseDao<T> baseDao = BaseDao.class.newInstance();
            baseDao.init(mDatabase, entityClass);
            daoMap.put(entityClass, baseDao);
            return baseDao;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T extends BaseDao<M>, M> T getBaseDao(Class<T> daoClass, Class<M> entityClass) {
        BaseDao baseDao = null;
        try {
            baseDao = daoClass.newInstance();
            baseDao.init(mDatabase, entityClass);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return (T) baseDao;
    }

    /**
     * OpenHelper继承 SQLiteOpenHelper
     * 对数据库创建，操作的帮助类
     */
    private static class MDbOpenHelper extends SQLiteOpenHelper {

        MDbOpenHelper(Context context, String name) {
            super(context, name, null, SCHEMA_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // 数据库创建后调用
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // 有版本更新后，调用
        }
    }

    /**
     * 删除某张表
     *
     * @param entityClass
     */
    public void dropTable(Class<?> entityClass) {
        try {
            if (entityClass != null) {
                DbTable annot = entityClass.getAnnotation(DbTable.class);
                String tableName = (annot == null) ? entityClass.getSimpleName() : annot.value();
                String sql = "drop table " + tableName + " ;";
                mDatabase.execSQL(sql);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}