package school.cesar.projetoaula2.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import school.cesar.projetoaula2.R

class TarefaAdapter(private val tarefas: List<String>) : RecyclerView.Adapter<TarefaAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): TarefaAdapter.ViewHolder {
        var v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tarefa_item, viewGroup, false);
        var pvh = ViewHolder(v);
        return pvh;
    }

    override fun onBindViewHolder(holder: TarefaAdapter.ViewHolder, posicao: Int) {
        holder.tarefaItemTitulo.setText(tarefas.get(posicao));
    }

    override fun getItemCount(): Int {
        return tarefas.size
    }

    class ViewHolder(var viewItem: View) : RecyclerView.ViewHolder(viewItem){
        var tarefaItemTitulo: TextView = viewItem.findViewById<TextView>(R.id.tarefa_item_txt_titulo)
    }

}
