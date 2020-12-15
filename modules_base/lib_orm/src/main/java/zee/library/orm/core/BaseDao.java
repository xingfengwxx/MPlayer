package zee.library.orm.core;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import zee.library.orm.annotation.DbField;
import zee.library.orm.annotation.DbTable;
import zee.library.orm.utils.Utils;

/**
 * 对数据库层的访问
 * (基于SQLiteDatabase)
 *
 * @param <T>
 */
public class BaseDao<T> implements IBaseDao<T> {

    // 持有数据库操作的引用
    private SQLiteDatabase mDatabase;

    // 对应的表名
    private String tableName;

    // 持有操作的实体类的class对象引用
    private Class<T> entityClass;

    // 反射后获取到的属性名和属性，key-value保存到Map
    // 这个属性名已经转为表字段名
    private Map<String, Field> fieldCacheMap;

    private boolean isInit = false;

    /*private BaseDao() {

    }*/

    public void init(SQLiteDatabase mDatabase, Class<T> entityClass) {

        this.mDatabase = mDatabase;
        this.entityClass = entityClass;

        if (isInit) {
            return;
        }

        // 分析实体类，创建这张数据表
        if (entityClass != null) {
            DbTable annot = entityClass.getAnnotation(DbTable.class);
            tableName = (annot == null) ? entityClass.getSimpleName() : annot.value();
        }

        // 动态生成这个创建表的sql
        //String sql = "create table if not exists ssss(name TEXT, age INTEGER);";
        String sql = getCreateTableSql();
        Log.i("TAG", "getCreateTableSql:" + sql);
        if (!TextUtils.isEmpty(sql)) {
            // 执行sql语句
            mDatabase.execSQL(sql);
        }

        isInit = true;
    }


