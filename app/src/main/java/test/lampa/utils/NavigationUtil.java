package test.lampa.utils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

public class NavigationUtil {

    public static void replaceFragment(FragmentActivity context, Class fragmentClass, Bundle bundle, boolean toStack, int container) {
        Fragment fragment = null;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
            fragment.setArguments(bundle);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        if (context != null) {
            FragmentTransaction transaction = context.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(container, fragment);

            if (toStack) {
                transaction.addToBackStack(fragmentClass.getName());
            }
            transaction.commit();
        }
    }

}
