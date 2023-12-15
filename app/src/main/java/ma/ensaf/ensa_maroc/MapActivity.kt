package ma.ensaf.ensa_maroc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment

class MapActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        val fragment : Fragment = Map_Fragement()
        supportFragmentManager.beginTransaction().replace(R.id.frameLayout,fragment).commit()
    }
}