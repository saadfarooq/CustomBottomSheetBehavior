package co.com.parsoniisolutions.custombottomsheetbehavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

public class AlertsBehavior<V extends View> extends CoordinatorLayout.Behavior<V> {
    public static final int STATE_EXPANDED = 1;
    public static final int STATE_COLLAPSED = 2;
    private V mChild;
    private int state;

    public AlertsBehavior() {}

    public AlertsBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, V child, int layoutDirection) {
        mChild  = child;
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

    public void setState(int state) {
        mChild.setVisibility(mChild.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
    }
}
