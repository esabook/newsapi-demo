package org.newsapi.newsapidemo.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.newsapi.newsapidemo.R;
import org.newsapi.newsapidemo.api.ApiFactory;
import org.newsapi.newsapidemo.api.IApiService;
import org.newsapi.newsapidemo.model.Source;
import org.newsapi.newsapidemo.model.SourceDTO;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsSource_Fragment extends Fragment {


    private OnNewsSourceListFragmentInteraction mListener;
    ArrayList<Source> sources;
    RecyclerView recyclerView;

    public NewsSource_Fragment() {
    }


    @SuppressWarnings("unused")
    public static NewsSource_Fragment newInstance() {
        NewsSource_Fragment fragment = new NewsSource_Fragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_newssource_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            sources = new ArrayList<Source>();

            recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(new NewsSource_RecyclerViewAdapter(sources, mListener));

            IApiService sourcesServices = new ApiFactory().create(IApiService.class);
            Call<SourceDTO> sourceDTO = sourcesServices.getSources();

            sourceDTO.enqueue(new Callback<SourceDTO>() {
                @Override
                public void onResponse(Call<SourceDTO> call, Response<SourceDTO> response) {
                    sources.clear();
                    sources.addAll(Arrays.asList(response.body().sources));
                    recyclerView.getAdapter().notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<SourceDTO> call, Throwable t) { }
            });

        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnNewsSourceListFragmentInteraction) {
            mListener = (OnNewsSourceListFragmentInteraction) context;
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


    public interface OnNewsSourceListFragmentInteraction {
        void onNewsSourceInteraction(Source item);
    }
}
