package RecyclerViewHelpers

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {    val textView: TextView = view.findViewById(
    william.campos.suministro_de_medicamentos_a_pacientes.R.id.txt_pacientesCard)
    val imgEditar: ImageView =view.findViewById(william.campos.suministro_de_medicamentos_a_pacientes.R.id.img_editar)
    val imgBorrar: ImageView =view.findViewById(william.campos.suministro_de_medicamentos_a_pacientes.R.id.img_borrar)
}