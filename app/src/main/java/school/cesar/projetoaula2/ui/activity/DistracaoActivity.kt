package school.cesar.projetoaula2.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import school.cesar.projetoaula2.R
import school.cesar.projetoaula2.async.MyAsyncTask
import school.cesar.projetoaula2.async.TaskListener
import school.cesar.projetoaula2.model.Noticia
import school.cesar.projetoaula2.ui.adapter.NoticiaAdapter
import java.net.URL

class DistracaoActivity : AppCompatActivity() {

    companion object{
        private val URL_API = "https://newsapi.org/v2/everything?q=bitcoin&apiKey=97e5ac9d6a2348b3a6f21da7bda3f431"
        private val STATUS_CONSULTA_OK = "ok";
    }

    private var noticias: MutableList<Noticia> = mutableListOf();
    private lateinit var adapter: RecyclerView.Adapter<NoticiaAdapter.ViewHolder>
    private lateinit var asyncTask: MyAsyncTask

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_distracao)
        configuraActionBar()
        configuraLista()
        getNoticias()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun configuraActionBar(){
        val actionbar = supportActionBar
        actionbar?.let {
            actionbar.title = getString(R.string.titulo_distracao)
            actionbar.setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun getNoticias(){
        asyncTask = MyAsyncTask(this, object: TaskListener {
            override fun onTaskComplete(json: String) {
                parseNoticias(json)
            }
        });
        asyncTask.execute(URL(URL_API))
    }

    private fun configuraLista(){
        var lstNoticias = findViewById<RecyclerView>(R.id.activity_distracao_lst_itens)
        adapter = NoticiaAdapter(noticias)
        lstNoticias.adapter = adapter
    }

    private fun parseNoticias(json: String){
        val jsonObj = JSONObject(json)
        val statusConsulta = jsonObj.get("status")
        val totalResultados = jsonObj.getInt("totalResults")
        if(statusConsulta.equals(STATUS_CONSULTA_OK) && totalResultados > 0){
            val gson: Gson = Gson()
            val listType = object : TypeToken<MutableList<Noticia>>() { }.type
            val noticiasRetornadas: MutableList<Noticia> = gson.fromJson(jsonObj.get("articles").toString(), listType)
            noticias.addAll(noticiasRetornadas);
        }
        adapter.notifyDataSetChanged()
    }
}
