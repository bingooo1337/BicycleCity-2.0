package com.diploma.volodymyr.bicyclecity.ui.activity.navigation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Menu
import android.view.MenuItem
import com.diploma.volodymyr.bicyclecity.presentation.view.navigation.MenuView
import com.diploma.volodymyr.bicyclecity.presentation.presenter.navigation.MenuPresenter
import com.arellomobile.mvp.presenter.InjectPresenter
import com.diploma.volodymyr.bicyclecity.R
import com.diploma.volodymyr.bicyclecity.ui.activity.auth.SignInActivity
import com.diploma.volodymyr.bicyclecity.ui.activity.auth.SignUpActivity
import com.diploma.volodymyr.bicyclecity.ui.activity.base.BaseActivity
import com.diploma.volodymyr.bicyclecity.ui.fragment.NewsFragment
import kotlinx.android.synthetic.main.activity_menu.*
import kotlinx.android.synthetic.main.app_bar_menu.*

class MenuActivity : BaseActivity(), MenuView, NavigationView.OnNavigationItemSelectedListener {

    companion object {
        val TAG = SignUpActivity::class.java.simpleName
        fun getIntent(context: Context) = Intent(context, MenuActivity::class.java)
    }

    @InjectPresenter
    lateinit var mMenuPresenter: MenuPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        initView()
    }

    override fun openNews() {
//        supportFragmentManager.beginTransaction()
//                .add(R.id.fragment_container, NewsFragment(), "NEWS")
//                .addToBackStack(null)
//                .commit()
    }

    override fun openGroupRides() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun openCompetitions() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun openMap() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun openSettings() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun openSignIn() {
        startActivity(SignInActivity.getIntent(this))
        finish()
    }

    override fun setTitle(title: String) {
        this.title = title
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        mMenuPresenter.optionsItemClicked(item.itemId)
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        mMenuPresenter.menuItemClicked(item)
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun initView() {
        setSupportActionBar(toolbar)
        nav_view.setNavigationItemSelectedListener(this)
        onNavigationItemSelected(nav_view.menu.findItem(R.id.nav_news))

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
    }
}
