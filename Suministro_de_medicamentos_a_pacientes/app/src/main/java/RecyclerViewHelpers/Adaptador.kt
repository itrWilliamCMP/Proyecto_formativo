package RecyclerViewHelpers

import Modelo.ClaseConexion
import Modelo.PACIENTES
import android.app.AlertDialog
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import william.campos.suministro_de_medicamentos_a_pacientes.DetallesPacientes
import william.campos.suministro_de_medicamentos_a_pacientes.EditarPacientes
import william.campos.suministro_de_medicamentos_a_pacientes.MainActivity
import william.campos.suministro_de_medicamentos_a_pacientes.R


class Adaptador(private var Datos: List<PACIENTES>) : RecyclerView.Adapter<ViewHolder>() {

    fun actualizarLista(nuevaLista: List<PACIENTES>) {
        Datos = nuevaLista
        notifyDataSetChanged()
    }

    fun actualizarListaDespuesDeActualizarDatos(idPaciente: Int, nuevosNOMBRES: String) {
        val index = Datos.indexOfFirst { it.id_paciente == idPaciente }
            Datos[index].nombres = nuevosNOMBRES
            notifyItemChanged(index)

    }

    fun eliminarRegistro(nombres: String, position: Int) {
        val listaDatos = Datos.toMutableList()
        listaDatos.removeAt(position)

        GlobalScope.launch(Dispatchers.IO) {
            val objConexion = ClaseConexion().cadenaConexion()
            val deletePACIENTES = objConexion?.prepareStatement("DELETE FROM PACIENTES WHERE nombres = ?")!!
            deletePACIENTES.setString(1, nombres)
            deletePACIENTES.executeUpdate()
        }

        Datos = listaDatos.toList()
        notifyItemRemoved(position)
        notifyDataSetChanged()
    }

    fun actualizarDatos(idPaciente: Int, nuevosNOMBRES: String) {
        GlobalScope.launch(Dispatchers.IO) {
            val objConexion = ClaseConexion().cadenaConexion()
            val updateNOMBRES = objConexion?.prepareStatement("UPDATE PACIENTES SET nombres = ? WHERE id_paciente = ?")!!
            updateNOMBRES.setString(1, nuevosNOMBRES)
            updateNOMBRES.setInt(2, idPaciente)
            updateNOMBRES.executeUpdate()

            withContext(Dispatchers.Main) {
                actualizarListaDespuesDeActualizarDatos(idPaciente, nuevosNOMBRES)
            }
        }
    }

    // Función para obtener medicamentos (dentro del adaptador)
    fun obtenerMedicamentos(idPaciente: Int): List<String> {
        val objConexion = ClaseConexion().cadenaConexion()
        val statement = objConexion?.createStatement()
        val medicamentos = mutableListOf<String>()
        val resultSet = statement?.executeQuery("select m.* from  medicamentos m inner join PACIENTES_MEDICAMENTOS pm on m.id_medicamento=pm.id_medicamento where pm.id_paciente=$idPaciente")

        if (resultSet != null) {
            while (resultSet.next()) {
                val nombremedicamento = resultSet.getString("medicamento_asignado")
                var hora_aplicacion = resultSet.getString("hora_aplicacion")
                Log.v("datos_medicamentos", nombremedicamento + " " + hora_aplicacion)

                if (hora_aplicacion == null) {
                    hora_aplicacion = "NO ESPECIFICADO"
                }

                hora_aplicacion = "DOSIS: " +hora_aplicacion
                // Agrega el nombre del medicamento y la hora de aplicación a la lista
                medicamentos.add(nombremedicamento + " " + hora_aplicacion)
            }
        } else {
            Log.e("Adaptador", "Error al obtener medicamentos: resultSet es nulo")
        }
        return medicamentos
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.activity_item_card, parent, false)
        return ViewHolder(vista)
    }

    override fun getItemCount() = Datos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pacientes = Datos[position]
        holder.textView.text = pacientes.nombres

        val item = Datos[position]

        holder.imgBorrar.setOnClickListener {
            val context = holder.itemView.context
            val builder = AlertDialog.Builder(context)
            builder.setTitle("¿Estás seguro?")
            builder.setMessage("¿Deseas en verdad eliminar el registro?")
            builder.setPositiveButton("Sí") { dialog, which ->
                eliminarRegistro(item.nombres, position)
            }
            builder.setNegativeButton("No") { dialog, which -> }
            val alertDialog = builder.create()
            alertDialog.show()
        }

        holder.imgEditar.setOnClickListener {
            val context = holder.itemView.context
            val pantallaEditarPaciente = Intent(context, EditarPacientes::class.java)
            pantallaEditarPaciente.putExtra("id_paciente", item.id_paciente)
            pantallaEditarPaciente.putExtra("nombres", item.nombres)
            pantallaEditarPaciente.putExtra("apellidos", item.apellidos)
            pantallaEditarPaciente.putExtra("edad", item.edad)
            pantallaEditarPaciente.putExtra("enfermedad", item.enfermedad)
            context.startActivity(pantallaEditarPaciente)
        }

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val pantallaDetalles = Intent(context, DetallesPacientes::class.java)
            pantallaDetalles.putExtra("id_paciente", item.id_paciente)
            pantallaDetalles.putExtra("nombres", item.nombres)
            pantallaDetalles.putExtra("apellidos", item.apellidos)
            pantallaDetalles.putExtra("edad", item.edad)
            pantallaDetalles.putExtra("enfermedad", item.enfermedad)
            pantallaDetalles.putExtra("numero_habitacion", item.numero_habitacion)
            pantallaDetalles.putExtra("numero_cama", item.numero_cama)

            val idPaciente=item.id_paciente

            // Usa una corrutina para obtener los medicamentos en un hilo de fondo
            CoroutineScope(Dispatchers.IO).launch {
                val medicamentos = obtenerMedicamentos(idPaciente)
                withContext(Dispatchers.Main) {
                    pantallaDetalles.putStringArrayListExtra("medicamentos", ArrayList(medicamentos))
                    context.startActivity(pantallaDetalles)
                }
            }
        }
    }
}
