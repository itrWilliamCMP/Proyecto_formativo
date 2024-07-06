package Modelo

data class PACIENTES(

    val uuid: String,
    var NOMBRES: String,
    val APELLIDOS: String,
    val EDAD: Int,
    val ENFERMEDAD: String,
    val NUMEROHABITACION: Int,
    val NUMEROCAMA: Int,
    val MEDICAMENTOASIGNADOS: String,
    val HORAAPLICACION: String
)