    @Override
    public void insert(T entity) {
        //ContentValues contentValues = new ContentValues();
        //contentValues.put("age", 18);

        // 1.获取这个实体类所有的属性(->对应字段)和值
        // 2.组装contentValues
        ContentValues contentValues = new ContentValues();
        for (String key : fieldCacheMap.keySet()) {
            Field field = fieldCacheMap.get(key);
            try {
                Object object = field.get(entity);
                if (object == null) continue;
                //Log.i("TAG", "key=" + key + "/value=" + object.toString());
                contentValues.put(key, object.toString());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        // 3.调用insert方法(SQLiteDatabase提供)，进行数据插入
        // 第二个参数nullColumnHack：指定你某列当你插入数据为null或者长度小于等于0，他不会报错。否则的会抛异常
        //mDatabase.insert(tableName, "name", contentValues);
        mDatabase.insert(tableName, null, contentValues);
    }

    @Override
    public List<T> query(T where) {

        //String selection = "";
        //String[] selectionArgs = new String[]{};
        //Cursor cursor = mDatabase.query(tableName, null, selection, selectionArgs, null, null, null, null);

        //String sql = "select * from " + tableName + " where 1 = 1 and musicName = ?";
        //String[] args = new String[]{"回到过去"};

        ArrayList<String> args = new ArrayList<>();
        String sql = getQuerySql(where, args);
        Cursor cursor = mDatabase.rawQuery(sql, args.toArray(new String[]{}));
        return getResultsFromCursor(cursor);
    }

    @Override
    public void delete(T where) {
//        StringBuilder conditionBuilder = new StringBuilder();
//        ArrayList<String> conditionArgs = new ArrayList<>();
//        conditionBuilder.append("1 = 1 ");
//        if (where != null) {
//            // 从缓存过的fieldCacheMap遍历
//            for (Map.Entry<String, Field> entry : fieldCacheMap.entrySet()) {
//                String key = entry.getKey();
//                Field field = entry.getValue();
//                try {
//                    // 通过反射，获取到传入对象的这条属性的值
//                    Object val = field.get(where);
//                    if (val == null || TextUtils.isEmpty(val.toString())) continue;
//                    conditionBuilder.append("and ").append(key).append(" = ? ");
//                    conditionArgs.add(val.toString());
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                }
//            }
//        }

        Condition condition = new Condition(where);
        mDatabase.delete(tableName, condition.condition, condition.args);
    }

    @Override
    public void update(T entity, T where) {
        ContentValues contentValues = new ContentValues();
        //contentValues.put("musicName", "回到过去XXXX");
        //mDatabase.update(tableName, contentValues, "musicName = ?", new String[]{"回到过去"});

        // 1.组装contentValues
        for (String key : fieldCacheMap.keySet()) {
            Field field = fieldCacheMap.get(key);
            try {
                Object object = field.get(entity);
                if (object == null) continue;
                //Log.i("TAG", "key=" + key + "/value=" + object.toString());
                contentValues.put(key, object.toString());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        // 2.组装条件String，和对应的参数
        StringBuilder conditionBuilder = new StringBuilder();
        ArrayList<String> conditionArgs = new ArrayList<>();
        conditionBuilder.append("1 = 1 ");
        if (where != null) {
            // 从缓存过的fieldCacheMap遍历
            for (Map.Entry<String, Field> entry : fieldCacheMap.entrySet()) {
                String key = entry.getKey();
                Field field = entry.getValue();
                try {
                    // 通过反射，获取到传入对象的这条属性的值
                    Object val = field.get(where);
                    if (val == null || TextUtils.isEmpty(val.toString())) continue;
                    conditionBuilder.append("and ").append(key).append(" = ? ");
                    conditionArgs.add(val.toString());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        mDatabase.update(tableName, contentValues, conditionBuilder.toString(), conditionArgs.toArray(new String[]{}));
    }


    /**
     * 动态创建建表的sql语句
     *
     * @return
     */
    private String getCreateTableSql() {

        fieldCacheMap = new HashMap<>();

        // getFields获取到的只能Public，所以要用getDeclaredFields
        Field[] fields = entityClass.getDeclaredFields();

        if (fields.length == 0) return "";

        //String sql = "create table if not exists ssss(name TEXT, age INTEGER);";
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("create table if not exists ").append(tableName).append("(");
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            // 获取属性名，用来映射表的字段
            String filedName = field.getName();
            DbField dbField = field.getAnnotation(DbField.class);
            if (dbField != null) {
                filedName = dbField.value();
            }
            // 获取这个属性类型
            Class<?> typeClass = field.getType();
            // 获取属性所对应的表字段类型名
            String typeName = Utils.getFieldTypeName(typeClass);
            sqlBuilder.append(filedName).append(" ").append(typeName);

            // 末尾的拼接
            String end = (i == fields.length - 1) ? ");" : ",";
            sqlBuilder.append(end);

            // 添加到缓存，避免下次还需要反射
            // 而且在这里把所有字段的访问权限打开
            field.setAccessible(true);
            fieldCacheMap.put(filedName, field);
        }
        return sqlBuilder.toString();
    }

    /**
     * 动态的创建SQL，并获取对应的占位参数值集合
     *
     * @param where
     * @param args  用来存放?占位的参数
     * @return
     */
    private String getQuerySql(T where, ArrayList<String> args) {

        // 用来分别替换sql语句中的"?"占位符
        // and musicName = ?
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("select * from ").append(tableName).append(" where 1 = 1 ");

        if (where != null) {
            // 从缓存过的fieldCacheMap遍历
            for (Map.Entry<String, Field> entry : fieldCacheMap.entrySet()) {
                String key = entry.getKey();
                Field field = entry.getValue();
                try {
                    // 通过反射，获取到传入对象的这条属性的值
                    Object val = field.get(where);
                    if (val == null || TextUtils.isEmpty(val.toString())) continue;
                    sqlBuilder.append("and ").append(key).append(" = ? ");
                    args.add(val.toString());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            }
        }
        sqlBuilder.append(";");
        return sqlBuilder.toString();
    }

    /**
     * 通过反射创建对象，并读取Cursor进行匹配赋值
     *
     * @param cursor 查询结果返回的Cursor
     * @return
     */
    private List<T> getResultsFromCursor(Cursor cursor) {
        ArrayList<T> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            try {
                // 通过newInstance反射创建对象，这个类必须要有无参构造方法
                // 否则会抛异常：has no zero argument constructor
                T item = entityClass.newInstance();
                // fieldCacheMap，之前就已经保存了这个类对应的表字段和属性信息
                // 通过遍历这个Map，根据字段名从Cursor中取出对应值，赋值给这个字段对应的Filed
                for (Map.Entry<String, Field> entry : fieldCacheMap.entrySet()) {
                    // 获取列名(字段名)
                    String columnName = entry.getKey();
                    // 以列名拿到列名在游标中的位置
                    int columnIndex = cursor.getColumnIndex(columnName);
                    // 获取相应的属性
                    Field field = entry.getValue();
                    if (columnIndex != -1) {
                        // 获取某一列的值
                        Object val = Utils.getColumnValue(columnIndex, cursor);
                        if (val != null) {
                            field.set(item, val);
                        }
                    }
                }
                list.add(item);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        cursor.close();
        return list;
    }

    private class Condition {

        String condition;
        String[] args;

        public Condition(Object where) {
            StringBuilder conditionBuilder = new StringBuilder();
            ArrayList<String> conditionArgs = new ArrayList<>();
            conditionBuilder.append("1 = 1 ");
            if (where != null) {
                // 从缓存过的fieldCacheMap遍历
                for (Map.Entry<String, Field> entry : fieldCacheMap.entrySet()) {
                    String key = entry.getKey();
                    Field field = entry.getValue();
                    try {
                        // 通过反射，获取到传入对象的这条属性的值
                        Object val = field.get(where);
                        if (val == null || TextUtils.isEmpty(val.toString())) continue;
                        conditionBuilder.append("and ").append(key).append(" = ? ");
                        conditionArgs.add(val.toString());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
            condition = conditionBuilder.toString();
            args = conditionArgs.toArray(new String[]{});
        }

    }

    /**
     * 根据实体对象创建ContentValues
     *
     * @param entity
     * @return
     */
    private ContentValues createContentValues(Object entity) {
        ContentValues contentValues = new ContentValues();
        for (String key : fieldCacheMap.keySet()) {
            Field field = fieldCacheMap.get(key);
            if (field != null) {
                try {
                    Object val = field.get(entity);
                    if (val == null) continue;
                    contentValues.put(key, val.toString());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return contentValues;
    }

}