package co.com.parsoniisolutions.custombottomsheetbehavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

public class AlertsBehavior<V extends View> extends CoordinatorLayout.Behavior<V> {
    public static final int STATE_EXPANDED = 1;
    public static final int STATE_COLLAPSED = 2;

    public AlertsBehavior() {
    }

    public AlertsBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }



    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, V child, int layoutDirection) {
        Log.d("AlertsBehavior", String.format("onLayoutChild: %s, %d", child, layoutDirection));
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
        params.gravity = Gravity.TOP;
        return super.onLayoutChild(parent, child, layoutDirection);
    }
}
