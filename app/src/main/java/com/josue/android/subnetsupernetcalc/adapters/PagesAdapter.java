package com.josue.android.subnetsupernetcalc.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.josue.android.subnetsupernetcalc.R;
import com.josue.android.subnetsupernetcalc.fragments.CalcFragment;
import com.josue.android.subnetsupernetcalc.fragments.SubnetFragment;
import com.josue.android.subnetsupernetcalc.fragments.SupernetFragment;

/**
 * Created by Josue on 04/07/2016.
 */
public class PagesAdapter extends FragmentPagerAdapter {
    CalcFragment calcFragment;
    SubnetFragment subnetFragment;
    SupernetFragment supernetFragment;

    private CharSequence title[] = {"Calculadora", "Subredes", "Superredes"};

    public PagesAdapter(FragmentManager fm) {
        super(fm);
        calcFragment = new CalcFragment();
        subnetFragment = new SubnetFragment();
        supernetFragment = new SupernetFragment();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:

                calcFragment.setRetainInstance(true);
                return calcFragment;
            case 1:

                subnetFragment.setRetainInstance(true);
                return subnetFragment;
            case 2:

                supernetFragment.setRetainInstance(true);
                return supernetFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return title.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }

    public CalcFragment getCalcFragment() {
        return calcFragment;
    }

    public SubnetFragment getSubnetFragment() {
        return subnetFragment;
    }

    public SupernetFragment getSupernetFragment() {
        return supernetFragment;
    }
}
