package co.com.parsoniisolutions.custombottomsheetbehavior;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class AlertsBehavior<V extends View> extends CoordinatorLayout.Behavior<V> {
    @IntDef({STATE_COLLAPSED, STATE_EXPANDED})
    @Retention(RetentionPolicy.SOURCE)
    public @interface State{}

    public static final int STATE_EXPANDED = 1;
    public static final int STATE_COLLAPSED = 2;
    private V mChild;
    @State
    private int mState = STATE_EXPANDED;

    public AlertsBehavior() {}

    public AlertsBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, V child, int layoutDirection) {
        if (mChild == null) {
            mChild  = child;
        }
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
        params.gravity = Gravity.TOP;
        return false;
    }

    @SuppressWarnings("unchecked")
    public static <V extends View> AlertsBehavior<V> from(V view) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (!(params instanceof CoordinatorLayout.LayoutParams)) {
            throw new IllegalArgumentException("The view is not a child of CoordinatorLayout");
        }
        CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) params)
                .getBehavior();
        if (!(behavior instanceof AlertsBehavior)) {
            throw new IllegalArgumentException(
                    "The view is not associated with BottomSheetBehaviorGoogleMapsLike");
        }
        return (AlertsBehavior<V>) behavior;
    }

    public void setState(@State int newState) {
        if (newState == STATE_EXPANDED && mState == STATE_COLLAPSED) {
            mChild.animate().translationY(0).start();
            mState = STATE_EXPANDED;
        } else if (newState == STATE_COLLAPSED && mState == STATE_EXPANDED) {
            mChild.animate().translationY(-mChild.getHeight()).start();
            mState = STATE_COLLAPSED;
        }
    }
}
