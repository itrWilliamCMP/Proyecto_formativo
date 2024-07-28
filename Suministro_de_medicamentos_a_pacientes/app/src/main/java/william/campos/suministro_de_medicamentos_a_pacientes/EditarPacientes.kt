package william.campos.suministro_de_medicamentos_a_pacientes

import Modelo.ClaseConexion
import RecyclerViewHelpers.Adaptador
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditarPacientes : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_editar_pacientes)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //mando a llamar a todos los elementos de la pantalla

        val txtid_paciente = findViewById<TextView>(R.id.txtid_paciente)
        val txtNOMBRESDetalle = findViewById<TextView>(R.id.txtNOMBRESDetalle)
        val txtAPELLIDOSDetalle = findViewById<TextView>(R.id.txtAPELLIDOSDetalle)
        val txtEDADDetalle = findViewById<TextView>(R.id.txtEDADDetalle)
        val txtENFERMEDADDETALLE = findViewById<TextView>(R.id.txtENFERMEDADDETALLE)
        val imgRegreso=findViewById<ImageView>(R.id.imgRegreso)

        val btnActualizar = findViewById<Button>(R.id.btn_actualizar)


        //recibir los valores

        val id_paciente = intent.getIntExtra("id_paciente", 0)
        val nombres = intent.getStringExtra("nombres")
        val apellidos = intent.getStringExtra("apellidos")
        val edad = intent.getIntExtra("edad", 0)
        val enfermedad = intent.getStringExtra("enfermedad")



        // poner los valores recibidos en el textview

        txtid_paciente.text = id_paciente.toString()
        txtNOMBRESDetalle.text= nombres
        txtAPELLIDOSDetalle.text= apellidos
        txtEDADDetalle.text = edad.toString()
        txtENFERMEDADDETALLE.text = enfermedad


        imgRegreso.setOnClickListener {
            val pantallaAtras = Intent(this,MainActivity::class.java)
            startActivity(pantallaAtras)
        }

        btnActualizar.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO){
                val claseConexion= ClaseConexion().cadenaConexion()

                // 1. Actualizar PACIENTES
                val updatePacientes = claseConexion?.prepareStatement("UPDATE PACIENTES SET nombres=?, apellidos=?,edad=?,enfermedad=? WHERE id_paciente=?")!!
                updatePacientes.setString(1, txtNOMBRESDetalle.text.toString())
                updatePacientes.setString(2, txtAPELLIDOSDetalle.text.toString())
                updatePacientes.setInt(3, txtEDADDetalle.text.toString().toInt())
                updatePacientes.setString(4, txtENFERMEDADDETALLE.text.toString())
                updatePacientes.setInt(5, txtid_paciente.text.toString().toInt())

                updatePacientes.executeUpdate()



            }
        }
    }
}