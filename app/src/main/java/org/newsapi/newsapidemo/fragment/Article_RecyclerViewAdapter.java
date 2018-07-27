package org.newsapi.newsapidemo.fragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.newsapi.newsapidemo.R;
import org.newsapi.newsapidemo.model.Article;

import java.util.List;

public class Article_RecyclerViewAdapter extends RecyclerView.Adapter<Article_RecyclerViewAdapter.ViewHolder> implements Filterable {

    private Article_Filter filter;
    private List<Article> mValues;
    private List<Article> tmpValues;

    private Context  mContext;

    private final Article_Fragment.OnArticleListFragmentInteraction mListener;

    public Article_RecyclerViewAdapter(List<Article> items, Article_Fragment.OnArticleListFragmentInteraction listener) {
        mValues = items;
        tmpValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.fragment_article, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = tmpValues.get(position);
        holder.mAuthor.setText(tmpValues.get(position).author);
        holder.mPublishAt.setText(tmpValues.get(position).getPublishedAt());
        holder.mTitle.setText(tmpValues.get(position).title);
        holder.mDescription.setText(tmpValues.get(position).description);
        Glide.with(mContext)
                .load(tmpValues.get(position).urlToImage)
                .into(holder.mImage);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onArticleInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return tmpValues.size();
    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new Article_Filter(mValues);
            filter.setOnArticleResult(result ->{
                this.tmpValues = result;
            });
        }
        return filter;
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mImage;
        public final TextView mAuthor;
        public final TextView mPublishAt;
        public final TextView mTitle;
        public final TextView mDescription;
        public Article mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mImage = (ImageView) view.findViewById(R.id.article_image);
            mAuthor = (TextView) view.findViewById(R.id.author);
            mPublishAt = (TextView) view.findViewById(R.id.publishedAt);
            mTitle = (TextView) view.findViewById(R.id.title);
            mDescription = (TextView) view.findViewById(R.id.description);
        }
    }
}
