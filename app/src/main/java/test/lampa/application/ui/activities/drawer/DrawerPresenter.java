package test.lampa.application.ui.activities.drawer;

import test.lampa.application.ui.abstractions.NewsPresenter;
import test.lampa.application.ui.activities.drawer.abstractions.IDrawerView;

public class DrawerPresenter extends NewsPresenter<IDrawerView> {

    @Override
    public void attachView(IDrawerView view) {
        super.attachView(view);
    }

}
