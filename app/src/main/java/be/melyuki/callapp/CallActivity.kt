package be.melyuki.callapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat

class CallActivity : AppCompatActivity() {

    lateinit var num : EditText
    lateinit var btnCall : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_call)

        num = findViewById(R.id.et_info_numero)
        btnCall = findViewById(R.id.btn_call)

        btnCall.setOnClickListener { callProcess() }
    }

    private fun callProcess() {
        if(!checkCallPermission()) {
            return
        }

        // Récuperation du numéro de tel (format Uri)
        val phoneNumber = num.text.toString()
        val uri : Uri = Uri.parse(getString(R.string.uri_tel).format(phoneNumber))

        // Création d'un intent pour demander à Android d'utiliser l'app "Telephone"
        val intent = Intent(Intent.ACTION_CALL, uri)

        // Envoi de l'intent à Android
        startActivity(intent)
    }

    val callPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { it ->
        if(it) {
            callProcess()
        }
        else {
            Toast.makeText(this, R.string.toast_no_call_permission, Toast.LENGTH_LONG).show()
        }
    }

    private fun checkCallPermission(): Boolean {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            return true
        }

        callPermissionLauncher.launch(Manifest.permission.CALL_PHONE)
        return false
    }
}