package com.dn.mplayer;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;
import zee.library.arouter.DNRouter;
import zee.library.router_annotation.Router;

@Router(path = "/app/WelcomeActivity")
public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                skip();
            }
        }, 2000);
    }

    private void skip() {
        DNRouter.getInstance().build("/login/LoginActivity").navigation(this);
        //DNRouter.getInstance().build("/main/MainActivity").navigation(this);
        finish();
        overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
    }

    /**
     * 动态申请权限
     * 6.0以上系统需要
     */
    /*private void applyPermission() {
        Disposable d = new RxPermissions(this).request("android.permission.MOUNT_UNMOUNT_FILESYSTEMS",
                "android.permission.WRITE_EXTERNAL_STORAGE")
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            Log.i("TAG", "READ_EXTERNAL_STORAGE/READ_EXTERNAL_STORAGE PERMISSION OPEN");
                        }
                    }
                });
    }*/

}