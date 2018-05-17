package com.diploma.volodymyr.bicyclecity.ui.activity.groupride

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_create_group_ride.*
import android.os.Build
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import com.arellomobile.mvp.presenter.InjectPresenter
import com.diploma.volodymyr.bicyclecity.*
import com.diploma.volodymyr.bicyclecity.common.getFormattedDateString
import com.diploma.volodymyr.bicyclecity.common.getTimeString
import com.diploma.volodymyr.bicyclecity.common.setGone
import com.diploma.volodymyr.bicyclecity.common.setVisible
import com.diploma.volodymyr.bicyclecity.data.objects.GroupRide
import com.diploma.volodymyr.bicyclecity.presentation.presenter.groupride.impl.CreateGroupRidePresenter
import com.diploma.volodymyr.bicyclecity.presentation.view.groupride.CreateGroupRideView
import com.diploma.volodymyr.bicyclecity.ui.activity.base.BaseActivity
import java.util.*

class CreateGroupRideActivity : BaseActivity(), CreateGroupRideView {

    companion object {
        private const val NEXT_SCREEN_REQUEST_CODE = 1
        fun getIntent(context: Context) = Intent(context, CreateGroupRideActivity::class.java)
    }

    @InjectPresenter
    lateinit var presenter: CreateGroupRidePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_group_ride)

        initView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.continue_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.continue_creating -> presenter
                    .onContinueClicked(title_et.text.toString(),
                            description_et.text.toString(),
                            avg_speed_spinner.selectedItemPosition)
            android.R.id.home -> finish()
        }
        return true
    }

    override fun showDatePicker() {
        date_picker.setVisible()
        time_picker.setGone()
        select_button.setVisible()
    }

    override fun showTimePicker() {
        date_picker.setGone()
        time_picker.setVisible()
        select_button.setVisible()
    }

    override fun hidePickers() {
        date_picker.setGone()
        time_picker.setGone()
        select_button.setGone()
    }

    override fun setDate(date: String) {
        date_text.text = date
    }

    override fun setTime(time: String) {
        time_text.text = time
    }

    override fun goToNextScreen(groupRide: GroupRide) {
        startActivityForResult(CreateGroupRideRouteActivity.getIntent(this, groupRide),
                NEXT_SCREEN_REQUEST_CODE)
    }

    override fun setErrorHints(titleError: String, descError: String) {
        title_input_layout.error = titleError
        description_input_layout.error = descError
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == NEXT_SCREEN_REQUEST_CODE && resultCode == Activity.RESULT_OK) finish()
    }

    private fun initView() {
        time_picker.setIs24HourView(true)

        with(Calendar.getInstance().time) {
            date_text.text = getFormattedDateString()
            time_text.text = getTimeString()
        }
        date_text.setOnClickListener { presenter.onDateCLicked() }
        time_text.setOnClickListener { presenter.onTimeCLicked() }

        select_button.setOnClickListener {
            if (date_picker.visibility == View.VISIBLE)
                with(date_picker) { presenter.onSelectClicked(dayOfMonth, month, year) }
            else {
                if (Build.VERSION.SDK_INT >= 23)
                    with(time_picker) { presenter.onSelectClicked(hour, minute) }
                else
                    with(time_picker) { presenter.onSelectClicked(currentHour, currentMinute) }
            }
        }

        val arrayAdapter = ArrayAdapter.createFromResource(
                this, R.array.avg_speed,
                android.R.layout.simple_spinner_item)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        avg_speed_spinner.adapter = arrayAdapter

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
