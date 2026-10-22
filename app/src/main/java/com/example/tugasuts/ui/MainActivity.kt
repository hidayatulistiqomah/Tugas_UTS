package com.example.tugasuts.ui

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.viewpager2.widget.ViewPager2
import com.example.tugasuts.R
import com.example.tugasuts.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object {
        @StringRes
        private val JUDUL_TAB = intArrayOf(
            R.string.tab_dashboard,
            R.string.tab_about
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Header menyesuaikan tinggi status bar agar tidak tertutup
        val paddingAtasAwal = binding.headerContainer.paddingTop
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { _, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            binding.headerContainer.updatePadding(top = paddingAtasAwal + bars.top)
            binding.viewPager.updatePadding(bottom = bars.bottom)
            insets
        }

        val halamanAdapter = HalamanPagerAdapter(this)

        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = halamanAdapter

        val tabs: TabLayout = binding.tabs

        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(JUDUL_TAB[position])
        }.attach()
    }
}
