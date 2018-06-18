package com.diploma.volodymyr.bicyclecity.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.diploma.volodymyr.bicyclecity.R
import com.diploma.volodymyr.bicyclecity.common.App
import com.diploma.volodymyr.bicyclecity.common.Const.GROUP_RIDE_DATE_FORMAT
import com.diploma.volodymyr.bicyclecity.common.inflate
import com.diploma.volodymyr.bicyclecity.data.objects.competition.BicycleType
import com.diploma.volodymyr.bicyclecity.data.objects.competition.Competition
import com.diploma.volodymyr.bicyclecity.data.objects.competition.TrainingLevel
import kotlinx.android.synthetic.main.recycler_competitions_item.view.*
import kotlin.math.roundToInt

class RecyclerCompetitionsAdapter(private val listener: (Competition) -> Unit) :
        RecyclerView.Adapter<RecyclerCompetitionsAdapter.CompetitionsViewHolder>() {

    var competitions: List<Competition> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    private val trainingLevels = with(App.INSTANSE) {
        arrayOf(getString(R.string.beginner),
                getString(R.string.advanced_beginner),
                getString(R.string.amateur),
                getString(R.string.everyday_rider),
                getString(R.string.professional))
    }
    private val bicycleTypes = App.INSTANSE.resources.getStringArray(R.array.bicycle_types)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            CompetitionsViewHolder(parent.inflate(R.layout.recycler_competitions_item))

    override fun getItemCount() = competitions.size

    override fun onBindViewHolder(holder: CompetitionsViewHolder, position: Int) =
            holder.bind(competitions[position], listener, trainingLevels, bicycleTypes)


    class CompetitionsViewHolder(itemView: View) :
            RecyclerView.ViewHolder(itemView) {

        fun bind(item: Competition, listener: (Competition) -> Unit,
                 trainingLevels: Array<String>, bicycleTypes: Array<String>) {

            with(itemView) {
                title.text = item.title
                date_time.text = GROUP_RIDE_DATE_FORMAT.format(item.date)
                distance.text = context.getString(R.string.km_placeholder, item.distance / 1000.0)
                riders_count.text = item.users.size.toString()
                level.text = when (item.trainingLevel) {
                    TrainingLevel.BEGINNER -> trainingLevels[0]
                    TrainingLevel.ADVANCED_BEGINNER -> trainingLevels[1]
                    TrainingLevel.AMATEUR -> trainingLevels[2]
                    TrainingLevel.EVERYDAY_RIDER -> trainingLevels[3]
                    TrainingLevel.PROFESSIONAL -> trainingLevels[4]
                }
                type.text = when (item.bicycleType) {
                    BicycleType.ROAD -> bicycleTypes[0]
                    BicycleType.MOUNTAIN -> bicycleTypes[1]
                    BicycleType.TRACK -> bicycleTypes[2]
                    BicycleType.TIME_TRIAL -> bicycleTypes[3]
                }
                setOnClickListener { listener(item) }
            }
        }
    }
}