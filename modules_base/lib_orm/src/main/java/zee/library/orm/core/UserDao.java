package zee.library.orm.core;

import android.util.Log;

import java.util.List;

public class UserDao extends BaseDao<User> {

    @Override
    public void insert(User entity) {
        // 查询tb_user;
        List<User> list = query(new User());
        User where = null;
        for (User user : list) {
            where = new User();
            where.setId(user.getId());
            user.setStatus(0);
            Log.e("TAG", "用户" + user.getName() + "更改为未登录状态");
            update(user, where);
        }
        Log.e("TAG", "用户" + entity.getName() + "登录成功");
        entity.setStatus(1);
        super.insert(entity);
    }

    // 得到当前登录的User
    public User getCurrentUser() {
        User user = new User();
        user.setStatus(1);
        List<User> list = query(user);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }
}