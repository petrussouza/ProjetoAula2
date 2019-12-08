package school.cesar.projetoaula2.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import school.cesar.projetoaula2.R
import school.cesar.projetoaula2.dao.TarefaDAO
import school.cesar.projetoaula2.model.Tarefa
import school.cesar.projetoaula2.model.Usuario
import school.cesar.projetoaula2.ui.adapter.TarefaAdapter

class TarefasActivity : AppCompatActivity() {

    private val diasSemana by lazy {
        resources.getStringArray(R.array.dias_semana);
    }

    private lateinit var usuario: Usuario;
    private var tarefas: MutableList<Tarefa> = mutableListOf();
    private lateinit var adapter: RecyclerView.Adapter<TarefaAdapter.ViewHolder>
    private lateinit var tarefaDAO: TarefaDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tarefas)
        configuraActionBar()
        configuraFabButton()
        getExtraIntent()
        tarefaDAO = TarefaDAO(this)
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_tarefas, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.menu_tarefas_distracao -> {
                intent = Intent(this, DistracaoActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.menu_tarefas_mapa -> {
                intent = Intent(this, MapaActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
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
        tarefas = tarefaDAO.getTarefas(usuario.id!!);
    }

    private fun configuraLista(){
        var lstTarefas = findViewById<RecyclerView>(R.id.activity_tarefas_lst_itens)
        adapter = TarefaAdapter(tarefas)
        lstTarefas.adapter = adapter
    }

    fun exibeFormulario(tarefa: Tarefa? = null, posicao: Int? = null) {
        val viewFormulario = LayoutInflater.from(this).inflate(R.layout.dialog_formulario_tarefa, null);
        val edtTarefa = viewFormulario.findViewById<EditText>(R.id.dialog_formulario_tarefas_edt_tarefa)
        val spnDiaSemana = viewFormulario.findViewById<Spinner>(R.id.dialog_formulario_tarefas_spn_dia_semana)
        ArrayAdapter.createFromResource(
            this,
            R.array.dias_semana,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spnDiaSemana.adapter = adapter
        }

        var alertDialog = AlertDialog.Builder(this)
            .setView(viewFormulario);
            if(tarefa != null){
                alertDialog.setTitle(getString(R.string.titulo_fomulario_todo_alterar_tarefa))
                    .setPositiveButton(getString(R.string.alterar), { dialog, which ->
                    Toast.makeText(applicationContext,
                        getString(R.string.msg_salvando), Toast.LENGTH_SHORT).show()
                    tarefa.descricao = edtTarefa.text.toString()
                    tarefa.diaSemana = spnDiaSemana.getSelectedItem().toString()
                    alterarTarefa(tarefa, posicao!!);
                })
                edtTarefa.setText(tarefa.descricao)
                spnDiaSemana.setSelection(diasSemana.indexOf(tarefa.diaSemana))
            }else{
                alertDialog.setTitle(getString(R.string.titulo_fomulario_todo_nova_tarefa))
                    alertDialog.setPositiveButton(getString(R.string.cadastrar), { dialog, which ->
                    Toast.makeText(applicationContext,
                        getString(R.string.msg_salvando), Toast.LENGTH_SHORT).show()
                    val tarefa = Tarefa(null, edtTarefa.text.toString(), spnDiaSemana.getSelectedItem().toString())
                    inserirTarefa(tarefa);
                })
            }
            alertDialog.setNegativeButton(getString(R.string.cancelar), null)
            .show();
    }

    fun exibeFormularioAlterar(tarefa: Tarefa, posicao: Int){
        exibeFormulario(tarefa, posicao)
    }

    fun inserirTarefa(tarefa: Tarefa) {
        tarefas.add(tarefa)
        tarefaDAO.insertTarefa(tarefa, usuario.id!!);
        adapter.notifyDataSetChanged()
    }

    fun alterarTarefa(tarefa: Tarefa, posicao: Int) {
        if(posicao != null) {
            tarefas[posicao] = tarefa
        }else{
            tarefas.add(tarefa)
        }
        tarefaDAO.updateTarefa(tarefa, usuario.id!!);

        adapter.notifyItemChanged(posicao)
    }

    fun removerTarefa(posicao: Int) {
        tarefaDAO.removeTarefa(tarefas[posicao].id!!, usuario.id!!);
        tarefas.removeAt(posicao)
        adapter.notifyItemRemoved(posicao)
    }

}
