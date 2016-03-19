package ydrasaal.alligregator.views;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by lchazal on 18/03/16.
 *
 * LayoutManager catching IOOBEExceptions randomly thrown by RecyclerViews while calling notifyXXX() methods.
 *
 */
public class RecyclerLayoutManager extends LinearLayoutManager {

    public RecyclerLayoutManager(Context context) {
        super(context);
    }

    public RecyclerLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public RecyclerLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        try {
            super.onLayoutChildren(recycler, state);
        } catch (IndexOutOfBoundsException e) {
            Log.e("RecyclerLayoutManager", "met a IOOBE in RecyclerView");
        }
    }
}