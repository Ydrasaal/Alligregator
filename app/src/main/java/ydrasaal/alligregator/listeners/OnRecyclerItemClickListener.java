package ydrasaal.alligregator.listeners;

import android.view.View;

/**
 * Created by lchazal on 18/03/16.
 *
 * interface grouping OnClick and OnLongClick callbacks
 */
public interface OnRecyclerItemClickListener {
    void onClick(View view, int position);
    void onLongCLick(View view, int position);
}
