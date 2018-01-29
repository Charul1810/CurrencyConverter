package com.incognysissolutions.currencyconverter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Dell on 30/06/2015.
 */
public class HomeAdapter extends FragmentStatePagerAdapter {
    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created


    public HomeAdapter(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);
        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;
    }



    // This method return the titles for the Tabs in the Tab Strip

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return NumbOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0) // if the position is 0 we are returning the First tab
        {
            Tab_Exchange_Rates tab1 = new Tab_Exchange_Rates();
            return tab1;
        }
        else if(position == 1) // if the position is 0 we are returning the First tab             // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
        {
            Tab_Currency_Converter tab1 = new Tab_Currency_Converter();
            return tab1;
        }
        else
        {
            Tab_Currency_Converter tab2 = new Tab_Currency_Converter();
            return tab2;
        }

    }
}
