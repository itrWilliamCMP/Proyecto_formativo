package william.campos.suministro_de_medicamentos_a_pacientes

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class DetallesPacientes : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.activity_detalles_pacientes)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //mando a llamar a todos los elementos de la pantalla
        val txtid_paciente = findViewById<TextView>(R.id.txtid_pacientedetalle)
        val spMedicamentos = findViewById<Spinner>(R.id.spMedicamentos)
        val txtNOMBRESDetalle = findViewById<TextView>(R.id.txtNOMBRESDetalle)
        val txtAPELLIDOSDetalle = findViewById<TextView>(R.id.txtAPELLIDOSDetalle)
        val txtEDADDetalle = findViewById<TextView>(R.id.txtEDADDetalle)
        val txtENFERMEDADDETALLE = findViewById<TextView>(R.id.txtENFERMEDADDETALLE)
        val txtNUMEROHABITACIONDETALLE = findViewById<TextView>(R.id.txtNUMEROHABITACIONDETALLE)
        val txtNUMEROCAMADETALLE = findViewById<TextView>(R.id.txtNUMEROCAMADETALLE)
        val txtHORAAPLICACIONDETALLE = findViewById<TextView>(R.id.txtHORAAPLICACIONDETALLE)
        val imgRegreso=findViewById<ImageView>(R.id.imgRegreso)

        //recibir los valores
        val id_paciente = intent.getIntExtra("id_paciente",0)
        val medicamentos = intent.getStringArrayListExtra("medicamentos")
        val nOMBRES = intent.getStringExtra("nombres")
        val aPELLIDOS = intent.getStringExtra("apellidos")
        val eDAD = intent.getIntExtra("edad",0)
        val eNFERMEDAD = intent.getStringExtra("enfermedad")
        val nUMEROHABITACION = intent.getIntExtra("numero_habitacion",0)
        val nUMEROCAMA = intent.getIntExtra("numero_cama",0)
        val hORAAPLICACION = intent.getStringExtra("hora_aplicacion")

        // poner los valores recibidos en el textview
        txtid_paciente.text= id_paciente.toString()
        spMedicamentos.adapter= ArrayAdapter(this,android.R.layout.simple_spinner_item,medicamentos!!)
        txtNOMBRESDetalle.text= nOMBRES
        txtAPELLIDOSDetalle.text= aPELLIDOS
        txtEDADDetalle.text= eDAD.toString()
        txtENFERMEDADDETALLE.text = eNFERMEDAD
        txtNUMEROHABITACIONDETALLE.text = nUMEROHABITACION.toString()
        txtNUMEROCAMADETALLE.text = nUMEROCAMA.toString()
        txtHORAAPLICACIONDETALLE.text = hORAAPLICACION

        imgRegreso.setOnClickListener {
            val pantallaAtras = Intent(this,MainActivity::class.java)
            startActivity(pantallaAtras)
        }
    }
}