package com.sixsixsix.asmt

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.sixsixsix.asmt.base.BaseActivity
import com.sixsixsix.asmt.base.BaseFragment
import com.sixsixsix.asmt.databinding.ActivityMainBinding
import com.sixsixsix.asmt.fragment.FeaturesFragment
import com.sixsixsix.asmt.fragment.SettingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {


    private val mFgList = mutableListOf<Fragment>()
    override fun initData() {
        mFgList.add(FeaturesFragment.newInstance())
        mFgList.add(SettingFragment.newInstance())
        //提交1
    }

    override fun initView() {
        initViewPager()
        initNav()
    }

    private fun initViewPager() {
        binding.viewpager.run {
            adapter = FgAdapter(supportFragmentManager)
            offscreenPageLimit = mFgList.size
            addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrollStateChanged(state: Int) {
                }

                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {

                }

                override fun onPageSelected(position: Int) {
                    binding.bottomnavigation.menu.getItem(position).isChecked = true
                }

            })
        }
    }

    private fun initNav() {
        binding.bottomnavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.features -> {
                    binding.viewpager.currentItem = 0
                }

                R.id.setting -> {
                    binding.viewpager.currentItem = 1
                }
            }
            true
        }
    }

    inner class FgAdapter(fm: FragmentManager) : FragmentPagerAdapter(
        fm,
        BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
    ) {
        override fun getItem(position: Int) = mFgList[position]

        override fun getCount() = mFgList.size

    }
}