package ma.ensaf.ensa_maroc

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class inscrire : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inscrire)
        val email: TextInputEditText
        val confirm:TextInputEditText
        val conecter: Button
        conecter=findViewById(R.id.Valider)
        val password: TextInputEditText
        val text: TextView
        val auth: FirebaseAuth
        auth= FirebaseAuth.getInstance()
        email=findViewById(R.id.email)
        confirm=findViewById(R.id.confirm)
        text=findViewById(R.id.text1)
        text.setOnClickListener {
            val intent = Intent(this, login::class.java)
            startActivity(intent)
        }
        password=findViewById(R.id.password)

        conecter.setOnClickListener {
            val emailInput = email.text.toString()
            val passwordInput = password.text.toString()
            val confirmInput = confirm.text.toString()

            if (emailInput.isEmpty() || passwordInput.isEmpty() || confirmInput.isEmpty()) {
                Toast.makeText(this, "Remplir tous les champs", Toast.LENGTH_LONG).show()
            } else if (passwordInput != confirmInput) {
                Toast.makeText(this, "Les mots de passe ne correspondent pas", Toast.LENGTH_LONG).show()
            } else {
                auth.createUserWithEmailAndPassword(emailInput, passwordInput)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Log.d(ContentValues.TAG, "createUserWithEmailAndPassword:success")
                            Toast.makeText(baseContext, "création de compte avec sussé ", Toast.LENGTH_SHORT).show()
                        } else {
                            // Authentification échouée
                            Log.w(ContentValues.TAG, "createUserWithEmailAndPassword:failure", task.exception)
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
}