package school.cesar.projetoaula2.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import school.cesar.projetoaula2.R
import school.cesar.projetoaula2.dao.TarefaDAO
import school.cesar.projetoaula2.model.Usuario
import school.cesar.projetoaula2.ui.adapter.TarefaAdapter

class TarefasActivity : AppCompatActivity() {

    private lateinit var usuario: Usuario;
    private var tarefas: MutableList<String> = mutableListOf();
    private lateinit var adapter: RecyclerView.Adapter<TarefaAdapter.ViewHolder>

    companion object{
        val ACAO_FORMULARIO_INCLUIR = 0
        val ACAO_FORMULARIO_ALTERAR = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tarefas)
        configuraActionBar()
        configuraFabButton()
        getExtraIntent()
        getTarefas()
        configuraLista()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun configuraActionBar(){
        val actionbar = supportActionBar
        actionbar?.let {
            actionbar.title = getString(R.string.titulo_todo)
            actionbar.setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun configuraFabButton(){
        val fabAdicionaItem = findViewById<FloatingActionButton>(R.id.activity_todo_fab_adiciona_item)
        fabAdicionaItem.setOnClickListener {
            exibeFormulario(ACAO_FORMULARIO_INCLUIR)
        }
    }

    private fun getExtraIntent(){
        if(intent.hasExtra(Usuario.TAG)){
            usuario = intent.getSerializableExtra(Usuario.TAG) as Usuario
        }
    }

    private fun getTarefas(){
        tarefas = TarefaDAO.getInstance(this).getTarefas(usuario.indice);
    }

    private fun configuraLista(){
        var lstTarefas = findViewById<RecyclerView>(R.id.activity_tarefas_lst_itens)
        adapter = TarefaAdapter(tarefas)
        lstTarefas.adapter = adapter
    }

    fun exibeFormulario(acaoFormulario: Int, posicao: Int? = null,  tarefa: String? = null) {
        val viewFormulario = LayoutInflater.from(this).inflate(R.layout.dialog_formulario_tarefa, null);
        val edtTarefa = viewFormulario.findViewById<EditText>(R.id.dialog_formulario_tarefas_edt_tarefa)
        var alertDialog = AlertDialog.Builder(this)
            .setView(viewFormulario);
            if(acaoFormulario == ACAO_FORMULARIO_ALTERAR){
                alertDialog.setTitle(getString(R.string.titulo_fomulario_todo_alterar_tarefa))
                    .setPositiveButton(getString(R.string.alterar), { dialog, which ->
                    Toast.makeText(applicationContext,
                        getString(R.string.msg_salvando), Toast.LENGTH_SHORT).show()
                    salvarTarefa(edtTarefa.text.toString(), posicao);
                })
                edtTarefa.setText(tarefa)
            }else{
                alertDialog.setTitle(getString(R.string.titulo_fomulario_todo_nova_tarefa))
                    alertDialog.setPositiveButton(getString(R.string.cadastrar), { dialog, which ->
                    Toast.makeText(applicationContext,
                        getString(R.string.msg_salvando), Toast.LENGTH_SHORT).show()
                    salvarTarefa(edtTarefa.text.toString());
                })
            }
            alertDialog.setNegativeButton(getString(R.string.cancelar), null)
            .show();
    }

    fun exibeFormularioAlterar(posicao: Int){
        exibeFormulario(ACAO_FORMULARIO_ALTERAR, posicao, tarefas[posicao])
    }

    fun salvarTarefa(tarefa: String, posicao: Int? = null) {
        if(!tarefas.contains(tarefa)) {
            if(posicao != null) {
                tarefas[posicao] = tarefa
            }else{
                tarefas.add(tarefa)
            }
            TarefaDAO.getInstance(this).salvar(usuario.indice, tarefas);
        }else{
            Toast.makeText(this, getString(R.string.msg_tarefa_ja_cadastrada), Toast.LENGTH_SHORT).show();
        }
        adapter.notifyDataSetChanged()
    }

    fun removerTarefa(posicao: Int) {
        tarefas.removeAt(posicao)
        TarefaDAO.getInstance(this).salvar(usuario.indice, tarefas);
    }
}
