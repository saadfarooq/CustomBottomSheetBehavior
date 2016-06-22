package co.com.parsoniisolutions.custombottomsheetbehavior;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class ParallaxBehavior<V extends View> extends CoordinatorLayout.Behavior<V> {
    public ParallaxBehavior() {
    }

    public ParallaxBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.d("ParallaxBehavior", "Constructing");
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, final V child, View dependency) {
//        Log.d("ParallaxBehavior", String.format("layoutDependsOn: %s\n%b", dependency, dependency instanceof NestedScrollingChild));


        if (dependency.getLayoutParams() instanceof CoordinatorLayout.LayoutParams &&
                ((CoordinatorLayout.LayoutParams)dependency.getLayoutParams()).getBehavior() instanceof BottomSheetBehaviorGoogleMapsLike) {
            BottomSheetBehaviorGoogleMapsLike.from(dependency).addBottomSheetCallback(new BottomSheetBehaviorGoogleMapsLike.BottomSheetCallback() {
                @Override
                public void onStateChanged(@NonNull View bottomSheet, @BottomSheetBehaviorGoogleMapsLike.State int newState) {
                    Log.d("ParallaxBehavior", String.format("onStateChanged: %d", newState));
                }

                @Override
                public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                    Log.d("ParallaxBehavior", String.format("onSlide: %f", slideOffset));
                    child.setTranslationY(-child.getHeight() * slideOffset * 0.5f);
                }
            });
        }
        return false;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, V child, View dependency) {
        Log.d("ParallaxBehavior", String.format("onDependentViewChanged: %s", dependency));
        return super.onDependentViewChanged(parent, child, dependency);
    }

}
