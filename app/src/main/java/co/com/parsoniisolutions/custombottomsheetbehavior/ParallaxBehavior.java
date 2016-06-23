package co.com.parsoniisolutions.custombottomsheetbehavior;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class ParallaxBehavior<V extends View> extends CoordinatorLayout.Behavior<V> {
    @IntDef({STATE_FULL, STATE_COLLAPSED})
    @Retention(RetentionPolicy.SOURCE)
    private @interface State {}

    private static final int STATE_FULL = 1;
    private static final int STATE_COLLAPSED = 2;

    @ParallaxBehavior.State
    private int mState;

    private V mChild;

    public ParallaxBehavior() {
    }

    public ParallaxBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.d("ParallaxBehavior", "Constructing");
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, final V child, View dependency) {
        return BehaviorHelper.hasBehavior(dependency, BottomSheetBehaviorGoogleMapsLike.class);
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, V child, View dependency) {
        if (mChild == null) {
            initValues(child);
        }
        return false;
    }

    private void initValues(V child) {
        mChild = child;
        mChild.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN && mState == STATE_FULL) {

                }
                return false;
            }
        });
    }

}
