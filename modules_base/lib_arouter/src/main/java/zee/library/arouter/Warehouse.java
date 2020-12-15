package zee.library.arouter;

import java.util.HashMap;
import java.util.Map;

import zee.library.arouter.template.IRouterGroup;
import zee.library.arouter.template.IService;
import zee.library.router_annotation.model.RouterMeta;

/**
 * 存放数据的地方
 */
public class Warehouse {

    // root 映射表 保存分组信息
    static Map<String, Class<? extends IRouterGroup>> groupsIndex = new HashMap<>();

    // group 映射表 保存组中的所有数据
    static Map<String, RouterMeta> routers = new HashMap<>();

    // group 映射表 保存组中的所有数据
    static Map<Class, IService> services = new HashMap<>();

}