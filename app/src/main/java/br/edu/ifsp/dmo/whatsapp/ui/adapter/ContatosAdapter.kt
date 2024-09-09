package br.edu.ifsp.dmo.whatsapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.dmo.whatsapp.R
import br.edu.ifsp.dmo.whatsapp.data.model.Usuario
import br.edu.ifsp.dmo.whatsapp.databinding.ItemUsuarioBinding
import br.edu.ifsp.dmo.whatsapp.ui.listeners.ContatoClickListener


class ContatosAdapter(val listener: ContatoClickListener?): RecyclerView.Adapter<ContatosAdapter.ViewHolder>()  {
    private var dataset:List<Usuario> = emptyList() //instancia o Dataset do adapter como uma lista vazia


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_usuario, parent, false) //define o layout do adapter
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ContatosAdapter.ViewHolder, position: Int) {
        holder.binding.textNomeContato.text = dataset[position].nome //define o comportamento do componente nome
        holder.binding.textEmailContato.text = dataset[position].email //define o comportamento do componente e-mail

        holder.binding.layoutItem.setOnClickListener{listener?.clickContatoItem(position)} //define o comportamento do click no adapter que será implementado na mainActivity

    }


    override fun getItemCount(): Int {
      return dataset.size //verifica o tamanho da lista Dataset
    }


    fun submitDataset(dataset: List<Usuario>){
        this.dataset = dataset //seta a lista que vem do banco de dados para o Dataset do adapter
    }


    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val binding:ItemUsuarioBinding = ItemUsuarioBinding.bind(view) //define qual será o layout do item no recyclerView
    }
}