package william.campos.suministro_de_medicamentos_a_pacientes

import Modelo.ClaseConexion
import Modelo.PACIENTES
import RecyclerViewHelpers.Adaptador
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //mandar a llamar a todos los elementos de la pantalla

        val txtNOMBRES = findViewById<EditText>(R.id.txtNombresPacientes)
        val txtApellidos = findViewById<EditText>(R.id.txtApellidosPaciente)
        val txtEdad = findViewById<EditText>(R.id.txtEdadPacientes)
        val txtEnfermedad = findViewById<EditText>(R.id.txtEnfermedadPaciente)
        val txtnumerohabitacion = findViewById<EditText>(R.id.txtNumeroHabitacion)
        val txtnumerocama = findViewById<EditText>(R.id.txtNumeroCama)
        val txtmedicamentoasignado = findViewById<EditText>(R.id.txtMedicamentoAsignado)
        val txtHoraAplicacion = findViewById<EditText>(R.id.txtHoraAplicacion)
        val btnAgregar = findViewById<Button>(R.id.btnAgregar)

        fun limpiar(){
            txtNOMBRES.setText("")
            txtApellidos.setText("")
            txtEdad.setText("")
            txtEnfermedad.setText("")
            txtnumerohabitacion.setText("")
            txtnumerocama.setText("")
            txtmedicamentoasignado.setText("")
            txtHoraAplicacion.setText("")
        }

        ////////////////////////////////TODO:mostrar datos ////////////////////////

        val rcvPacientes = findViewById<RecyclerView>(R.id.rcvPacientes)

        //asignar un layout al reciclerviewLedview

        rcvPacientes.layoutManager= LinearLayoutManager(this)

        //funcion para obtener datos
        fun obtenerDatos():List<PACIENTES>{
            val objConexion= ClaseConexion().cadenaConexion()

            val statement = objConexion?.createStatement()
            val resultSet = statement?.executeQuery("select * from PACIENTES")!!

            val Pacientes = mutableListOf<PACIENTES>()

            while (resultSet.next()){
                val uuid = resultSet.getString("uuid")
                val NOMBRES = resultSet.getString("NOMBRES")
                val APELLIDOS = resultSet.getString("APELLIDOS")
                val EDAD = resultSet.getInt("EDAD")
                val ENFERMEDAD = resultSet.getString("ENFERMEDAD")
                val NUMEROHABITACION = resultSet.getInt("NUMEROHABITACION")
                val NUMEROCAMA = resultSet.getInt("NUMEROCAMA")
                val MEDICAMENTOASIGNADOS = resultSet.getString("MEDICAMENTOASIGNADOS")
                val HORAAPLICACION= resultSet.getString("HORAAPLICACION")


                val paciente = PACIENTES(uuid,NOMBRES,APELLIDOS,EDAD,ENFERMEDAD,NUMEROHABITACION,NUMEROCAMA,MEDICAMENTOASIGNADOS,HORAAPLICACION)
                Pacientes.add(paciente)
            }
            return Pacientes
        }

        //asignar un adaptador

        CoroutineScope ( Dispatchers.IO) .launch {
            val pacientes =obtenerDatos()
            withContext(Dispatchers.Main){
                val miAdapter = Adaptador(pacientes)
                rcvPacientes.adapter=miAdapter
            }
        }
        ///////////////////////////////// TODO:GUARDAR Pacientes////////////////////////////////////
        // programar el boton
        btnAgregar.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO){

                //guardar datos

                //crear un objeto de la clase conexion
                val claseConexion= ClaseConexion().cadenaConexion()

                //crar una variable que contenga un preparedstatement

                val addPacientes =claseConexion?.prepareStatement("insert into PACIENTES (uuid ,NOMBRES,APELLIDOS,EDAD,ENFERMEDAD,NUMEROHABITACION,NUMEROCAMA,MEDICAMENTOASIGNADOS,HORAAPLICACION)values(?,?,?,?,?,?,?,?,?)")!!

                addPacientes.setString(1, UUID.randomUUID().toString())
                addPacientes.setString(2,txtNOMBRES.text.toString())
                addPacientes.setString(3,txtApellidos.text.toString())
                addPacientes.setInt(4,txtEdad.text.toString().toInt())
                addPacientes.setString(5,txtEnfermedad.text.toString())
                addPacientes.setInt(6,txtnumerohabitacion.text.toString().toInt())
                addPacientes.setInt(7,txtnumerocama.text.toString().toInt())
                addPacientes.setString(8,txtmedicamentoasignado.text.toString())
                addPacientes.setString(9,txtHoraAplicacion.text.toString())
                addPacientes.executeUpdate()

                val nuevosPacientes=obtenerDatos()

                withContext(Dispatchers.Main){
                    (rcvPacientes.adapter as? Adaptador)?.actualizarLista(nuevosPacientes)
                }
            }
        }

    }
}
