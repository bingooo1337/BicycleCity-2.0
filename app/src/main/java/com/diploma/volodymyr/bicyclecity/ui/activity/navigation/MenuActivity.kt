package com.diploma.volodymyr.bicyclecity.ui.activity.navigation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Menu
import android.view.MenuItem
import com.diploma.volodymyr.bicyclecity.presentation.view.navigation.MenuView
import com.diploma.volodymyr.bicyclecity.presentation.presenter.navigation.MenuPresenter
import com.arellomobile.mvp.presenter.InjectPresenter
import com.diploma.volodymyr.bicyclecity.Const.GROUP_RIDES_FRAGMENT
import com.diploma.volodymyr.bicyclecity.R
import com.diploma.volodymyr.bicyclecity.ui.activity.auth.SignInActivity
import com.diploma.volodymyr.bicyclecity.ui.activity.base.BaseActivity
import com.diploma.volodymyr.bicyclecity.ui.activity.groupride.CreateGroupRideActivity
import com.diploma.volodymyr.bicyclecity.ui.fragment.GroupRidesFragment
import kotlinx.android.synthetic.main.activity_menu.*
import kotlinx.android.synthetic.main.app_bar_menu.*

class MenuActivity : BaseActivity(), MenuView, NavigationView.OnNavigationItemSelectedListener {

    companion object {
        val TAG = MenuActivity::class.java.simpleName
        fun getIntent(context: Context) = Intent(context, MenuActivity::class.java)
    }

    @InjectPresenter
    lateinit var mMenuPresenter: MenuPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        initView()
    }

    override fun openFragment(fragment: Fragment, tag: String) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment, tag)
                .addToBackStack(null)
                .commit()
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

        fab.setOnClickListener {
            if (supportFragmentManager.findFragmentById(R.id.fragment_container) is GroupRidesFragment)
                startActivity(CreateGroupRideActivity.getIntent(this))
        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
    }
}
