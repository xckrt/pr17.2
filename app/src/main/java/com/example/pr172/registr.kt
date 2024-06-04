package com.example.pr172
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
class registr : AppCompatActivity() {
    private lateinit var loginEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var enterButton: Button
    private val validLogin = "xckrt"
    private val validPassword = "12345678"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registr)
        loginEditText = findViewById(R.id.login)
        passwordEditText = findViewById(R.id.pass)
        enterButton = findViewById(R.id.enter_button)
    }
    fun main(view: View) {
        val login = loginEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()
        if (login.isEmpty()) {
            Toast.makeText(this, R.string.login_empty_error, Toast.LENGTH_SHORT).show()
            return
        }
        if (password.isEmpty()) {
            Toast.makeText(this, R.string.password_empty_error, Toast.LENGTH_SHORT).show()
            return
        }
        if (login != validLogin || password != validPassword) {
            Toast.makeText(this, R.string.invalid_credentials_error, Toast.LENGTH_SHORT).show()
            return
        }
        Toast.makeText(this, R.string.validation_success, Toast.LENGTH_SHORT).show()
        val intent = Intent(this,main::class.java)
        startActivity(intent)
    }
}