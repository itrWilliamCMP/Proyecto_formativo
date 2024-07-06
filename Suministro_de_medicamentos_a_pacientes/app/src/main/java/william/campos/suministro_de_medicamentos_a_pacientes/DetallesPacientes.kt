package william.campos.suministro_de_medicamentos_a_pacientes

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class DetallesPacientes : AppCompatActivity() {
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

        //recibir los valores


        val uuid_PACIENTES = intent.getStringExtra("uuid")
        val nOMBRES = intent.getStringExtra("NOMBRES")
        val aPELLIDOS = intent.getStringExtra("APELLIDOS")
        val eDAD = intent.getIntExtra("EDAD",0)
        val eNFERMEDAD = intent.getStringExtra("ENFERMEDAD")
        val nUMEROHABITACION = intent.getIntExtra("NUMEROHABITACION",0)
        val nUMEROCAMA= intent.getIntExtra("NUMEROCAMA", 0)
        val mEDICAMENTOASIGNADOS= intent.getStringExtra("MEDICAMENTOASIGNADOS")
        val hORAAPLICACION = intent.getStringExtra("HORAAPLICACION")


        txtUUIDDetalle.text = uuid_PACIENTES
        txtNOMBRESDetalle.text= nOMBRES
        txtAPELLIDOSDetalle.text= aPELLIDOS
        txtEDADDetalle.text= eDAD.toString()
        txtENFERMEDADDETALLE.text = eNFERMEDAD
        txtNUMEROHABITACIONDETALLE.text = nUMEROHABITACION.toString()
        txtNUMEROCAMADETALLE.text = nUMEROCAMA.toString()
        txtMEDICAMENTOSDETALLE.text = mEDICAMENTOASIGNADOS
        txtHORAAPLICACIONDETALLE.text = hORAAPLICACION

        imgRegreso.setOnClickListener {
            val pantallaAtras = Intent(this,MainActivity::class.java)
            startActivity(pantallaAtras)
        }
    }
}