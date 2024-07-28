package Modelo

data class PACIENTES(

    val id_paciente: Int,
    var nombres: String,
    val apellidos: String,
    val edad: Int,
    val enfermedad: String,
    val numero_habitacion: Int,
    val numero_cama : Int,

)
