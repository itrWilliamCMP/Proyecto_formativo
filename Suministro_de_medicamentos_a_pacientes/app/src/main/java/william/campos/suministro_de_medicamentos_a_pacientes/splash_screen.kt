package william.campos.suministro_de_medicamentos_a_pacientes

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class splash_screen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Creamos una corutina
        //Tiene que ser en el hilo Main porque ese si muestra
        //cosas en pantalla
        GlobalScope.launch(Dispatchers.Main){
            //Espera 3 segundos
            delay(3000)

            val pantallaInicio = Intent(this@splash_screen, login::class.java)
            startActivity(pantallaInicio)
            //Finalizar
            finish()
        }
    }
}