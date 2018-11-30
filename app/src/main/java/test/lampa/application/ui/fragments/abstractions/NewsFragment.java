package test.lampa.application.ui.fragments.abstractions;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import test.lampa.application.ui.abstractions.NewsPresenter;

public abstract class NewsFragment<P extends NewsPresenter> extends Fragment {

    protected P presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

}
