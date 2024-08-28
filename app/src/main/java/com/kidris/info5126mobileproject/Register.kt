package com.kidris.info5126mobileproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.kidris.info5126mobileproject.DataClasses.UserData
import com.kidris.info5126mobileproject.databinding.ActivityRegisterBinding
import java.security.MessageDigest
import java.security.SecureRandom

class Register : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //create data base instance
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.reference.child("users")

        binding.signupButton.setOnClickListener {
            val signupUsername = binding.signupUsername.text.toString()
            val signupPassword = binding.signupPassword.text.toString()

            if (signupUsername.isNotEmpty() && signupPassword.isNotEmpty()){
                signupUser(signupUsername, signupPassword)
            }
            else{
                Toast.makeText(this@Register, "All fields Mandatory", Toast.LENGTH_SHORT).show()

            }
        }

        binding.signupRedirect.setOnClickListener{
            startActivity(Intent(this@Register, Login::class.java))
            finish()
        }
    }

    // Function to generate a random salt
    private fun generateSalt(): ByteArray {
        val random = SecureRandom()
        val salt = ByteArray(16)
        random.nextBytes(salt)
        return salt
    }


    private fun signupUser(username: String, password: String ){


        //val id = databaseReference.push().key
        // Generate a salt for the user
        val salt = generateSalt()

        // Hash the password with the generated salt
        val hashedPassword = hashPassword(password, salt)

        // Convert hashed password to a hex string for storage
        val hashedPasswordHex = hashedPassword.joinToString("") { "%02x".format(it) }

        // Store the salt and hashed password
        //val userData = UserData(id, username, hashedPasswordHex, salt)

        databaseReference.orderByChild("username").equalTo(username).addListenerForSingleValueEvent(object : ValueEventListener {

            //datasnapshot is realtime data in the firebase
                override fun onDataChange(dataSnapshot: DataSnapshot){
                    if (!dataSnapshot.exists()){
                        //create the user object and send an onscreen message for success
                        val id = databaseReference.push().key
                       // val userData = UserData(id, username, hashedPasswordHex, salt)
                        val userData = UserData(id, username, password)

                        databaseReference.child(id!!).setValue(userData)
                        Toast.makeText(this@Register, "Signup Successful", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@Register, Login::class.java))
                        finish()
                    }
                    else{
                        Toast.makeText(this@Register, "User Already Exists", Toast.LENGTH_SHORT).show()
                    }
                }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@Register, "DataBase Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }

            })
    }

   // Function to hash a password using SHA-256 and a salt
    fun hashPassword(password: String, salt: ByteArray): ByteArray {

       // Get a MessageDigest instance for SHA-256
       val md = MessageDigest.getInstance("SHA-256")

       // Update the digest with the byte value of the salt
       md.update(salt)

       // Perform the hash computation and return the hash as a ByteArray
       return md.digest(password.toByteArray(Charsets.UTF_8))
    }


}