package ydrasaal.alligregator.adapters;

import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ydrasaal.alligregator.R;
import ydrasaal.alligregator.data.LoadEntry;
import ydrasaal.alligregator.listeners.OnRecyclerItemClickListener;

/**
 * Created by lchazal on 18/03/16.
 */
public class FeedListAdapter
        extends RecyclerView.Adapter<FeedListAdapter.ViewHolder> {

    private final SortedList<LoadEntry>     values;
    private OnRecyclerItemClickListener     listener;

    public FeedListAdapter(OnRecyclerItemClickListener listener) {
        this.listener = listener;

        values = new SortedList<>(LoadEntry.class, new SortedList.Callback<LoadEntry>() {
            @Override
            public int compare(LoadEntry o1, LoadEntry o2) {
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
            public boolean areContentsTheSame(LoadEntry oldItem, LoadEntry newItem) {
                return oldItem.getLink().equals(newItem.getLink());
            }

            @Override
            public boolean areItemsTheSame(LoadEntry item1, LoadEntry item2) {
                return item1.getTitle().equals(item2.getTitle());
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
        holder.mItem = values.get(position);
        holder.title.setText(Html.fromHtml(values.get(position).getTitle()));

        if (listener != null) {
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(v, position);
                }
            });
        }
//        holder.mView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Bundle arguments = new Bundle();
//                arguments.putString(EntryDetailFragment.ARG_URL, holder.mItem.getLink());
//                arguments.putString(EntryDetailFragment.ARG_SNIPPET, holder.mItem.getContentSnippet());
//                arguments.putString(EntryDetailFragment.ARG_TITLE, holder.mItem.getTitle());
//                if (mTwoPane) {
//                    EntryDetailFragment fragment = new EntryDetailFragment();
//                    fragment.setArguments(arguments);
//                    getSupportFragmentManager().beginTransaction()
//                            .replace(R.id.feed_detail_container, fragment)
//                            .commit();
//                } else {
//                    Intent intent = new Intent(LobbyActivity.this, EntryDetailActivity.class);
//                    intent.putExtras(arguments);
//                    startActivity(intent);
////                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(holder.mItem.getLink()));
////                        startActivity(browserIntent);
//                }
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public void addItem(LoadEntry item) {
        values.add(item);
        notifyItemInserted(values.size() - 1);
    }

    public void removeItem(int position) {
        values.removeItemAt(position);
        notifyItemRemoved(position);
    }

    public LoadEntry getItem(int position) {
        return values.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView title;
        public LoadEntry mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            title = (TextView) view.findViewById(R.id.title);
        }
    }
}