package org.newsapi.newsapidemo.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import org.newsapi.newsapidemo.R;
import org.newsapi.newsapidemo.api.ApiFactory;
import org.newsapi.newsapidemo.api.IApiService;
import org.newsapi.newsapidemo.model.Article;
import org.newsapi.newsapidemo.model.ArticleDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import me.everything.android.ui.overscroll.IOverScrollDecor;
import me.everything.android.ui.overscroll.IOverScrollState;
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Article_Fragment extends Fragment {

    private static String PAGE = "page";


    private static String SOURCE = "source";

    int mPage = 1;
    String mSource = "";

    private boolean isWasBouncing;
    private boolean onGoing;
    private OnArticleListFragmentInteraction mListener;
    RecyclerView recyclerView;
    ArrayList<Article> articles;

    public Article_Fragment() {
    }


    @SuppressWarnings("unused")
    public static Article_Fragment newInstance(String source) {
        Article_Fragment fragment = new Article_Fragment();
        Bundle args = new Bundle();
        args.putString(SOURCE, source);
        fragment.setArguments(args);
        return fragment;
    }

    public void setSources(String sources){
        this.mSource = sources;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            mSource = getArguments().getString(SOURCE);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_article_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            articles = new ArrayList<Article>();


            recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(new Article_RecyclerViewAdapter(articles, mListener));
            getArticle(mSource, mPage, false);

            IOverScrollDecor decor = OverScrollDecoratorHelper.setUpOverScroll(recyclerView, OverScrollDecoratorHelper.ORIENTATION_VERTICAL);
            decor.setOverScrollUpdateListener((dec, state, offset)->{

                int offsetAction = 50;
                if (state != IOverScrollState.STATE_IDLE && (offset > offsetAction | offset<-offsetAction)){
                    RelativeLayout.LayoutParams l = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 20);

                    int pos = offset < -offsetAction ? RelativeLayout.ALIGN_PARENT_BOTTOM: RelativeLayout.ALIGN_PARENT_TOP;
                    l.addRule(pos);
                    ////hit API if
                    if (state == IOverScrollState.STATE_BOUNCE_BACK && !isWasBouncing ) {
                        isWasBouncing = true;
                        mPage++;
                        getArticle(mSource, mPage, true);
                    }

                }else {isWasBouncing = false;}

            });
        }
        return view;
    }


    /**
     * getting Article
     * @param source
     * @param page
     * @param nextMode not used
     */
    void getArticle(String source, int page, Boolean nextMode){

        IApiService sourcesServices = new ApiFactory().create(IApiService.class);
        Call<ArticleDTO> sourceDTO = sourcesServices.getNewsEverything(source, page);

        sourceDTO.enqueue(new Callback<ArticleDTO>() {
            @Override
            public void onResponse(Call<ArticleDTO> call, Response<ArticleDTO> response) {
                if (!nextMode){
                    articles.clear();
                }
                articles.addAll(Arrays.asList(Objects.requireNonNull(response.body()).articles));
                recyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArticleDTO> call, Throwable t) { }
        });
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnArticleListFragmentInteraction) {
            mListener = (OnArticleListFragmentInteraction) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnArticleListFragmentInteraction {
        void onArticleInteraction(Article item);
    }
}
