package william.campos.suministro_de_medicamentos_a_pacientes

import Modelo.ClaseConexion
import Modelo.HABITACIONES
import Modelo.MEDICAMENTOS
import Modelo.PACIENTES
import RecyclerViewHelpers.Adaptador
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
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
    @SuppressLint("NotifyDataSetChanged")
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

        val btnAgregar = findViewById<Button>(R.id.btnAgregar)

        fun limpiar(){
            txtNOMBRES.setText("")
            txtApellidos.setText("")
            txtEdad.setText("")
            txtEnfermedad.setText("")
        }

        ////////////////////////////////TODO:mostrar datos ////////////////////////

        val rcvPacientes = findViewById<RecyclerView>(R.id.rcvPacientes)

        //asignar un layout al reciclerviewLedview

        rcvPacientes.layoutManager= LinearLayoutManager(this)



        //funcion para obtener datos
        fun obtenerDatos():List<PACIENTES>{
            val objConexion= ClaseConexion().cadenaConexion()

            val statement = objConexion?.createStatement()
            val resultSet = statement?.executeQuery("select p.*,h.numero_habitacion,h.numero_cama from PACIENTES p " +
                    " left join HABITACIONES h on p.id_paciente=h.id_paciente")!!

            val Pacientes = mutableListOf<PACIENTES>()

            Pacientes.clear()

            while (resultSet.next()){
                val id_paciente = resultSet.getInt("id_paciente")
                val nombres = resultSet.getString("nombres")
                val apellidos = resultSet.getString("apellidos")
                val edad = resultSet.getInt("edad")
                val enfermedad = resultSet.getString("enfermedad")
                var numero_habitacion = resultSet.getInt("numero_habitacion")
                var numero_cama = resultSet.getInt("numero_cama")

                Log.v("datos",id_paciente.toString()+" "+nombres+" "+apellidos+" "+edad+" "+enfermedad+" "+numero_habitacion+" "+numero_cama)

                if (numero_habitacion == null) {
                    numero_habitacion = 0
                }
                if (numero_cama == null) {
                    numero_cama = 0
                }

                val paciente = PACIENTES(id_paciente,nombres,apellidos,edad,enfermedad,numero_habitacion,numero_cama)
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


        ///////////////////////////////// TODO:GUARDAR Datos////////////////////////////////////
        // programar el boton
        btnAgregar.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO){
                //guardar datos
                val claseConexion= ClaseConexion().cadenaConexion()

                // 1. Insertar en PACIENTES
                val addPacientes =claseConexion?.prepareStatement("INSERT INTO PACIENTES (nombres, apellidos, edad, enfermedad) VALUES (?,?,?,?)")!!
                addPacientes.setString(1,txtNOMBRES.text.toString())
                addPacientes.setString(2,txtApellidos.text.toString())
                addPacientes.setInt(3,txtEdad.text.toString().toInt())
                addPacientes.setString(4,txtEnfermedad.text.toString())
                addPacientes.executeUpdate()


                val nuevosPacientes=obtenerDatos()


                withContext(Dispatchers.Main){
                    (rcvPacientes.adapter as? Adaptador)?.actualizarLista(nuevosPacientes)
                }


            }
        }
    }
}






