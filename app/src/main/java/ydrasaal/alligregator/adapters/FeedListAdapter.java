package ydrasaal.alligregator.adapters;

import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ydrasaal.alligregator.R;
import ydrasaal.alligregator.data.EntryItem;
import ydrasaal.alligregator.data.LoadEntry;
import ydrasaal.alligregator.listeners.OnRecyclerItemClickListener;

/**
 * Created by lchazal on 18/03/16.
 *
 * Adapter for the main entry lists. Uses a SortedList ordering entries by creation date.
 */
public class FeedListAdapter
        extends RecyclerView.Adapter<FeedListAdapter.ViewHolder> {

    private String                          currentDisplay = "";
    private List<EntryItem>                 hiddenValues;

    private final SortedList<EntryItem>     values;
    private OnRecyclerItemClickListener     listener;

    public FeedListAdapter(OnRecyclerItemClickListener listener) {
        this.listener = listener;
        hiddenValues = new ArrayList<>();

        values = new SortedList<>(EntryItem.class, new SortedList.Callback<EntryItem>() {
            @Override
            public int compare(EntryItem o2, EntryItem o1) {
                return o1.getEntry().getPublishedDate().compareTo(o2.getEntry().getPublishedDate());
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
            public boolean areContentsTheSame(EntryItem oldItem, EntryItem newItem) {
                return oldItem.getEntry().getLink().equals(newItem.getEntry().getLink());
            }

            @Override
            public boolean areItemsTheSame(EntryItem item1, EntryItem item2) {
                return item1.getEntry().getTitle().equals(item2.getEntry().getTitle());
            }
        });
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewholder_entry, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = values.get(position).getEntry();
        holder.title.setText(Html.fromHtml(values.get(position).getEntry().getTitle()));
        holder.feed.setText(Html.fromHtml(values.get(position).getFeedName()));

        if (listener != null) {
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(v, position);
                }
            });
        }
    }



    @Override
    public int getItemCount() {
        return values.size();
    }

    public void addItem(EntryItem item) {
        values.add(item);
        notifyItemInserted(values.size() - 1);
    }

    public EntryItem getItem(int position) {
        return values.get(position);
    }

    public void filterByUrl(String url) {
        restoreHiddenEntries();
        if (url.equals(currentDisplay)) {
            currentDisplay = "";
            return;
        }
        values.beginBatchedUpdates();
        for (int i = values.size() - 1; i >= 0; i--) {
            if (!values.get(i).getFeedName().equals(url)) {
                hiddenValues.add(values.removeItemAt(i));
            }
        }
        values.endBatchedUpdates();
        currentDisplay = url;
        notifyDataSetChanged();
    }

    public void removeByUrl(String url) {
        restoreHiddenEntries();
        values.beginBatchedUpdates();
        for (int i = values.size() - 1; i >= 0; i--) {
            if (values.get(i).getFeedName().equals(url)) {
                values.removeItemAt(i);
            }
        }
        values.endBatchedUpdates();
        notifyDataSetChanged();
    }

    public void restoreHiddenEntries() {
        values.beginBatchedUpdates();
        for (EntryItem item :
                hiddenValues) {
            values.add(item);
        }
        hiddenValues.clear();
        values.endBatchedUpdates();
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView title;
        public final TextView feed;
        public LoadEntry mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            title = (TextView) view.findViewById(R.id.entry_title);
            feed = (TextView) view.findViewById(R.id.feed_title);
        }
    }
}