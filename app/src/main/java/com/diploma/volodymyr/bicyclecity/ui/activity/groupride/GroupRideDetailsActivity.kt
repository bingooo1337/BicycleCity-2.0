package com.diploma.volodymyr.bicyclecity.ui.activity.groupride

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.MenuItem
import com.diploma.volodymyr.bicyclecity.R
import com.diploma.volodymyr.bicyclecity.ui.activity.base.BaseActivity
import kotlinx.android.synthetic.main.activity_group_ride_details.*

class GroupRideDetailsActivity : BaseActivity() {

    companion object {
        private const val GROUP_RIDE_TITLE = "group_ride_title"
        private const val GROUP_RIDE_ID = "group_ride_id"

        fun getIntent(context: Context, groupRideTitle: String, groupRideId: String) =
                Intent(context, GroupRideDetailsActivity::class.java)
                        .apply {
                            putExtra(GROUP_RIDE_TITLE, groupRideTitle)
                            putExtra(GROUP_RIDE_ID, groupRideId)
                        }
    }

    private lateinit var groupRideId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_ride_details)
        setSupportActionBar(toolbar)
        title = intent.getStringExtra(GROUP_RIDE_TITLE)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return true
    }

}
