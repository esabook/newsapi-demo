package org.newsapi.newsapidemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import org.newsapi.newsapidemo.R;
import org.newsapi.newsapidemo.fragment.Article_Fragment;
import org.newsapi.newsapidemo.fragment.NewsSource_Fragment;
import org.newsapi.newsapidemo.model.Article;
import org.newsapi.newsapidemo.model.Source;
import org.newsapi.newsapidemo.view.util.ZoomOutViewPager;

public class MainActivity extends FragmentActivity implements NewsSource_Fragment.OnNewsSourceListFragmentInteraction, Article_Fragment.OnArticleListFragmentInteraction {

    private ViewPager pager;
    private MyPagerAdapter myPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());

        pager = (ViewPager) findViewById(R.id.news_pager);
        pager.setPageTransformer(true, new ZoomOutViewPager());
        pager.setAdapter(myPagerAdapter);

    }




    @Override
    public void onBackPressed() {
        if (pager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            pager.setCurrentItem(pager.getCurrentItem() - 1);
        }
    }

    @Override
    public void onNewsSourceInteraction(Source item) {
        myPagerAdapter.setArticleFragment(item);
        pager.setAdapter(myPagerAdapter);
        pager.getAdapter().notifyDataSetChanged();
        pager.setCurrentItem(2);
    }

    @Override
    public void onArticleInteraction(Article item) {
        Intent intent = new Intent(MainActivity.this, ReadNewsActivity.class);
        intent.putExtra("URL", item.url);
        startActivity(intent);
    }


    /**
     * My Pager Adapter for ..
     */
    private class MyPagerAdapter extends FragmentPagerAdapter {

        Article_Fragment nextFragment = Article_Fragment.newInstance("dummy");

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void setArticleFragment(Source item){
            nextFragment.setSources(item.id);
        }

        @Override
        public Fragment getItem(int pos) {
            switch(pos) {
                case 0: return NewsSource_Fragment.newInstance();
                case 1:  return nextFragment;
                default: return NewsSource_Fragment.newInstance();
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

}
