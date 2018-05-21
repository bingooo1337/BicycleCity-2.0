package com.diploma.volodymyr.bicyclecity.ui.activity.competition

import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import com.arellomobile.mvp.presenter.InjectPresenter
import com.diploma.volodymyr.bicyclecity.R
import com.diploma.volodymyr.bicyclecity.common.*
import com.diploma.volodymyr.bicyclecity.presentation.presenter.competition.impl.CreateCompetitionPresenter
import com.diploma.volodymyr.bicyclecity.presentation.view.competition.CreateCompetitionView
import com.diploma.volodymyr.bicyclecity.ui.activity.base.BaseActivity
import kotlinx.android.synthetic.main.activity_create_competition.*
import java.util.*

class CreateCompetitionActivity : BaseActivity(), CreateCompetitionView {

    companion object {
        fun getIntent(context: Context) = Intent(context, CreateCompetitionActivity::class.java)
    }

    @InjectPresenter
    lateinit var presenter: CreateCompetitionPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_competition)

        initView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.continue_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.continue_creating -> {
            }
            android.R.id.home -> finish()
        }
        return true
    }

    override fun showPrivateCode(code: String) {
        access_code.text = getString(R.string.access_code_placeholder, code)
        access_code.setVisible()
        copy_code_button.setVisible()
    }

    override fun hidePrivateCode() {
        access_code.setGone()
        copy_code_button.setGone()
    }

    override fun showPrizeEditText() {
        prize_input_layout.setVisible()
    }

    override fun hidePrizeEditText() {
        prize_input_layout.setGone()
    }

    override fun showDatePicker() {
        date_picker.setVisible()
        time_picker.setGone()
        select_button.setVisible()
        select_button.requestFocus()
    }

    override fun showTimePicker() {
        date_picker.setGone()
        time_picker.setVisible()
        select_button.setVisible()
        select_button.requestFocus()
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

    override fun setErrorHints(titleError: String, descError: String) {
        title_input_layout.error = titleError
        description_input_layout.error = descError
    }

    override fun goToNextScreen() {
    }

    private fun initView() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        time_picker.setIs24HourView(true)
        with(Calendar.getInstance().time) {
            date_text.text = getFormattedDateString()
            time_text.text = getTimeString()
        }

        private_checkbox.setOnCheckedChangeListener { _, isChecked ->
            presenter.onIsPrivateChanged(isChecked)
        }
        prize_checkbox.setOnCheckedChangeListener { _, isChecked ->
            presenter.addPrizeClicked(isChecked)
        }
        copy_code_button.setOnClickListener {
            presenter.onCopyCodeClicked(getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager)
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

        ArrayAdapter.createFromResource(this,
                R.array.bicycle_types, android.R.layout.simple_spinner_item)
                .let {
                    it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    bicycle_type_spinner.adapter = it
                }
    }
}
