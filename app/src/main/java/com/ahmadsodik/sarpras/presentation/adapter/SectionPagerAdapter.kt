package com.ahmadsodik.sarpras.presentation.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ahmadsodik.sarpras.presentation.sedang_dipinjam.SedangDipinjamFragment
import com.ahmadsodik.sarpras.presentation.selesai.SelesaiFragment

class SectionPagerAdapter(
    fragment: Fragment,
) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> SedangDipinjamFragment()
            1 -> SelesaiFragment()
            else -> SedangDipinjamFragment()
        }
    }

}