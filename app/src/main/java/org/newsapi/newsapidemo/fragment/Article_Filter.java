package org.newsapi.newsapidemo.fragment;

import android.widget.Filter;

import org.newsapi.newsapidemo.model.Article;

import java.util.ArrayList;
import java.util.List;

public class Article_Filter extends Filter {

    List<Article> mValues;
    List<Article> tmpValues;
    OnArticleResult iResult;

    Article_Filter(List<Article> mValue){
        this.mValues = mValue;
        this.mValues = mValue;
    }

    public void setOnArticleResult(OnArticleResult onArticleResult){
        this.iResult = onArticleResult;
    }


        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint != null && constraint.length() > 0) {
                List<Article> filteredList = new ArrayList<Article>();

                for (Article cs : mValues) {

                    //find by title
                    if (cs.title.toLowerCase().contains(constraint)) {
                        filteredList.add(cs);
                    }
                }
                results.count = filteredList.size();
                results.values = filteredList;
            } else {
                results.count = mValues.size();
                results.values = mValues;

            }
            this.iResult.onArticleResult(tmpValues);
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            tmpValues = (List<Article>) results.values;
            this.iResult.onArticleResult(tmpValues);
        }


        interface  OnArticleResult{
        void onArticleResult(List<Article> articles);
        }


}
