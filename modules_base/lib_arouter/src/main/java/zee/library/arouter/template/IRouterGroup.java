package zee.library.arouter.template;

import java.util.Map;

import zee.library.router_annotation.model.RouterMeta;

public interface IRouterGroup {

    void loadInfo(Map<String, RouterMeta> atlas);
}
