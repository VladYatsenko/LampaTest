package test.lampa.application.ui.activities.abstractions;

import android.support.v7.app.AppCompatActivity;

import test.lampa.application.ui.abstractions.NewsPresenter;

public abstract class NewsActivity<P extends NewsPresenter> extends AppCompatActivity{

    protected P presenter;


}
