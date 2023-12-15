package ma.ensaf.ensa_maroc
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
class MainActivity : AppCompatActivity() {


    companion object {
        private const val RC_SIGN_IN = 9001
    }
    private lateinit var auth: FirebaseAuth
    private lateinit var callbackManager: CallbackManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()


        auth = FirebaseAuth.getInstance()
        callbackManager = CallbackManager.Factory.create()

        val button3 = findViewById<Button>(R.id.button3)
        button3.setOnClickListener {
            val intent = Intent(this, login::class.java)
            startActivity(intent)
        }
        // Configure Facebook login button
        val loginButton = findViewById<LoginButton>(R.id.login_button)
        loginButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                handleFacebookAccessToken(loginResult.accessToken)
            }

            override fun onCancel() {
                Toast.makeText(applicationContext, "Facebook login canceled", Toast.LENGTH_SHORT).show()
                // Vous pouvez ajouter ici d'autres actions à effectuer en cas d'annulation
            }

            override fun onError(error: FacebookException) {
                Toast.makeText(applicationContext, "Facebook login error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })

        val signInButton = findViewById<Button>(R.id.signInButton)
        signInButton.setOnClickListener {
            signIn()
        }
        val currentUser = auth.currentUser
        if (currentUser != null) {
            // User is signed in, display the name
            Toast.makeText(this, "Signed in as ${currentUser.displayName}", Toast.LENGTH_SHORT).show()
        }
    }
    private fun signIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(this, gso)
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)


        if (auth.currentUser != null) {
            // L'utilisateur est authentifié, déterminez la méthode d'authentification utilisée
            val user = auth.currentUser

            if (user?.email != null) {

                startActivity(Intent(this, MapActivity::class.java))
            } else if (user?.providerData?.any { it.providerId == GoogleAuthProvider.PROVIDER_ID } == true) {

                startActivity(Intent(this, MapActivity::class.java))
            } else if (user?.providerData?.any { it.providerId == FacebookAuthProvider.PROVIDER_ID } == true) {

                startActivity(Intent(this, MapActivity::class.java))
            }
        }

    }



    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    Toast.makeText(this, "Signed in as ${user?.displayName}", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show()
                }
            }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Pass the result to the Facebook SDK
        callbackManager.onActivityResult(requestCode, resultCode, data)

        // Continue with other onActivityResult logic (Google sign-in)
        if (requestCode == RC_SIGN_IN) {
            // Handle Google sign-in result
        }
    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    Toast.makeText(this, "Signed in as ${user?.displayName}", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show()
                }
            }
    }
}