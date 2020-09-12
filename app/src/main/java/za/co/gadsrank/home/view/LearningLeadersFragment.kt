package za.co.gadsrank.home.view

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.learning_leaders_fragment.view.*
import za.co.gadsrank.R
import za.co.gadsrank.home.adapter.TopLearnerHoursAdapter
import za.co.gadsrank.home.model.TopLearnerHours
import za.co.gadsrank.home.viewmodel.LearningLeadersViewModel

class LearningLeadersFragment : Fragment() {

    companion object {
        fun newInstance() = LearningLeadersFragment()
    }

    lateinit var viewModel: LearningLeadersViewModel
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.learning_leaders_fragment, container, false)

        recyclerView = view.recyclerView

        initViewModel()
        initObservers()

        return view;
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(LearningLeadersViewModel::class.java)
        viewModel.init()
    }

    private fun initObservers() {
        viewModel.items.observe(viewLifecycleOwner, Observer {
            initLeaders(it)
        })
    }

    private fun initLeaders(it: List<TopLearnerHours>?) {
        val topLearnerHoursAdapter = it?.let {
           TopLearnerHoursAdapter(it)
        }
        recyclerView.apply {
            setHasFixedSize(true)
            adapter = topLearnerHoursAdapter
        }
    }

}