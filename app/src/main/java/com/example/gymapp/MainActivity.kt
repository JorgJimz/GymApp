package com.example.gymapp

import android.os.Bundle
import android.preference.PreferenceManager
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.gymapp.fragments.AsistenciaFragment
import com.example.gymapp.fragments.HomeFragment
import com.example.gymapp.fragments.MasterClassesFragment
import com.example.gymapp.fragments.MembresiaFragment
import com.example.gymapp.model.Usuario
import com.google.android.material.navigation.NavigationView
import com.google.gson.Gson


class MainActivity : AppCompatActivity() {

    private lateinit var toogle: ActionBarDrawerToggle


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val vDrawerLayout: DrawerLayout = findViewById(R.id.gymDrawerLayout)
        val vNavView: NavigationView = findViewById(R.id.gymNavigationView)
        toogle =
            ActionBarDrawerToggle(this, vDrawerLayout, R.string.open_drawer, R.string.close_drawer)
        vDrawerLayout.addDrawerListener(toogle)
        toogle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        vNavView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_inicio -> {
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.gymFragmentContainerView, HomeFragment())
                        commit()
                    }
                }
                R.id.nav_clases_grupales -> {
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.gymFragmentContainerView, MasterClassesFragment())
                        commit()
                    }
                }
                R.id.nav_membresia -> {
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.gymFragmentContainerView, MembresiaFragment())
                        commit()
                    }
                }
                R.id.nav_asistencia -> {
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.gymFragmentContainerView, AsistenciaFragment())
                        commit()
                    }
                }
            }
            vDrawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        val settings = PreferenceManager.getDefaultSharedPreferences(
            applicationContext
        )

        val header : View = vNavView.getHeaderView(0)
        val txtHdUserName: TextView = header.findViewById(R.id.txtHdUsername)
        val txtHdEmail: TextView = header.findViewById(R.id.txtHdEmail)

        val userLogged : Usuario  = Gson().fromJson(settings.getString("logged", null), Usuario::class.java)

        txtHdUserName.text = userLogged.Nombres.plus(" ").plus(userLogged.Apellidos)
        txtHdEmail.text = userLogged.Email

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toogle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}