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
import za.co.gadsrank.home.adapter.TopLearnerSkillAdapter
import za.co.gadsrank.home.model.TopLearnerSkillIq
import za.co.gadsrank.home.viewmodel.SkillIqViewModel

class SkillIqFragment : Fragment() {

    companion object {
        fun newInstance() = SkillIqFragment()
    }

    lateinit var viewModel: SkillIqViewModel
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.skill_iq_fragment, container, false)

        recyclerView = view.recyclerView

        initViewModel()
        initObservers()

        return view
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(SkillIqViewModel::class.java)
        viewModel.init()
    }

    private fun initObservers() {
        viewModel.items.observe(viewLifecycleOwner, Observer {
            initLeaders(it)
        })
    }

    private fun initLeaders(it: List<TopLearnerSkillIq>?) {
        val topLearnerSkillAdapter = it?.let {
            TopLearnerSkillAdapter(it)
        }
        recyclerView.apply {
            setHasFixedSize(true)
            adapter = topLearnerSkillAdapter
        }
    }

}