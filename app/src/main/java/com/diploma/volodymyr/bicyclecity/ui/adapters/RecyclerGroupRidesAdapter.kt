package com.diploma.volodymyr.bicyclecity.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.diploma.volodymyr.bicyclecity.Const.GROUP_RIDE_DATEFORMAT
import com.diploma.volodymyr.bicyclecity.R
import com.diploma.volodymyr.bicyclecity.data.objects.GroupRide
import com.diploma.volodymyr.bicyclecity.inflate
import kotlinx.android.synthetic.main.recycler_group_rides_item.view.*
import kotlin.collections.ArrayList
import kotlin.math.roundToInt

class RecyclerGroupRidesAdapter(rides: List<GroupRide> = ArrayList(), val listener: (GroupRide) -> Unit)
    : RecyclerView.Adapter<RecyclerGroupRidesAdapter.GroupRideViewHolder>() {

    var rides: List<GroupRide> = rides
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
                    map_image.setImageDrawable(context.getDrawable(R.drawable.map))
                    title.text = item.title
                    date_time.text = GROUP_RIDE_DATEFORMAT.format(item.date)
                    distance.text = context.getString(R.string.km_placeholder, (item.distance / 100).roundToInt() / 10.0)
                    time.text = context.getString(R.string.mins_placeholder, item.approximateTime)
                    setOnClickListener { listener(item) }
                }
    }
}