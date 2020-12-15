package zee.library.mglide.manager;

import androidx.fragment.app.Fragment;
import zee.library.mglide.RequestManager;

public class SupportRequestManagerFragment extends Fragment {

    // 生命周期回调管理类
    ActivityFragmentLifecycle lifecycle;

    public SupportRequestManagerFragment() {
        lifecycle = new ActivityFragmentLifecycle();
    }

    RequestManager requestManager;

    public RequestManager getRequestManager() {
        return requestManager;
    }

    public void setRequestManager(RequestManager requestManager) {
        this.requestManager = requestManager;
    }

    public ActivityFragmentLifecycle getGlideLifecycle() {
        return lifecycle;
    }

    @Override
    public void onStart() {
        super.onStart();
        lifecycle.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        lifecycle.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        lifecycle.onDestroy();
    }
}
