package br.edu.ifsp.dmo.whatsapp.data.model

import java.io.Serializable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

//declaração de atributos
class Mensagem(
    val emailDestinatario: String = "",
    var emailRemetente: String = "",
    val textoMensagem: String = "",
    var idMensagem: Long = 1
) : Serializable {

    constructor() : this("", "", "", 1)
//inicialização do atributo idMensagem quando instanciar um objeto
    init {
        val currentDateTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss")
        val formattedDateTime = currentDateTime.format(formatter)
        idMensagem = formattedDateTime.toString().toLong()
    }
}