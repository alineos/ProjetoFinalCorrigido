package br.edu.ifsp.dmo.whatsapp.data.model

import com.google.firebase.firestore.Exclude
import java.io.Serializable
//Declaração de atributos
class Usuario(val nome:String = "",
              val email:String = "",
              @get:Exclude //Anotação responsável por indicar que o atributo senha não será salvo no banco de dados
              val senha:String = ""
) :Serializable {
}