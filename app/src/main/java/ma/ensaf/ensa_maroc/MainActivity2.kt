package ma.ensaf.ensa_maroc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.facebook.login.widget.LoginButton

class MainActivity2 : AppCompatActivity() {
    val Text1 = findViewById<TextView>(R.id.textView)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        val name: String = intent.getStringExtra("name") ?: ""
        Text1.setText(name)

    }
}