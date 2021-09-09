package com.example.wizbook;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.example.wizbook.KnownFragment.spell_list_known;

public class PagerAdapter extends FragmentPagerAdapter {

    private int numOfTabs ;
    private final Context mContext ;
    private static final int[] TAB_TITLES = new int[]{R.string.tabSpells, R.string.tabKnown, R.string.tabPrepared} ;
    public static List<List<String>> spell_list ;
    public SpellsFragment spell_fragment ;

    public PagerAdapter(FragmentManager fm, int numOfTabs, Context context, List<List<String>> spell_list) {
        super(fm) ;
        this.numOfTabs = numOfTabs ;
        mContext = context ;
        this.spell_list = spell_list ;
        spell_fragment = SpellsFragment.newInstance(spell_list) ;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0 : Log.d("mega", "PagerAdapter is returning spell_fragment") ; return spell_fragment ;
            case 1 : Log.d("mega", "PagerAdapter is returning KnownFragment") ; return new KnownFragment() ;
            case 2 : Log.d("mega", "PagerAdapter is returning PreparedFragment") ; return new PreparedFragment() ;
        }
        return null;
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]) ;
    }

}
