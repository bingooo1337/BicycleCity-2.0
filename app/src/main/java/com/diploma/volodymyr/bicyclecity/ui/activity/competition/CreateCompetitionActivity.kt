package com.diploma.volodymyr.bicyclecity.ui.activity.competition

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import com.diploma.volodymyr.bicyclecity.R
import com.diploma.volodymyr.bicyclecity.common.getFormattedDateString
import com.diploma.volodymyr.bicyclecity.common.getTimeString
import kotlinx.android.synthetic.main.activity_create_competition.*
import java.util.*

class CreateCompetitionActivity : AppCompatActivity() {

    companion object {
        fun getIntent(context: Context) = Intent(context, CreateCompetitionActivity::class.java)
    }

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

    private fun initView() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        with(Calendar.getInstance().time) {
            date_text.text = getFormattedDateString()
            time_text.text = getTimeString()
        }

        ArrayAdapter.createFromResource(this,
                R.array.bicycle_types, android.R.layout.simple_spinner_item)
                .let {
                    it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    bicycle_type_spinner.adapter = it
                }
    }
}
