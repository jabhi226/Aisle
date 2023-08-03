package com.example.aisleassignment.moudles.notes.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.aisleassignment.R
import com.example.aisleassignment.databinding.ActivityNotesBinding
import com.example.aisleassignment.moudles.notes.ui.fragments.EmptyFragment
import com.example.aisleassignment.moudles.notes.ui.fragments.NotesFragment
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList
import java.util.HashMap

@AndroidEntryPoint
class NotesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotesBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        initTabLayout()
        initViewPager()
    }

    private var onPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            binding.tabLayout.selectTab(binding.tabLayout.getTabAt(position))
            super.onPageSelected(position)
        }
    }

    private var onTabSelectedListener = object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab) {
            binding.pager.setCurrentItem(tab.position, false)
        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {
        }

        override fun onTabReselected(tab: TabLayout.Tab) {
            binding.pager.setCurrentItem(tab.position, false)
        }
    }

    private fun initTabLayout() {
        binding.tabLayout.apply {
            addTab(newTab().setText(getString(R.string.discover)))
            addTab(newTab().setText(getString(R.string.notes)))
            addTab(newTab().setText(getString(R.string.matches)))
            addTab(newTab().setText(getString(R.string.profile)))
            getTabAt(0)?.setIcon(R.drawable.iv_message)
            getTabAt(1)?.setIcon(R.drawable.iv_message)
            getTabAt(2)?.setIcon(R.drawable.iv_message)
            getTabAt(3)?.setIcon(R.drawable.iv_message)
        }
    }

    private fun initViewPager() {
        val myTabPagerAdapter =
            TabPagerAdapter(supportFragmentManager, lifecycle, getViewPagersFragments())
        binding.pager.apply {
            adapter = myTabPagerAdapter
            setPageTransformer { _, _ ->
            }
            binding.tabLayout.addOnTabSelectedListener(onTabSelectedListener)
            registerOnPageChangeCallback(onPageChangeCallback)
        }
    }

    private fun getViewPagersFragments(): ArrayList<Fragment> {
        return arrayListOf(
            NotesFragment.newInstance(),
            EmptyFragment.newInstance(),
            EmptyFragment.newInstance(),
            EmptyFragment.newInstance()
        )
    }

//    private fun replaceFragment(fragment: Fragment) {
//        supportFragmentManager
//            .beginTransaction()
//            .replace(binding.frameNoteActivity.id, fragment, fragment::class.java.simpleName)
//            .setCustomAnimations(
//                R.anim.fragment_right_to_left,
//                R.anim.fragment_left_to_right,
//                R.anim.fragment_right_to_left,
//                R.anim.fragment_left_to_right
//            )
//            .addToBackStack(null)
//            .commit()
//    }

//    @Deprecated("Deprecated in Java")
//    override fun onBackPressed() {
//        val fragment = supportFragmentManager.findFragmentById(binding.frameNoteActivity.id)
//        when (fragment?.tag) {
//            NotesFragment::class.java.simpleName -> {
//                this.finish()
//            }
//
//            else -> {
//                super.onBackPressed()
//            }
//        }
//    }

    inner class TabPagerAdapter(
        fa: FragmentManager,
        lc: Lifecycle,
        private var fragmentsList: ArrayList<Fragment>
    ) : FragmentStateAdapter(fa, lc) {
        override fun getItemCount(): Int = fragmentsList.size

        override fun createFragment(position: Int): Fragment {
            return fragmentsList[position]
        }

        fun changeScreen(newFragment: Fragment, position: Int) {
            fragmentsList[position] = newFragment
            notifyItemChanged(position, Unit)
        }
    }
}