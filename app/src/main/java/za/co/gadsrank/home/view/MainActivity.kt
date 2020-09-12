package za.co.gadsrank.home.view

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*
import za.co.gadsrank.R
import za.co.gadsrank.home.adapter.LeadersViewPagerAdapter
import za.co.gadsrank.submission.ProjectSubmissionActivity

class MainActivity : AppCompatActivity() {

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var loadingImageView: View
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = custom_toolbar

        loadingImageView = loading_image

        val learningLeadersFragment = LearningLeadersFragment.newInstance()
        val skillIqFragment = SkillIqFragment.newInstance()
        val items = listOf(learningLeadersFragment, skillIqFragment)

        initViewPager(items)

        val handler = Handler()
        handler.postDelayed({
           loadingImageView.visibility = View.GONE
            configureToolbar()
        }, 2000)

    }

    private fun initViewPager(items: List<Fragment>) {
        viewPager = view_pager_content
        viewPager.adapter = LeadersViewPagerAdapter(this, items)
        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        tabLayout = tab_layout
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.learning_leaders)
                1 -> tab.text = getString(R.string.skill_iq_leaders)
            }
        }.attach()
    }

    private fun configureToolbar() {
        setSupportActionBar(toolbar)
        submit_btn.setOnClickListener {
            startActivity(Intent(this, ProjectSubmissionActivity::class.java))
        }
    }

}