package school.cesar.projetoaula2.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import school.cesar.projetoaula2.R
import school.cesar.projetoaula2.ui.activity.TarefasActivity

class TarefaAdapter(private val tarefas: MutableList<String>) : RecyclerView.Adapter<TarefaAdapter.ViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): TarefaAdapter.ViewHolder {
        context = viewGroup.context
        var v = LayoutInflater.from(context).inflate(R.layout.tarefa_item, viewGroup, false);
        var pvh = ViewHolder(v);
        return pvh;
    }

    override fun onBindViewHolder(holder: TarefaAdapter.ViewHolder, posicao: Int) {
        holder.tarefaItemTitulo.setText(tarefas.get(posicao));
        holder.viewItem.setOnCreateContextMenuListener { contextMenu, _, _ ->
            MenuInflater(context).inflate(R.menu.menu_tarefa_item, contextMenu)
            contextMenu.findItem(R.id.menu_tarefa_item_alterar).setOnMenuItemClickListener {
                (context as TarefasActivity).exibeFormularioAlterar(posicao)
                true
            }
            contextMenu.findItem(R.id.menu_tarefa_item_excluir).setOnMenuItemClickListener {
                (context as TarefasActivity).removerTarefa(posicao)
                notifyDataSetChanged()
                true
            }
        }
    }

    override fun getItemCount(): Int {
        return tarefas.size
    }

    class ViewHolder(var viewItem: View) : RecyclerView.ViewHolder(viewItem) {
        var tarefaItemTitulo: TextView = viewItem.findViewById<TextView>(R.id.tarefa_item_txt_titulo)

    }

}
