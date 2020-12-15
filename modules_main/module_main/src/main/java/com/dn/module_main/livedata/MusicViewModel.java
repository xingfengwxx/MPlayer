package com.dn.module_main.livedata;

import com.dn.domain.Music;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import zee.library.orm.DBManager;

/**
 * 处理数据的变化跟视图
 */
public class MusicViewModel extends ViewModel {

    // 定义一个对象，相当于用来存放数据的仓库
    private static MutableLiveData<List<Music>> liveData;

    /**
     * 用于获取数据
     * 并保存到LiveData中(可以理解成数据仓库)
     *
     * @return
     */
    public MutableLiveData<List<Music>> getMusics() {
        if (liveData == null) {
            List<Music> musics = loadMusics();
            liveData = new MutableLiveData<>();
            //liveData.setValue(musics);
            // 把数据存放到仓库
            // post 和 set 是跟同步异步区别
            liveData.postValue(musics);
        }
        return liveData;
    }

    /**
     * 加载数据
     * 可以从本地或者通过服务端请求获得
     *
     * @return
     */
    private List<Music> loadMusics() {
        //List<Music> musics = DBManager.getInstance().getBaseDao(Music.class).query(null);
        List<Music> musics = new ArrayList<>();
        musics.add(new Music("回到过去", "周杰伦", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1606926129166&di=f08418580e24fee51077e6a4f5279ecf&imgtype=0&src=http%3A%2F%2Fwx4.sinaimg.cn%2Fmw690%2F9d6a5d79gy1gkemogt3gaj20uj0xc13i.jpg"));
        musics.add(new Music("晴天", "周杰伦", "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2476348865,1314216596&fm=26&gp=0.jpg"));
        musics.add(new Music("双节棍", "周杰伦", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1607770631659&di=ffbe96adf1a5d4b430535dc0d72b7528&imgtype=0&src=http%3A%2F%2Fimg.mp.itc.cn%2Fupload%2F20170125%2F968756bee00945dfb4240d11302e0a82_th.jpg"));
        musics.add(new Music("霍元甲", "周杰伦", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1607770631657&di=a78d5069ad702bbe5f2226e2db253d63&imgtype=0&src=http%3A%2F%2Fhbimg.b0.upaiyun.com%2F3b4f70e339a937a9b98475f308d677e1816a41a8277e8-BpfUSV_fw658"));
        musics.add(new Music("七里香", "周杰伦", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1607770631658&di=a9b5ebddcdced0e7756d9f60d4fada38&imgtype=0&src=http%3A%2F%2Fc.hiphotos.baidu.com%2Fbaike%2Fpic%2Fitem%2Fa6efce1b9d16fdfab5809e8cbc8f8c5495ee7be7.jpg"));
        musics.add(new Music("一路向北", "周杰伦", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1607770631654&di=1c216e4f940a862433801f4c3fed6d02&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn%2F20171112%2Fedeb-fynstfh6327729.jpg"));
        return musics;
    }

    /**
     * 修改某条数据
     *
     * @param index
     * @param music
     */
    public void changeItem(int index, Music music) {
        List<Music> musics = liveData.getValue();
        musics.set(index, music);
        liveData.postValue(musics);
    }

}
