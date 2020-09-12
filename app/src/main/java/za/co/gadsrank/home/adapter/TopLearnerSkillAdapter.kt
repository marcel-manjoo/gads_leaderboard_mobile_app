package za.co.gadsrank.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import kotlinx.android.synthetic.main.learning_leaders_item.view.*
import za.co.gadsrank.R
import za.co.gadsrank.home.model.TopLearnerHours
import za.co.gadsrank.home.model.TopLearnerSkillIq

class TopLearnerSkillAdapter(private val items: List<TopLearnerSkillIq>):
    RecyclerView.Adapter<TopLearnerSkillAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.skill_iq_leaders_item, parent, false)
        return ViewHolder(
            inflatedView
        )
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bindItem(item)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItem(topLearnerSkillIq: TopLearnerSkillIq) {
            val nameMTV = itemView.name as MaterialTextView
            val dataMTV = itemView.data as MaterialTextView

            nameMTV.text = topLearnerSkillIq.name
            val skill = topLearnerSkillIq.score.toString()
            dataMTV.text = skill + " skill IQ Score, "+ topLearnerSkillIq.country
        }
    }

}