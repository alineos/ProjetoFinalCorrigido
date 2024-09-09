package br.edu.ifsp.dmo.whatsapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.dmo.whatsapp.R
import br.edu.ifsp.dmo.whatsapp.data.model.Mensagem
import br.edu.ifsp.dmo.whatsapp.data.repository.UsuarioRepository
import br.edu.ifsp.dmo.whatsapp.databinding.AdapterMensagemDestinatarioBinding
import br.edu.ifsp.dmo.whatsapp.databinding.AdapterMensagemRemetenteBinding


class MensagemAdapter(val emailDestinatario:String) :RecyclerView.Adapter<RecyclerView.ViewHolder>()  {

    private var dataset:List<Mensagem> = emptyList()
    private var TIPO_REMETENTE = 0;
    private var TIPO_DESTINATARIO = 1;


    override fun getItemViewType(position: Int): Int {
        val mensagem = dataset.get(position) //instancia um objeto mensagem na posição do adapter passada no parametro

        if (emailDestinatario.equals(mensagem.emailDestinatario)){ //verifica se o e-mail destinatario da mensagem é igual ao e-mail passado no parametro da classe MensagemAdapter
            return TIPO_REMETENTE //retorna o TIPO_REMETENTE para a viewType
        }
        return TIPO_DESTINATARIO //retorna o TIPO_DESTINATARIO para a viewType
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if(viewType==TIPO_REMETENTE){ //valida se o viewType é igual a TIPO_REMETENTE
            var view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_mensagem_remetente, parent, false) //seta o layout do TIPO_REMETENTE para o view do adapter
           return ViewHolderRemetente(view) //retorna o view para o adapter
        }else{
            var view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_mensagem_destinatario, parent, false)//seta o layout do TIPO_DESTINATARIO para o view do adapter
            return ViewHolderDestinatario(view) //retorna o view para o adapter
        }
    }


    override fun getItemCount(): Int {
        return dataset.size //retorna o tamanho da lista de mensagens
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){ //verifica o tipo do holder
            is ViewHolderRemetente->{ //caso seja do tipo remetente
                holder.bindingRemetente.textMensagemRemetente.text = dataset[position].textoMensagem //insere o texto da mensagem no viewText do remetente
            }
            is ViewHolderDestinatario ->{ //caso seja do tipo destinatario
                holder.bindingDestinatario.textMensagemDestinatario.text =dataset[position].textoMensagem //insere o texto da mensagem no viewText do destinatario
            }
        }
    }


    fun submitDataset(dataset: List<Mensagem>){
        this.dataset = dataset //seta a lista que vem do banco de dados para o Dataset do adapter
    }


    class ViewHolderRemetente(view: View): RecyclerView.ViewHolder(view){
        val bindingRemetente: AdapterMensagemRemetenteBinding
        = AdapterMensagemRemetenteBinding.bind(view)
    }


    class ViewHolderDestinatario(view: View): RecyclerView.ViewHolder(view){
        val bindingDestinatario:AdapterMensagemDestinatarioBinding
            =AdapterMensagemDestinatarioBinding.bind(view)
    }
}