package com.lagradost

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.lagradost.cloudstream3.AcraApplication.Companion.openBrowser
import com.lagradost.cloudstream3.R.drawable.nginx
import com.lagradost.cloudstream3.R.drawable.nginx_question
import com.lagradost.cloudstream3.R.string.*
import com.lagradost.cloudstream3.plugins.Plugin
import com.lagradost.cloudstream3.ui.settings.SettingsAccount.Companion.showLoginInfo
import com.lagradost.cloudstream3.ui.settings.SettingsAccount.Companion.addAccount
import com.lagradost.databinding.NginxSettingsBinding

class NginxSettingsFragment(private val plugin: Plugin, val nginxApi: NginxApi) :
    BottomSheetDialogFragment() {

    private lateinit var binding: NginxSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val id = plugin.resources!!.getIdentifier("nginx_settings", "layout", "com.lagradost")
        val layout = plugin.resources!!.getLayout(id)
        return inflater.inflate(layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = NginxSettingsBinding.inflate(layoutInflater)


        super.onViewCreated(view, savedInstanceState)
//        val infoView = view.findViewById<LinearLayout>(R.id.nginx_info)
//        val infoTextView = view.findViewById<TextView>(R.id.info_main_text)
//        val infoSubTextView = view.findViewById<TextView>(R.id.info_sub_text)
//        val infoImageView = view.findViewById<ImageView>(R.id.nginx_info_imageview)

        binding.infoMainText.text = getString(nginx_info_title)
        binding.infoSubText.text = getString(nginx_info_summary)
        binding.nginxInfoImageview.setImageResource(nginx_question)

//        val loginView = view.findViewById<LinearLayout>(R.id.nginx_login)
//        val loginTextView = view.findViewById<TextView>(R.id.main_text)
//        val loginImageView = view.findViewById<ImageView>(R.id.nginx_login_imageview)
        binding.nginxLoginImageview.setImageResource(nginx)

        // object : View.OnClickListener is required to make it compile because otherwise it used invoke-customs
        binding.nginxInfo.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                openBrowser(nginxApi.createAccountUrl)
            }
        })


        binding.mainText.text = getString(login_format).format(nginxApi.name, getString(account))
        binding.nginxLogin.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val info = nginxApi.loginInfo()
                if (info != null) {
                    showLoginInfo(activity, nginxApi, info)
                } else {
                    addAccount(activity, nginxApi)
                }
            }
        })
    }
}