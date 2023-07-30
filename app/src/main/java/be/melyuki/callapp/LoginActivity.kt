package be.melyuki.callapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var inputFormUser : EditText
    lateinit var inputFormPwd : EditText
    lateinit var btnValid : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        inputFormUser = findViewById(R.id.et_login_username)
        inputFormPwd = findViewById(R.id.et_login_password)
        btnValid = findViewById(R.id.btn_login)

        btnValid.setOnClickListener(this)
        // Aussi possible...
//        btnValid.setOnClickListener { goToMain() }
        // Grâce à ça:
        // - Pas besoin de ", View.OnClickListener"
        // - Pas de "fun onClick" à implémenter

        inputFormUser.addTextChangedListener {
                text -> lockLoginBtn()
        }
        inputFormPwd.addTextChangedListener {
                text -> lockLoginBtn()
        }

    }

    private fun lockLoginBtn() {

        val userData : String = inputFormUser.text.toString()
        val pwdData : String = inputFormPwd.text.toString()

        btnValid.isEnabled = !(userData.isBlank() || pwdData.isEmpty())
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_login -> goToMain()
            else -> Toast.makeText(this, getString(R.string.msg_btn_error), Toast.LENGTH_LONG).show()
        }
    }

    private fun goToMain() {

        val userData : String = inputFormUser.text.toString()
        val pwdData : String = inputFormPwd.text.toString()

        if( userData == getString(R.string.valid_username) && pwdData == getString(R.string.valid_password)) {
            Toast.makeText(this, R.string.msg_btn_valid, Toast.LENGTH_LONG).show()
            navigate(userData)
        }
        else {
            Toast.makeText(this, R.string.msg_btn_not_valid, Toast.LENGTH_LONG).show()

            inputFormPwd.text.clear()
            inputFormUser.requestFocus()
        }
    }

    private fun navigate(username : String) {

        val intentMain : Intent = Intent(this, MainActivity::class.java).apply {
            putExtra(MainActivity.EXTRA_USERNAME, username)
        }
        startActivity(intentMain)
        finish()
        // la touche "back" du tel, grâce à finish() :
        // - Ne fait pas revenir à thisActivity
        // - Ferme l'app
        // - La laisse en arrière plan
        // - Ramène à thisActivity à la réouverture
    }
}
