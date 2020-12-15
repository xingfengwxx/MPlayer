package zee.library.arouter.template;

import java.util.Map;

public interface IRouterRoot {

    /**
     * @param routes input
     */
    void loadInfo(Map<String, Class<? extends IRouterGroup>> routes);
}
