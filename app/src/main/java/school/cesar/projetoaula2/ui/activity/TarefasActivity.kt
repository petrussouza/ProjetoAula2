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
            exibeFormulario()
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
        val adapter = TarefaAdapter(tarefas)
        lstTarefas.adapter = adapter
    }

    private fun exibeFormulario(){
        val viewFormulario = LayoutInflater.from(this)
            .inflate(R.layout.dialog_formulario_tarefa, null);
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.titulo_fomulario_todo_item))
            .setView(viewFormulario)
            .setPositiveButton(getString(R.string.cadastrar), { dialog, which ->
                Toast.makeText(applicationContext,
                    getString(R.string.msg_salvando), Toast.LENGTH_SHORT).show()
                val edtAtividade = viewFormulario.findViewById<EditText>(R.id.dialog_formulario_tarefas_edt_tarefa)
                salvarTarefa(edtAtividade.text.toString());
            })
        .setNegativeButton(getString(R.string.cancelar), null)
            .show();
    }

    fun salvarTarefa(tarefa: String) {
        if(!tarefas.contains(tarefa)) {
            tarefas.add(tarefa)
            TarefaDAO.getInstance(this).salvar(usuario.indice, tarefas);
        }
    }
}
