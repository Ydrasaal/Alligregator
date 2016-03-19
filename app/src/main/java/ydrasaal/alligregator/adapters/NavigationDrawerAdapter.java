package ydrasaal.alligregator.adapters;

import android.support.v7.util.SortedList;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ydrasaal.alligregator.R;
import ydrasaal.alligregator.data.MenuItem;
import ydrasaal.alligregator.listeners.OnRecyclerItemClickListener;

/**
 * Created by Léo on 12/03/2016.
 *
 * Adapter for the navigationDrawer's Recyclerview. Sort elements with a provided index by this index, else by feed title.
 */
public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.MenuItemViewHolder> {

    private final int TYPE_HEADER = 0;
    private final int TYPE_SECTION = 1;
    private final int TYPE_ITEM = 2;

    private final SortedList<MenuItem> values;
    private OnRecyclerItemClickListener itemClickListener;

    public NavigationDrawerAdapter(OnRecyclerItemClickListener listener) {
        itemClickListener = listener;

        values = new SortedList<>(MenuItem.class, new SortedList.Callback<MenuItem>() {
            @Override
            public int compare(MenuItem o1, MenuItem o2) {
                if (o1.getPosition() != o2.getPosition()) return Double.compare(o1.getPosition(), o2.getPosition());
                return o1.getTitle().compareTo(o2.getTitle());
            }

            @Override
            public void onInserted(int position, int count) {
                notifyItemRangeInserted(position, count);
            }

            @Override
            public void onRemoved(int position, int count) {
                notifyItemRangeRemoved(position, count);
            }

            @Override
            public void onMoved(int fromPosition, int toPosition) {
                notifyItemMoved(fromPosition, toPosition);
            }

            @Override
            public void onChanged(int position, int count) {
                notifyItemRangeChanged(position, count);
            }

            @Override
            public boolean areContentsTheSame(MenuItem oldItem, MenuItem newItem) {
                return oldItem.getUrl().equals(newItem.getUrl());
            }

            @Override
            public boolean areItemsTheSame(MenuItem item1, MenuItem item2) {
                return item1.getTitle().equals(item2.getTitle());
            }
        });
    }

    @Override
    public MenuItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId = R.layout.nav_button_home;
        switch (viewType) {
            case TYPE_HEADER:
                layoutId = R.layout.nav_header_home;
                break;
            case TYPE_SECTION:
                layoutId = R.layout.nav_content_home;
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);

        return new MenuItemViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(MenuItemViewHolder holder, final int position) {
        if (holder.viewType == TYPE_HEADER) return;

        MenuItem item = values.get(position);

        if (holder.viewType == TYPE_ITEM) {
            if (item.getIconId() != 0) holder.button.setCompoundDrawablesWithIntrinsicBounds(item.getIconId(), 0, 0, 0);
            if (itemClickListener != null) {
                holder.button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        itemClickListener.onClick(v, position);
                    }
                });
                holder.button.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        itemClickListener.onLongCLick(v, position);
                        return true;
                    }
                });
            }
        }
        holder.button.setText(item.getTitle());
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) return TYPE_HEADER;
        return values.get(position).isHeader() ? TYPE_SECTION : TYPE_ITEM;
    }

    public void addFixedItem(MenuItem item, int position) {
        item.setPosition(position);
        values.add(item);
        notifyItemInserted(values.size() - 1);
    }

    public void addItem(MenuItem item) {
        values.add(item);
        notifyItemInserted(values.size() - 1);
    }

    public void removeItem(int position) {
        values.removeItemAt(position);
        notifyItemRemoved(position);
    }

    public MenuItem getItem(int position) {
        return values.get(position);
    }

    public class  MenuItemViewHolder extends RecyclerView.ViewHolder {

        public AppCompatButton button;
        public final int viewType;

        public MenuItemViewHolder(View itemView, int viewType) {
            super(itemView);

            button = (AppCompatButton) itemView.findViewById(R.id.entry_title);
            this.viewType = viewType;
        }
    }
}
