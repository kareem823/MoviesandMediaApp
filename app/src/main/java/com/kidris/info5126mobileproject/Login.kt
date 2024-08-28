package com.kidris.info5126mobileproject

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CheckBox
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.kidris.info5126mobileproject.R
import com.kidris.info5126mobileproject.databinding.ActivityLoginBinding
import com.kidris.info5126mobileproject.DataClasses.UserData
import dagger.hilt.android.AndroidEntryPoint

/*
* Kareem Idris
* Mobile dev: Movie App
* Due: Dec 11, 2023
* */


@AndroidEntryPoint
class Login : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    private lateinit var rememberMeCheckBox: CheckBox
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.reference.child("users")

      //  rememberMeCheckBox = findViewById(R.id.rememberMeCheckBox)
        sharedPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE)

        // Restore "Remember Me" state from SharedPreferences
        //val rememberMeChecked = sharedPreferences.getBoolean("rememberMe", false)
        //rememberMeCheckBox.isChecked = rememberMeChecked

        binding.loginButton.setOnClickListener {
            val loginUsername = binding.loginUsername.text.toString()
            val loginPassword = binding.loginPassword .text.toString()

            if (loginUsername.isNotEmpty() && loginPassword.isNotEmpty()){
                loginUser(loginUsername, loginPassword)
                // Save "Remember Me" state in SharedPreferences
                val editor = sharedPreferences.edit()
                //editor.putBoolean("rememberMe", rememberMeCheckBox.isChecked)
                editor.apply()
            }

            else{
                Toast.makeText(this@Login, "All fields Mandatory", Toast.LENGTH_SHORT).show()

            }
        }

        binding.loginRedirect .setOnClickListener{
            startActivity(Intent(this@Login, Register::class.java))
            finish()
        }

    }

    //for the user to login
    private fun loginUser(username: String, password: String){
        databaseReference.orderByChild("username").equalTo(username).addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()){
                    for (userSnapshot in dataSnapshot.children){
                        //store the data
                        val userData = userSnapshot.getValue(UserData::class.java)

                        //taking credentials of the user through the snapshot
                        if (userData !== null && userData.password == password){
                            Toast.makeText(this@Login, "Login Successful", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this@Login, MainActivity::class.java))
                            finish()
                            return
                        }
                    }
                }
                Toast.makeText(this@Login, "Login failed", Toast.LENGTH_SHORT).show()

            }//end onDataChange

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@Login, "DataBase Error: ${error.message}", Toast.LENGTH_SHORT).show()

            }

        })
    }//end userLogin

    //if first attempt doesn't work then try hashing so that the passwords match

}