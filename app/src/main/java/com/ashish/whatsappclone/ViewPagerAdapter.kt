package com.ashish.whatsappclone

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class ViewPagerAdapter(supportFragment:FragmentManager):FragmentStatePagerAdapter(supportFragment) {
    val mFragmentList=ArrayList<Fragment>()
    val mFragmentTitleList=ArrayList<String>()
    override fun getCount(): Int {
        return mFragmentList.size
    }

    override fun getItem(position: Int): Fragment {
        return mFragmentList[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mFragmentTitleList[position]
    }
    fun addFragment(fragment: Fragment,title:String){
        mFragmentList.add(fragment)
        mFragmentTitleList.add(title)
    }
}