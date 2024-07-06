package william.campos.suministro_de_medicamentos_a_pacientes

import Modelo.ClaseConexion
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

        val txtUUIDDetalle = findViewById<TextView>(R.id.txtUUIDDetalle)
        val txtNOMBRESDetalle = findViewById<TextView>(R.id.txtNOMBRESDetalle)
        val txtAPELLIDOSDetalle = findViewById<TextView>(R.id.txtAPELLIDOSDetalle)
        val txtEDADDetalle = findViewById<TextView>(R.id.txtEDADDetalle)
        val txtENFERMEDADDETALLE = findViewById<TextView>(R.id.txtENFERMEDADDETALLE)
        val txtNUMEROHABITACIONDETALLE = findViewById<TextView>(R.id.txtNUMEROHABITACIONDETALLE)
        val txtNUMEROCAMADETALLE = findViewById<TextView>(R.id.txtNUMEROCAMADETALLE)
        val txtMEDICAMENTOSDETALLE = findViewById<TextView>(R.id.txtMEDICAMENTOSDETALLE)
        val txtHORAAPLICACIONDETALLE = findViewById<TextView>(R.id.txtHORAAPLICACIONDETALLE)
        val imgRegreso=findViewById<ImageView>(R.id.imgRegreso)

        val btnActualizar = findViewById<Button>(R.id.btn_actualizar)


        //recibir los valores


        val UUID_PACIENTES = intent.getStringExtra("uuid")
        val NOMBRES= intent.getStringExtra("NOMBRES")
        val APELLIDOS = intent.getStringExtra("APELLIDOS")
        val EDAD = intent.getIntExtra("EDAD", 0)
        val ENFERMEDAD = intent.getStringExtra("ENFERMEDAD")
        val NUMEROHABITACION = intent.getIntExtra("NUMEROHABITACION", 0)
        val NUMEROCAMA = intent.getIntExtra("NUMEROCAMA", 0)
        val MEDICAMENTOASIGNADOS = intent.getStringExtra("MEDICAMENTOASIGNADOS")
        val HORAAPLICACION = intent.getStringExtra("HORAAPLICACION")


        // poner los valores recibidos en el textview


        txtUUIDDetalle.text = UUID_PACIENTES
        txtNOMBRESDetalle.text= NOMBRES
        txtAPELLIDOSDetalle.text= APELLIDOS
        txtEDADDetalle.text = EDAD.toString()
        txtENFERMEDADDETALLE.text = ENFERMEDAD
        txtNUMEROHABITACIONDETALLE.text = NUMEROHABITACION.toString()
        txtNUMEROCAMADETALLE.text = NUMEROCAMA.toString()
        txtMEDICAMENTOSDETALLE.text = MEDICAMENTOASIGNADOS
        txtHORAAPLICACIONDETALLE.text = HORAAPLICACION

        imgRegreso.setOnClickListener {
            val pantallaAtras = Intent(this,MainActivity::class.java)
            startActivity(pantallaAtras)
        }

        btnActualizar.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO){



                //crear un objeto de la clase conexion
                val claseConexion= ClaseConexion().cadenaConexion()

                //crar una variable que contenga un preparedstatement
                val updatePACIENTES = claseConexion?.prepareStatement("update PACIENTES set NOMBRES=?, APELLIDOS=?,EDAD=?,ENFERMEDAD=?,NUMEROHABITACION=?,NUMEROCAMA=?, MEDICAMENTOS=? ,HORAAPLICACION=? where uuid=?")!!

                updatePACIENTES.setString(1,txtNOMBRESDetalle.text.toString())
                updatePACIENTES.setString(2,txtAPELLIDOSDetalle.text.toString())
                updatePACIENTES.setInt(3,txtEDADDetalle.text.toString().toInt())
                updatePACIENTES.setString(4,txtENFERMEDADDETALLE.text.toString())
                updatePACIENTES.setInt(5,txtNUMEROHABITACIONDETALLE.text.toString().toInt())
                updatePACIENTES.setInt(6,txtNUMEROCAMADETALLE.text.toString().toInt())
                updatePACIENTES.setString(7,txtMEDICAMENTOSDETALLE.text.toString())
                updatePACIENTES.setString(8,txtHORAAPLICACIONDETALLE.text.toString())
                updatePACIENTES.setString(9, txtUUIDDetalle.text.toString())

                updatePACIENTES.executeUpdate()

            }
        }
    }
}