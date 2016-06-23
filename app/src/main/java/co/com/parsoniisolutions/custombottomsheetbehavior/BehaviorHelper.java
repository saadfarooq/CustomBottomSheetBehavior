package co.com.parsoniisolutions.custombottomsheetbehavior;

import android.support.design.widget.CoordinatorLayout;
import android.view.View;

public class BehaviorHelper {
    public static <V extends View> boolean hasBehavior(V child, Class<? extends CoordinatorLayout.Behavior> behaviorClass) {
        if (child.getLayoutParams() instanceof CoordinatorLayout.LayoutParams) {
            if (((CoordinatorLayout.LayoutParams) child.getLayoutParams()).getBehavior().getClass().equals(behaviorClass)) {
                return true;
            }
        }
        return false;
    }
}
