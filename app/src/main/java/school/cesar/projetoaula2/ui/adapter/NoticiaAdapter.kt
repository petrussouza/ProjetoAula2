package school.cesar.projetoaula2.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import school.cesar.projetoaula2.R
import school.cesar.projetoaula2.model.Noticia

class NoticiaAdapter(private val noticias: MutableList<Noticia>) : RecyclerView.Adapter<NoticiaAdapter.ViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): NoticiaAdapter.ViewHolder {
        context = viewGroup.context
        var v = LayoutInflater.from(context).inflate(R.layout.noticia_item, viewGroup, false);
        var pvh = ViewHolder(v);
        return pvh;
    }

    override fun onBindViewHolder(holder: NoticiaAdapter.ViewHolder, posicao: Int) {
        holder.noticiaItemTitulo.setText(noticias.get(posicao).titulo);
        holder.noticiaItemDescricao.setText(noticias.get(posicao).descricao);
    }

    override fun getItemCount(): Int {
        return noticias.size
    }

    class ViewHolder(var viewItem: View) : RecyclerView.ViewHolder(viewItem) {
        var noticiaItemTitulo: TextView = viewItem.findViewById<TextView>(R.id.noticia_item_txt_titulo)
        var noticiaItemDescricao: TextView = viewItem.findViewById<TextView>(R.id.noticia_item_txt_descricao)
    }

}
