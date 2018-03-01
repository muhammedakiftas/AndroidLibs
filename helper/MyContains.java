package matas.com.hayalhanem.fragment;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import matas.com.hayalhanem.R;

/**
 * Created by muham on 25.02.2018.
 */

public class MyContains {

    static FragmentManager fragmentManager;

    public MyContains(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public MyContains() {
    }

    public static void replaceFragment(Fragment _fragment, boolean isBack){
        FragmentTransaction transaction =  fragmentManager.beginTransaction();
        if (isBack)
            transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
        else
            transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        transaction.replace(R.id.main_fragment, _fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
