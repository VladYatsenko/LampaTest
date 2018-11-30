package test.lampa.tools;

import io.reactivex.disposables.Disposable;

public class RxTool {

    public static void autoDispose(Disposable... disposables) {

        for (Disposable disposable : disposables) {

            if (disposable != null && !disposable.isDisposed())
                disposable.dispose();

        }

    }

}
