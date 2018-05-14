package com.diploma.volodymyr.bicyclecity.ui.adapters.news

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.diploma.volodymyr.bicyclecity.R
import com.diploma.volodymyr.bicyclecity.common.getFormattedDateString
import com.diploma.volodymyr.bicyclecity.common.inflate
import com.diploma.volodymyr.bicyclecity.data.objects.FeedObject
import kotlinx.android.synthetic.main.recycler_feed_item.view.*

class RecyclerFeedAdapter(private val listener: (FeedObject) -> Unit) :
        RecyclerView.Adapter<RecyclerFeedAdapter.FeedViewHolder>() {

    var feedList: List<FeedObject> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder =
            FeedViewHolder(parent.inflate(R.layout.recycler_feed_item))

    override fun getItemCount(): Int = feedList.size

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        holder.bind(feedList[holder.adapterPosition], listener)
    }

    class FeedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: FeedObject, listener: (FeedObject) -> Unit) =
                with(itemView) {
                    feed_object_title.text = item.title
                    feed_object_description.text = item.description
                    feed_object_date.text = item.date.getFormattedDateString()
                    Glide.with(itemView.context).load(item.image).into(feed_object_image)
                    setOnClickListener { listener(item) }
                }
    }
}