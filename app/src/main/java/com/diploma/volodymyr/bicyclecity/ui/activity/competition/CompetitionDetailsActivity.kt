package com.diploma.volodymyr.bicyclecity.ui.activity.competition

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.diploma.volodymyr.bicyclecity.R
import com.diploma.volodymyr.bicyclecity.common.*
import com.diploma.volodymyr.bicyclecity.common.Const.CAMERA_PADDING
import com.diploma.volodymyr.bicyclecity.data.objects.User
import com.diploma.volodymyr.bicyclecity.data.objects.competition.BicycleType
import com.diploma.volodymyr.bicyclecity.data.objects.competition.Competition
import com.diploma.volodymyr.bicyclecity.data.objects.competition.TrainingLevel
import com.diploma.volodymyr.bicyclecity.presentation.presenter.competition.impl.CompetitionDetailsPresenter
import com.diploma.volodymyr.bicyclecity.presentation.view.competition.CompetitionDetailsView
import com.diploma.volodymyr.bicyclecity.ui.activity.base.BaseActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.activity_competition_details.*

class CompetitionDetailsActivity : BaseActivity(), CompetitionDetailsView, OnMapReadyCallback {

    companion object {
        private const val COMPETITION_TITLE = "group_ride_title"
        private const val COMPETITION_ID = "competition_id"

        fun getIntent(context: Context, competitionTitle: String, competitionId: String) =
                Intent(context, CompetitionDetailsActivity::class.java).apply {
                    putExtra(COMPETITION_ID, competitionId)
                    putExtra(COMPETITION_TITLE, competitionTitle)
                }
    }

    @InjectPresenter
    lateinit var presenter: CompetitionDetailsPresenter
    private lateinit var competitionId: String
    private lateinit var map: GoogleMap

    @ProvidePresenter
    fun providePresenter() = CompetitionDetailsPresenter(competitionId)

    override fun onCreate(savedInstanceState: Bundle?) {
        competitionId = intent.getStringExtra(COMPETITION_ID)
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_competition_details)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = intent.getStringExtra(COMPETITION_TITLE)

        (supportFragmentManager.findFragmentById(R.id.competition_details_map) as SupportMapFragment)
                .getMapAsync(this)

        initView()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return true
    }

    override fun showLoading() {
        competition_details_progress.setVisible()
    }

    override fun hideLoading() {
        competition_details_progress.setInvisible()
    }

    override fun enableJoinButton() {
        fab_join.setVisible()
        fab_join.isEnabled = true
    }

    override fun disableJoinButton() {
        fab_join.isEnabled = false
    }

    override fun showDoneButton() {
        disableJoinButton()
        fab_join.setVisible()
        fab_join.setImageDrawable(getDrawable(R.drawable.ic_done))
    }

    override fun showReadyButton(text: String) {
        ready_button.text = text
        ready_button.setVisible()
    }

    override fun showReadyCount(readyCount: Int, usersCount: Int) {
        ready_tv.text = getString(R.string.ready_count, readyCount, usersCount)
        ready_tv.setVisible()
    }

    @SuppressLint("SetTextI18n")
    override fun showCompetitionDetails(competition: Competition) {
        competition.let {
            val trainingLevels = with(App.INSTANSE) {
                arrayOf(getString(R.string.beginner),
                        getString(R.string.advanced_beginner),
                        getString(R.string.amateur),
                        getString(R.string.everyday_rider),
                        getString(R.string.professional))
            }
            val bicycleTypes = App.INSTANSE.resources.getStringArray(R.array.bicycle_types)
            competition_description_tv.text = it.description
            competition_distance_tv.text = getString(R.string.km_placeholder, it.distance / 1000.0)
            competition_date_tv.text = it.date?.getFormattedDateString()
            competition_time_tv.text = it.date?.getTimeString()
            bicycle_type_tv.text = getString(R.string.recommended_bicycle_type) + " " +
                    when (it.bicycleType) {
                        BicycleType.ROAD -> bicycleTypes[0]
                        BicycleType.MOUNTAIN -> bicycleTypes[1]
                        BicycleType.TRACK -> bicycleTypes[2]
                        BicycleType.TIME_TRIAL -> bicycleTypes[3]
                    }
            training_level_tv.text = getString(R.string.recommended_training_level) + " " +
                    when (it.trainingLevel) {
                        TrainingLevel.BEGINNER -> trainingLevels[0]
                        TrainingLevel.ADVANCED_BEGINNER -> trainingLevels[1]
                        TrainingLevel.AMATEUR -> trainingLevels[2]
                        TrainingLevel.EVERYDAY_RIDER -> trainingLevels[3]
                        TrainingLevel.PROFESSIONAL -> trainingLevels[4]
                    }
            if (it.prize != null) {
                prize_tv.text = it.prize
                prize_tv.setVisible()
            }
        }
    }

    override fun showCreatorInfo(user: User) {
        competition_creator_name_tv.text =
                getString(R.string.user_full_name_placeholder, user.firstName, user.lastName)
        competition_creator_email_tv.text = user.email
        competition_creator_number_tv.text = user.mobileNumber
    }

    override fun showMarkers(start: MarkerOptions, finish: MarkerOptions) {
        map.addMarker(start)
        map.addMarker(finish)

        val bounds = LatLngBounds.Builder().include(start.position).include(finish.position).build()
        val displayWidth = windowManager.getDisplayWidth()
        map.animateCamera(CameraUpdateFactory
                .newLatLngBounds(bounds, displayWidth, (displayWidth * 0.6).toInt(), CAMERA_PADDING))
    }

    override fun showUsersList(userNames: String) {
        competition_riders_list_tv.text = userNames
    }

    override fun startTrackingLocation(competitionId: String) {
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
    }

    private fun initView() {
        fab_join.setOnClickListener { presenter.onJoinClicked() }
        ready_button.setOnClickListener { presenter.onReadyClicked() }
    }
}
