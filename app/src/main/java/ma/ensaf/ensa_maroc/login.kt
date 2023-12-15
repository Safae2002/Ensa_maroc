package ma.ensaf.ensa_maroc

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val email: TextInputEditText
        val text:TextView
         val auth:FirebaseAuth
         auth=FirebaseAuth.getInstance()
        email=findViewById(R.id.email)
        text=findViewById(R.id.text1)
        text.setOnClickListener {
            val intent = Intent(this, inscrire::class.java)
            startActivity(intent)
        }
        val conecter: Button
        conecter=findViewById(R.id.Valider)
        val password: TextInputEditText
        password=findViewById(R.id.password)

        conecter.setOnClickListener {
            if(email.equals("") || password.equals(""))
            {
                Toast.makeText(this,"Remplir tous les champs",Toast.LENGTH_LONG).show()
            }
            auth.signInWithEmailAndPassword(email.text.toString(), password.text.toString())
                .addOnCompleteListener(this) {task ->

                    if (task.isSuccessful) {

                        Log.d(TAG, "createUserWithEmailAndPassword:success")
                        Toast.makeText(
                            baseContext,
                            "Authentication succes: ",
                            Toast.LENGTH_SHORT
                        ).show()

                            val intent = Intent(this, MapActivity::class.java)
                            startActivity(intent)

                    } else {
                        // Authentification échouée
                        Log.w(TAG, "createUserWithEmailAndPassword:failure", task.exception)
                        Toast.makeText(
                            baseContext,
                            "Authentication failed: " + task.exception?.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

        }
        }

}