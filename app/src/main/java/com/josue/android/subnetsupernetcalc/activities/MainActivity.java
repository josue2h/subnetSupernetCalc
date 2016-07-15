package com.josue.android.subnetsupernetcalc.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.josue.android.subnetsupernetcalc.R;
import com.josue.android.subnetsupernetcalc.adapters.ListCalcAdapter;
import com.josue.android.subnetsupernetcalc.adapters.ListCalcListener;
import com.josue.android.subnetsupernetcalc.adapters.PagesAdapter;
import com.josue.android.subnetsupernetcalc.fragments.CalcFragment;
import com.josue.android.subnetsupernetcalc.models.Network;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.mainToolbar)
    Toolbar toolbar;
    @Bind(R.id.mainViewpager)
    ViewPager viewPager;
    @Bind(R.id.mainTablayout)
    TabLayout tabLayout;

    PagesAdapter adapter;

    CalcFragment calcFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initPages();

        calcFragment = adapter.getCalcFragment();
    }

    private void initPages() {
        adapter = new PagesAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @OnClick(R.id.mainBtnFloat)
    public void handleClick() {
        calcFragment.addElement();
        /*if (calcFragment.getInited()) {
            if (calcFragment.isError()) {
                //calcFragment.onItemCLick();
                 calcFragment.addElement();
            } else {
                Toast.makeText(MainActivity.this, "existen fallas por solucionar", Toast.LENGTH_SHORT).show();
            }
        }else {
            calcFragment.addElement();
        }*/

    }

}
