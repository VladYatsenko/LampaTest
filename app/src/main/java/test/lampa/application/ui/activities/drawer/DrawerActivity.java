package test.lampa.application.ui.activities.drawer;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;

import butterknife.BindView;
import butterknife.ButterKnife;
import test.lampa.R;
import test.lampa.application.ui.activities.abstractions.NewsActivity;
import test.lampa.application.ui.activities.drawer.abstractions.IDrawerView;
import test.lampa.application.ui.fragments.news.TabNewsFragment;
import test.lampa.utils.NavigationUtil;

public class DrawerActivity extends NewsActivity<DrawerPresenter> implements IDrawerView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        ButterKnife.bind(this);
        presenter = new DrawerPresenter();

        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.app_name);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationUtil.replaceFragment(this, TabNewsFragment.class, null, false, R.id.container);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.attachView(this);
    }

}
