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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import william.campos.suministro_de_medicamentos_a_pacientes.DetallesPacientes
import william.campos.suministro_de_medicamentos_a_pacientes.EditarPacientes
import william.campos.suministro_de_medicamentos_a_pacientes.R


class Adaptador(private var Datos: List<PACIENTES>) : RecyclerView.Adapter<ViewHolder>() {

    fun actualizarLista(nuevaLista:List<PACIENTES>){
        Datos=nuevaLista
        notifyDataSetChanged()
    }

    //funcion parar actualizar el reciler view cuando actualizo los datos

    fun actualizarListaDespuesDeActualizarDatos(uuid: String,nuevosNOMBRES:String){
        val index=Datos.indexOfFirst { it.uuid==uuid }
        Datos[index].NOMBRES=nuevosNOMBRES
        notifyItemChanged(index)

    }
    fun eliminarRegistro(NOMBRES:String,position: Int){

        //quitar el elementpo de la lista
        val listaDatos = Datos .toMutableList()
        listaDatos.removeAt(position)

        //quitar de la base de datos
        GlobalScope.launch(Dispatchers.IO) {

            //crear un objeto e la clase conexion
            val objConexion=ClaseConexion().cadenaConexion()

            val deletePACIENTES = objConexion?.prepareStatement("delete PACIENTES where NOMBRES=?")!!
            deletePACIENTES.setString( 1,NOMBRES)
            deletePACIENTES.executeUpdate()

            val commit = objConexion.prepareStatement( "commit")!!
            commit.executeUpdate()
        }
        Datos=listaDatos.toList()
        notifyItemRemoved(position)
        notifyDataSetChanged()

    }

    fun actualizarTiquete(NOMBRES: String , uuid:String){
        //crear na co rrutinan
        GlobalScope.launch(Dispatchers.IO){
            //creo un objeto de la clase conexion

            val objConexion = ClaseConexion().cadenaConexion()

            //variable que contenga prepared sttement
            val updateNOMBRES = objConexion?.prepareStatement("update PACIENTES set NOMBRES = ? where uuid = ?")!!

            updateNOMBRES.setString(1,NOMBRES)
            updateNOMBRES.setString(2,uuid)
            updateNOMBRES.executeUpdate()

            val commit = objConexion.prepareStatement("commit")
            commit.executeUpdate()

            withContext(Dispatchers.Main){
                actualizarListaDespuesDeActualizarDatos(uuid,NOMBRES )
            }

        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.activity_item_card, parent, false)
        return ViewHolder(vista)
    }

    override fun getItemCount() = Datos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pacientes = Datos[position]
        holder.textView.text = pacientes.NOMBRES

        val item =Datos[position]


        holder.imgBorrar.setOnClickListener {
            //craeamos una alaerta

            //invocamos  el contexto
            val context = holder.itemView.context

            //CREO LA ALERTA

            val builder = AlertDialog.Builder(context)

            //le ponemos titulo a la alerta

            builder.setTitle("¿estas seguro?")

            //ponerle mendsaje a la alerta

            builder.setMessage("Deseas en verdad eliminar el registro")

            //agrgamos los botones

            builder.setPositiveButton("si"){dialog,wich ->
                eliminarRegistro(item.NOMBRES,position)
            }

            builder.setNegativeButton("no"){dialog,wich ->

            }

            //cramos la alerta
            val alertDialog=builder.create()

            //mostramos la alerta

            alertDialog.show()

        }

        holder.imgEditar.setOnClickListener {
            //invoco el contexto
            val context = holder.itemView.context

            //cambiamos de pantalla
            //abro pantalla detalle pacientes
            val pantallaEditarPaciente = Intent(context, EditarPacientes::class.java)
            //aqui antes de abrir la nueva pantalla le abro los parametros


            pantallaEditarPaciente.putExtra("uuid",item.uuid)
            pantallaEditarPaciente.putExtra("NOMBRES",item.NOMBRES)
            pantallaEditarPaciente.putExtra("APELLIDOS",item.APELLIDOS)
            pantallaEditarPaciente.putExtra("EDAD",item.EDAD)
            pantallaEditarPaciente.putExtra("ENFERMEDAD",item.ENFERMEDAD)
            pantallaEditarPaciente.putExtra("NUMEROHABITACION",item.NUMEROHABITACION)
            pantallaEditarPaciente.putExtra("NUMEROCAMA",item.NUMEROCAMA)
            pantallaEditarPaciente.putExtra("MEDICAMENTOSASIGNADOS",item.MEDICAMENTOASIGNADOS)
            pantallaEditarPaciente.putExtra("HORAAPLICACION",item.HORAAPLICACION)

            context.startActivity(pantallaEditarPaciente)
        }

        //darle clic a la card
        holder.itemView.setOnClickListener {
            //invoco el contexto
            val context = holder.itemView.context

            //cambiamos de pantalla
            //abro pantalla detalle pacientes
            val pantallaDetalles = Intent(context, DetallesPacientes::class.java)
            //aqui antes de abrir la nueva pantalla le abro los parametros


            pantallaDetalles.putExtra("uuid",item.uuid)
            pantallaDetalles.putExtra("NOMBRES",item.NOMBRES)
            pantallaDetalles.putExtra("APELLIDOS",item.APELLIDOS)
            pantallaDetalles.putExtra("EDAD", item.EDAD)
            pantallaDetalles.putExtra("ENFERMEDAD",item.ENFERMEDAD)
            pantallaDetalles.putExtra("NUMEROHABITACION",item.NUMEROHABITACION)
            pantallaDetalles.putExtra("NUMEROCAMA",item.NUMEROCAMA)
            pantallaDetalles.putExtra("MEDICAMENTOSASIGNADOS",item.MEDICAMENTOASIGNADOS)
            pantallaDetalles.putExtra("HORAAPLICACION",item.HORAAPLICACION)

            context.startActivity(pantallaDetalles)
        }

    }
}