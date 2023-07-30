package be.melyuki.callapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity(), View.OnClickListener {

    // Décla d'une constante via un companion object qui sera utilisé dans loginActivity
    companion object {
        const val EXTRA_USERNAME = "username"
    }

    lateinit var btnInfo : Button
    lateinit var btnTel : Button

    private lateinit var username : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Récup du username de l'intent de la loginActivity
//        val username : String? = intent.getStringExtra(EXTRA_USERNAME)
        username = intent.getStringExtra(EXTRA_USERNAME) ?: getString(R.string.msg_main_unknown_username)

        // Toast de bienvenue
        Toast.makeText(this, getString(R.string.toast_msg_main_welcome).format(username), Toast.LENGTH_LONG).show()

        // Binding
        btnInfo = findViewById(R.id.btn_main_info)
        btnTel = findViewById(R.id.btn_main_tel)

        // Listener
        btnInfo.setOnClickListener(this)
        btnTel.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when(v?.id) {
//            R.id.btn_main_info -> goToInfo()
//            R.id.btn_main_tel -> goToCall()
            R.id.btn_main_info -> goToActivity(InfoActivity::class.java)
            R.id.btn_main_tel -> goToActivity(CallActivity::class.java)
            else -> Toast.makeText(this, getString(R.string.msg_btn_error), Toast.LENGTH_LONG).show()
        }
    }

    private fun goToActivity(classe: Class<*>) {
        val intent = Intent(this, classe)
        startActivity(intent)
    }

//
//    private fun goToCall() {
//        val intentInfo = Intent(this, CallActivity::class.java)
//        startActivity(intentInfo)
//    }
//
//    private fun goToInfo() {
//        val intentCall = Intent(this, InfoActivity::class.java)
//        startActivity(intentCall)
//    }
}