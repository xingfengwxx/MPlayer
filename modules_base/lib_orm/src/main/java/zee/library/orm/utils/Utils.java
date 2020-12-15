package zee.library.orm.utils;

import android.database.Cursor;

public class Utils {

    /**
     * 根据类型获取sql中对应的字段类型
     *
     * @param clazz
     * @return
     */
    public static String getFieldTypeName(Class<?> clazz) {
        if (String.class == clazz) {
            return "TEXT";
        } else if (Integer.class == clazz) {
            return "INTEGER";
        } else if (Long.class == clazz) {
            return "BIGINT";
        } else if (String.class == clazz) {
            return "TEXT";
        } else if (Double.class == clazz) {
            return "DOUBLE";
        } else if (byte[].class == clazz) {
            return "BOLB";
        }
        return "";
    }

    /**
     * 获取游标对象某列的值
     *
     * @param columnIndex
     * @param cursor
     * @return
     */
    public static Object getColumnValue(int columnIndex, Cursor cursor) {
        switch (cursor.getType(columnIndex)) {
            case Cursor.FIELD_TYPE_NULL:
                return null;
            case Cursor.FIELD_TYPE_INTEGER:
                return cursor.getLong(columnIndex);
            case Cursor.FIELD_TYPE_FLOAT:
                return cursor.getDouble(columnIndex);
            case Cursor.FIELD_TYPE_STRING:
                return cursor.getString(columnIndex);
            case Cursor.FIELD_TYPE_BLOB:
                return cursor.getBlob(columnIndex);
        }
        return null;
    }

}