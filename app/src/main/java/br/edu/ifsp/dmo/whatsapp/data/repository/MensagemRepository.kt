package br.edu.ifsp.dmo.whatsapp.data.repository

import android.util.Log
import br.edu.ifsp.dmo.whatsapp.data.model.Mensagem
import br.edu.ifsp.dmo.whatsapp.util.Constants
import com.google.firebase.firestore.firestore
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class MensagemRepository {

    private val database = Firebase.firestore
    private lateinit var auth: FirebaseAuth

    fun insert(mensagem: Mensagem, callback: (Boolean) -> Unit) {
        auth = FirebaseAuth.getInstance()
        var remetente = "" //cria uma variavel remetente vazia

        if (!auth.currentUser?.email?.isEmpty()!!) { //verifica se o e-mail do usuario logado está vazio
            remetente = auth.currentUser!!.email.toString() //seta o remetente com o e-mail do usuario logado
        }

        mensagem.emailRemetente = remetente //seta o remetente da mensagem como o e-mail obtido a cima

        database.collection(Constants.COLLECTION_MENSAGENS) //inclui na coleção mensagens a nova mensagem
            .add(mensagem)
            .addOnSuccessListener { callback(true) } //retorna sucesso na inclusão do banco de dados
            .addOnFailureListener { callback(false) } //retorna falha na inclusão do banco de dados
    }

    fun findMensagem(emailDestinatario: String, callback: (List<Mensagem>) -> Unit) { //recebe o destinatário e retorna as mensagens enviadas para este destinatario a partir do usuario logado
        auth = FirebaseAuth.getInstance()
        val usersList = listOf(auth.currentUser?.email, emailDestinatario) //lista de usuarios para consulta no banco de dados

        database.collection(Constants.COLLECTION_MENSAGENS) //busca na coleção mensagens
            .whereIn(Constants.ATTR_EMAIL_REMETENTE,usersList) //onde o remetente estiver na lista de usuarios usersList
            .whereIn(Constants.ATTR_EMAIL_DESTINATARIO, usersList) //onde o destinatario estiver na lista de usuarios usersList
            .orderBy(Constants.ATTR_ID_MENSAGEM, Query.Direction.ASCENDING)//ordenação da mensagem a partir do id em ordem ascendente - FOI NECESSARIO CRIAR UM INDICE NO BANCO DO FIREBASE
            .addSnapshotListener { querySnapshot, exception ->
                if (exception != null) { //verifica se houve excessão na consulta
                    callback(emptyList()) //se houver excessão, atribui ao callback uma lista vazia
                    return@addSnapshotListener //retorna o listener
                }
                if (querySnapshot != null) { // verifica se a lista é diferente de null
                    val list = querySnapshot.toObjects(Mensagem::class.java) // cria uma lista de mensagens e converte o retorno do banco para Mensagem
                    callback(list) //atribui ao callback a lista criada
                } else {
                    callback(emptyList()) //se a lista não for diferente de null, seta uma lista vazia no callback
                }
            }
    }
}