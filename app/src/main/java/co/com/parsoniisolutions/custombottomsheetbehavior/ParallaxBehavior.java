package co.com.parsoniisolutions.custombottomsheetbehavior;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class ParallaxBehavior<V extends View> extends CoordinatorLayout.Behavior<V> {
    private BottomSheetBehaviorGoogleMapsLike<View> bottomSheetsBehavior;
    private AlertsBehavior<View> alertsBehavior;

    @IntDef({STATE_GO_FULL, STATE_COLLAPSE})
    @Retention(RetentionPolicy.SOURCE)
    @interface State {}

    static final int STATE_GO_FULL = 1;
    static final int STATE_COLLAPSE = 2;

    @ParallaxBehavior.State
    private int mState = STATE_GO_FULL;

    private V mChild;

    public ParallaxBehavior() {}

    public ParallaxBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, final V child, View dependency) {
        return BehaviorHelper.hasBehavior(dependency, BottomSheetBehaviorGoogleMapsLike.class) || BehaviorHelper.hasBehavior(dependency, AlertsBehavior.class);
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, V child, View dependency) {
        if (mChild == null) {
            initValues(child, dependency);
        }

        if (BehaviorHelper.hasBehavior(dependency, BottomSheetBehaviorGoogleMapsLike.class)) {
            bottomSheetsBehavior = BottomSheetBehaviorGoogleMapsLike.from(dependency);
            bottomSheetsBehavior.addBottomSheetCallback(new BottomSheetBehaviorGoogleMapsLike.BottomSheetCallback() {
                @Override
                public void onStateChanged(@NonNull View bottomSheet, @BottomSheetBehaviorGoogleMapsLike.State int newState) {

                }

                @Override
                public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                    mChild.setTranslationY(-mChild.getHeight() * slideOffset * 0.6f);
                }
            });
        }

        if (BehaviorHelper.hasBehavior(dependency, AlertsBehavior.class)) {
            alertsBehavior = AlertsBehavior.from(dependency);
        }


        return false;
    }

    private void initValues(V child, View dependency) {
        mChild = child;
        mChild.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN && mState == STATE_GO_FULL) {
                    bottomSheetsBehavior.setState(BottomSheetBehaviorGoogleMapsLike.STATE_COLLAPSED);
                    alertsBehavior.setState(AlertsBehavior.STATE_COLLAPSED);
                    mState = STATE_COLLAPSE;
                    return true;
                }
                return false;
            }
        });

        mChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mState == STATE_COLLAPSE) {
                    bottomSheetsBehavior.setLastKnownState();
                    alertsBehavior.setState(AlertsBehavior.STATE_EXPANDED);
                    mState = STATE_GO_FULL;
                }
            }
        });
    }

    @SuppressWarnings("unchecked")
    public static <V extends View> ParallaxBehavior<V> from(V view) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (!(params instanceof CoordinatorLayout.LayoutParams)) {
            throw new IllegalArgumentException("The view is not a child of CoordinatorLayout");
        }
        CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) params)
                .getBehavior();
        if (!(behavior instanceof ParallaxBehavior)) {
            throw new IllegalArgumentException(
                    "The view is not associated with BottomSheetBehaviorGoogleMapsLike");
        }
        return (ParallaxBehavior<V>) behavior;
    }

}
