package com.diploma.volodymyr.bicyclecity.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.diploma.volodymyr.bicyclecity.common.Const.GROUP_RIDE_DATE_FORMAT
import com.diploma.volodymyr.bicyclecity.R
import com.diploma.volodymyr.bicyclecity.common.getStaticMapPath
import com.diploma.volodymyr.bicyclecity.data.objects.GroupRide
import com.diploma.volodymyr.bicyclecity.common.inflate
import kotlinx.android.synthetic.main.recycler_group_rides_item.view.*
import kotlin.math.roundToInt

class RecyclerGroupRidesAdapter(private val listener: (GroupRide) -> Unit)
    : RecyclerView.Adapter<RecyclerGroupRidesAdapter.GroupRideViewHolder>() {

    var rides: List<GroupRide> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            GroupRideViewHolder(parent.inflate(R.layout.recycler_group_rides_item))

    override fun getItemCount() = rides.size

    override fun onBindViewHolder(holder: GroupRideViewHolder, position: Int) =
            holder.bind(rides[position], listener)

    class GroupRideViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: GroupRide, listener: (GroupRide) -> Unit) =
                with(itemView) {
                    title.text = item.title
                    date_time.text = GROUP_RIDE_DATE_FORMAT.format(item.date)
                    distance.text = context.getString(R.string.km_placeholder, item.distance / 1000.0)
                    time.text = context.getString(R.string.mins_placeholder, item.approximateTime)
                    Glide.with(itemView.context).load(item.getStaticMapPath()).into(map_image)
                    setOnClickListener { listener(item) }
                }
    }
}