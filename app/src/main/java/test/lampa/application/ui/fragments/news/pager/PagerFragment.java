package test.lampa.application.ui.fragments.news.pager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import test.lampa.R;
import test.lampa.application.ui.fragments.abstractions.IPagerView;
import test.lampa.application.ui.fragments.abstractions.NewsFragment;
import test.lampa.application.ui.fragments.news.pager.model.NewsModel;

public class PagerFragment extends NewsFragment<PagerPresenter> implements IPagerView {

    @BindView(R.id.news_recycler)
    RecyclerView newsRecycler;
    private NewsAdapter newsAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_pager, container, false);
        ButterKnife.bind(this, view);

        presenter = new PagerPresenter(getContext());
        newsAdapter = new NewsAdapter();

        newsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        newsRecycler.setAdapter(newsAdapter);
        newsRecycler.addItemDecoration(new FooterItemDecoration());

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.attachView(this);
    }

    @Override
    public void updateNewsList(List<NewsModel> list) {
        newsAdapter.setItems(list);
    }

    @Override
    public void updateIndicator(ViewGroup indicatorsContainer, List<NewsModel> list){

        indicatorsContainer.removeAllViews();
        int buttonSize = (int) getResources().getDimension(R.dimen.padding_small);

        for (int i = 0; i < list.size(); i++) {
            ImageView button = (ImageView) getLayoutInflater().inflate(R.layout.circle_layout, indicatorsContainer, false);
            ImageView icon = button.findViewById(R.id.circle);
            icon.setImageDrawable(getContext().getDrawable(R.drawable.circle));

            if (list.get(i).isSelected()){
                icon.setColorFilter(getContext().getResources().getColor(R.color.colorBlue));
            }else{
                icon.setColorFilter(getContext().getResources().getColor(R.color.colorDarkGrey));
            }
            button.setSelected(list.get(i).isSelected());

            indicatorsContainer.addView(button);

            if (i !=  list.size() - 1) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(buttonSize, buttonSize);
                Space space = new Space(getContext());
                space.setLayoutParams(params);
                indicatorsContainer.addView(space);
            }

        }

    }

    public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private static final int TYPE_HEADER = 0;
        private static final int TYPE_ITEM = 1;

        List<NewsModel> list = new ArrayList<>();

        void setItems(List<NewsModel> list) {

            this.list = list;
            notifyDataSetChanged();

        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {

            if (position == TYPE_ITEM) {

                return new VHItem(

                        LayoutInflater.from(getContext())
                                .inflate(R.layout.item_news, viewGroup, false)

                );

            } else if (position == TYPE_HEADER) {

                return new VHHeader(

                        LayoutInflater.from(getContext())
                                .inflate(R.layout.header_news, viewGroup, false)

                );

            }

            throw new RuntimeException("there is no type that matches the type " + position + " + make sure your using types correctly");

        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

            if (holder instanceof VHItem && position < list.size()) {

                VHItem item = (VHItem) holder;

                NewsModel dataItem = list.get(position-1);

                item.title.setText(dataItem.getTitle());
                item.date.setText("Date: "+dataItem.getDate());
                item.source.setText("Source: "+dataItem.getSource());
                Picasso.get().load(dataItem.getImage()).into(item.image);


            } else if (holder instanceof VHHeader) {

                final VHHeader header = (VHHeader) holder;
                final TopNewsAdapter adapter = new TopNewsAdapter(list);
                header.itemPager.setAdapter(adapter);
                updateIndicator(header.indicatorsContainer, list);
                header.itemPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        presenter.updateIndicator(header.indicatorsContainer, position);
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });

            }

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        @Override
        public int getItemViewType(int position) {

            if (position == 0)
                return TYPE_HEADER;

            return TYPE_ITEM;

        }

        class VHItem extends RecyclerView.ViewHolder {

            @BindView(R.id.title)
            TextView title;
            @BindView(R.id.image)
            ImageView image;
            @BindView(R.id.date)
            TextView date;
            @BindView(R.id.source)
            TextView source;

            VHItem(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }

        }

        class VHHeader extends RecyclerView.ViewHolder {

            @BindView(R.id.item_pager)
            ViewPager itemPager;
            @BindView(R.id.indicators_container)
            ViewGroup indicatorsContainer;

            VHHeader(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }

        }

    }

    class TopNewsAdapter extends PagerAdapter {

        List<NewsModel> list;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.image)
        ImageView image;
        @BindView(R.id.date)
        TextView date;
        @BindView(R.id.source)
        TextView source;

        LayoutInflater layoutInflater;

        TopNewsAdapter(List<NewsModel> list){
            this.list = list;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @NonNull
        @SuppressLint("SetTextI18n")
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View itemView = LayoutInflater.from(container.getContext()).inflate(R.layout.pager_news, container, false);
            ButterKnife.bind(this, itemView);

            title.setText(list.get(position).getTitle());
            date.setText("Date: "+list.get(position).getDate());
            source.setText("Source: "+list.get(position).getSource());
            Picasso.get().load(list.get(position).getImage()).into(image);

            container.addView(itemView);
            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    public class FooterItemDecoration extends RecyclerView.ItemDecoration {

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            if (parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount() - 1) {
                outRect.bottom = 32;
            }
        }

    }

}
