package com.baiganov.stocksapp

import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.children
import androidx.viewpager2.widget.ViewPager2
import com.baiganov.stocksapp.ui.PagerViewAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var tablayout: TabLayout
    private lateinit var editTextSearchView: EditText
    private lateinit var searchView: SearchView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.mainToolbar)
        setSupportActionBar(toolbar)
        viewPager = findViewById(R.id.view_pager)
        tablayout = findViewById(R.id.tab_layout)
        val adapter = PagerViewAdapter(supportFragmentManager, lifecycle)
        viewPager.adapter = adapter
        TabLayoutMediator(tablayout, viewPager) { tab, position ->
            when(position) {
                0 -> {
                    tab.text = "Stocks"
                }

                1 -> {
                    tab.text = "Favourite"
                    tab.view.children.forEach {
                        if (it is TextView) {
                            it.post{
                                it.setTextAppearance(this, R.style.TabLayoutStyleSmall)
                            }

                        }
                    }
                }
            }
        }.attach()

        tablayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.view?.children?.forEach {
                    if (it is TextView) {
                        it.post {
                            it.setTextAppearance(applicationContext, R.style.TabLayoutStyle)

                        }

                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

                tab?.view?.children?.forEach {
                    if (it is TextView) {
                        it.post {
                            it.setTextAppearance(applicationContext, R.style.TabLayoutStyleSmall)
                        }

                    }
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }
}