package com.ashish.whatsappclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {
    lateinit var toolbar:Toolbar
    lateinit var ViewPager:ViewPager
    lateinit var tablyt:TabLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar=findViewById(R.id.toolbar)
        ViewPager=findViewById(R.id.viewpager)
        tablyt=findViewById(R.id.tablyt)
        setSupportActionBar(toolbar)
        val adapter=ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(ChatsFragment(),"Chats")
        adapter.addFragment(StatusFragment(),"Status")
        adapter.addFragment(CallsFragment(),"Calls")
        ViewPager.adapter=adapter
        tablyt.setupWithViewPager(ViewPager)
    }
}