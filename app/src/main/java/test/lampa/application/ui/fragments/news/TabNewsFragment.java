package test.lampa.application.ui.fragments.news;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import test.lampa.R;
import test.lampa.application.ui.fragments.news.pager.PagerFragment;

public class TabNewsFragment extends Fragment {

    @BindView(R.id.news_pager)
    ViewPager newsPager;
    @BindView(R.id.news_tab)
    TabLayout newsTab;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        ButterKnife.bind(this, view);

        newsTab.addTab(newsTab.newTab().setText(R.string.stories));
        newsTab.addTab(newsTab.newTab().setText(R.string.video));
        newsTab.addTab(newsTab.newTab().setText(R.string.favourites));
        newsTab.setTabGravity(TabLayout.GRAVITY_FILL);

        final NewsPagerAdapter adapter = new NewsPagerAdapter
                (getActivity().getSupportFragmentManager(), newsTab.getTabCount());
        newsPager.setAdapter(adapter);
        newsPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(newsTab));
        newsPager.setOffscreenPageLimit(3);
        newsTab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                newsPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return view;
    }

    class NewsPagerAdapter extends FragmentStatePagerAdapter {

        int count;

        NewsPagerAdapter(FragmentManager fm, int count) {
            super(fm);
            this.count = count;
        }

        @Override
        public Fragment getItem(int position) {
            return new PagerFragment();
        }

        @Override
        public int getCount() {
            return count;
        }
    }


}
