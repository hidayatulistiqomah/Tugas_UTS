package com.example.tugasuts.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.tugasuts.ui.beranda.BerandaFragment
import com.example.tugasuts.ui.profil.ProfilFragment

class HalamanPagerAdapter(activity: AppCompatActivity) :
    FragmentStateAdapter(activity) {

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> BerandaFragment()
            else -> ProfilFragment()
        }
    }

    override fun getItemCount(): Int = 2
}
