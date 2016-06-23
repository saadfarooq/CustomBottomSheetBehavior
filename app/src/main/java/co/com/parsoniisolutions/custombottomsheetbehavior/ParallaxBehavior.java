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
import java.util.ArrayList;
import java.util.List;

public class ParallaxBehavior<V extends View> extends CoordinatorLayout.Behavior<V> {
    @IntDef({STATE_FULL, STATE_COLLAPSED})
    @Retention(RetentionPolicy.SOURCE)
    private @interface State {}

    private static final int STATE_FULL = 1;
    private static final int STATE_COLLAPSED = 2;

    private final List<StateChangeListener> listeners = new ArrayList<>(2); // init with 2, we're unlikely to have more

    @ParallaxBehavior.State
    private int mState = STATE_FULL;

    private V mChild;

    public ParallaxBehavior() {}

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
            initValues(child, dependency);
        }
        return false;
    }

    private void initValues(V child, View dependency) {
        mChild = child;
        mChild.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN && mState == STATE_FULL) {
                    notifyListenersToChangeState(view, STATE_COLLAPSED);
                    mState = STATE_COLLAPSED;
                }
                return false;
            }
        });

        mChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mState == STATE_COLLAPSED) {
                    notifyListenersToChangeState(v, STATE_FULL);
                    mState = STATE_FULL;
                }
            }
        });

        if (BehaviorHelper.hasBehavior(dependency, BottomSheetBehaviorGoogleMapsLike.class)) {
            BottomSheetBehaviorGoogleMapsLike.from(dependency).addBottomSheetCallback(new BottomSheetBehaviorGoogleMapsLike.BottomSheetCallback() {
                @Override
                public void onStateChanged(@NonNull View bottomSheet, @BottomSheetBehaviorGoogleMapsLike.State int newState) {

                }

                @Override
                public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                    mChild.setTranslationY(-mChild.getHeight() * slideOffset * 0.6f);
                }
            });
        }
    }

    public void onStateChangeListener(StateChangeListener listener) {
        listeners.add(listener);
    }

    private void notifyListenersToChangeState(View view, @State int targetState) {
        for (StateChangeListener listener : listeners) {
            listener.onStateChanged(view, targetState);
        }
    }

    public interface StateChangeListener {
        void onStateChanged(View view, @State int newState);
    }

}
