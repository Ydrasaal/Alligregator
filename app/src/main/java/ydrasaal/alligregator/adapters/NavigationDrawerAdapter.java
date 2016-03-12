package ydrasaal.alligregator.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ydrasaal.alligregator.FeedListActivity;
import ydrasaal.alligregator.R;
import ydrasaal.alligregator.data.MenuItem;

/**
 * Created by Léo on 12/03/2016.
 */
public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.MenuItemViewHolder> {

    private final int TYPE_SEPARATOR = 0;
    private final int TYPE_ITEM = 1;

    private final List<MenuItem>    values;
    private OnClickListener         itemClickListener;

    public interface OnClickListener {
        void onClick(View view, int position);
    }

    public NavigationDrawerAdapter(List<MenuItem> list, OnClickListener listener) {
        values = list;
        itemClickListener = listener;
    }

    @Override
    public MenuItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(viewType == TYPE_ITEM ? R.layout.viewholder_menu_item : R.layout.viewholder_menu_separator,
                        parent, false);
        return new MenuItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MenuItemViewHolder holder, final int position) {
        holder.title.setText("MenuItem n°" + position);
        if (!values.get(position).isSeparator()) {
            holder.title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (itemClickListener != null) itemClickListener.onClick(view, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    @Override
    public int getItemViewType(int position) {
        return values.get(position).isSeparator() ? TYPE_SEPARATOR : TYPE_ITEM;
    }

    public class  MenuItemViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        public MenuItemViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title);
        }
    }
}
