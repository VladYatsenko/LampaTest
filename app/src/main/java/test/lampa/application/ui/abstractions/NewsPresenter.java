package test.lampa.application.ui.abstractions;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;

import io.reactivex.disposables.CompositeDisposable;
import test.lampa.tools.RxTool;

public abstract class NewsPresenter<V extends LifecycleOwner> implements LifecycleObserver {

    protected CompositeDisposable disposables;
    protected V view;

    public void attachView(V view) {

        this.view = view;
        this.view.getLifecycle().addObserver(this);
        this.disposables = new CompositeDisposable();

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    protected void detachView() {

        RxTool.autoDispose(disposables);
        this.view = null;

    }

    public V getView() {
        return view;
    }

}
