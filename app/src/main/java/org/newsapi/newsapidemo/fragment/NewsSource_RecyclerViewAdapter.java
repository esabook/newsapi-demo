package org.newsapi.newsapidemo.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.newsapi.newsapidemo.R;
import org.newsapi.newsapidemo.model.Source;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsSource_RecyclerViewAdapter extends RecyclerView.Adapter<NewsSource_RecyclerViewAdapter.ViewHolder> {

    private final List<Source> mValues;
    private final NewsSource_Fragment.OnNewsSourceListFragmentInteraction mListener;

    public NewsSource_RecyclerViewAdapter(List<Source> items, NewsSource_Fragment.OnNewsSourceListFragmentInteraction listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_newssource, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        //FIXME move to use data binding
        holder.mSourceItem = mValues.get(position);
        holder.mName.setText(mValues.get(position).name);
        holder.mLanguange.setText(mValues.get(position).language);
        holder.mDescription.setText(mValues.get(position).description);
        holder.mUrl.setText(mValues.get(position).url);
        holder.mCategory.setText(mValues.get(position).category);

        holder.mView.setOnClickListener(v-> {
                if (null != mListener) {
                    mListener.onNewsSourceInteraction(holder.mSourceItem);
                }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;

        @BindView(R.id.name) public TextView mName;
        @BindView(R.id.language) public TextView mLanguange;
        @BindView(R.id.description) public TextView mDescription;
        @BindView(R.id.url) public TextView mUrl;
        @BindView(R.id.category) public TextView mCategory;

        public Source mSourceItem;

        public ViewHolder(View view) {

            super(view);
            mView = view;
            ButterKnife.bind(this, view);
        }

        @Override
        public String toString() {
            return String.valueOf(mName.getText());
        }
    }
}
