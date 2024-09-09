package br.edu.ifsp.dmo.whatsapp.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.edu.ifsp.dmo.whatsapp.data.model.Usuario
import br.edu.ifsp.dmo.whatsapp.util.Constants
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.Query

import com.google.firebase.firestore.firestore


class UsuarioRepository {

    //objeto de banco de dados
    private val database = Firebase.firestore
    //objeto da classe de autenticação do Firebase - FirebaseAuth Classe responsável por interagir com o serviço de autenticação do Firebase
    private lateinit var auth: FirebaseAuth


    fun insert(usuario: Usuario, callback: (Boolean) -> Unit, callbackMessage: (String) -> Unit){

        var messageRetorno = ""

        auth = FirebaseAuth.getInstance() //instancia a autenticação de acordo com a instancia atual do banco de dados

        auth.createUserWithEmailAndPassword(usuario.email, usuario.senha) //solicita criação de usuario passando e-mail e senha
            .addOnCompleteListener { task -> //escuta o retorno do método de criação do usuário e seta o retorno na variavel task
                if (task.isSuccessful){ //valida o retorno do método createUser
                    database.collection(Constants.COLLECTION_USUARIO).add(usuario) //grava o usuario no banco de dados caso a autenticação tenha retornado sucesso
                }else{
                    messageRetorno = task.exception?.message.toString()
                }
                callback(task.isSuccessful) //retorna se houve sucesso ao criar o usuário
                callbackMessage(messageRetorno)
            }
    }

    fun login(usuario: Usuario, callback: (Boolean) -> Unit) {
        auth = FirebaseAuth.getInstance() //pega a instancia atual do banco de dados
        auth.signInWithEmailAndPassword(usuario.email, usuario.senha).addOnCompleteListener { task -> //acessa o sistema utilizando o serviço de atenticação do firebase
            callback(task.isSuccessful) //retorna sucesso na autenticação
        }
    }

    fun verificaSessao(callback: (Boolean) -> Unit) {
        auth = FirebaseAuth.getInstance() //pega a instancia atual do banco de dados
        val usuario: FirebaseUser? = auth.currentUser //pega usuario atual logado
        callback(usuario != null) //retorna se o usuário é diferente de null, o que significa que há um usuario logado
    }

    fun logoff(){
        auth= FirebaseAuth.getInstance()
        auth.signOut()
    }

    fun findAll(callback: (List<Usuario>) -> Unit) {
       //auth= FirebaseAuth.getInstance()


        //acessa os dados de usuario no banco de dados
        database.collection(Constants.COLLECTION_USUARIO)//.whereNotEqualTo(ATTR_EMAIL,auth.currentUser?.email)
            .orderBy(Constants.ATTR_NAME_USUARIO, Query.Direction.ASCENDING) //ordena de forma ascendente os dados de usuarios por nome
            .addSnapshotListener{ querySnapshot, exception -> //escuta o retorno do banco de dados
                if (exception != null){ //verifica se houve excessão na consulta
                    callback(emptyList()) //se houver excessão, atribui ao callback uma lista vazia
                    return@addSnapshotListener //retorna o listener
                }
                if (querySnapshot != null){ // verifica se a lista é diferente de null
                    val list = querySnapshot.toObjects(Usuario::class.java) // cria uma lista de usuário e converte o retorno do banco para Usuario
                    callback(list) //atribui ao callback a lista criada
                } else {
                    callback(emptyList()) //se a lista não for diferente de null, seta uma lista vazia no callback
                }
            }
    }

}