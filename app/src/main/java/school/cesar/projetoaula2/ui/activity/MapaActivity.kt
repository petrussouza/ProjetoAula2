package school.cesar.projetoaula2.ui.activity

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import school.cesar.projetoaula2.R
import school.cesar.projetoaula2.async.MyAsyncTask
import school.cesar.projetoaula2.async.TaskListener
import school.cesar.projetoaula2.model.AcademiaCidade
import java.net.URL

class MapaActivity : AppCompatActivity(), OnMapReadyCallback {

    companion object{
        private val URL_API = "http://dados.recife.pe.gov.br/api/3/action/datastore_search?resource_id=78fccbb7-b44d-49a8-8c82-bcc1dc8463b4&limit=20"
    }

    private lateinit var mMap: GoogleMap
    private lateinit var asyncTask: MyAsyncTask
    private var academiasCidade: MutableList<AcademiaCidade> = mutableListOf();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mapa)
        configuraActionBar()
        init()
    }

    private fun configuraActionBar(){
        val actionbar = supportActionBar
        actionbar?.let {
            actionbar.title = getString(R.string.titulo_mapa)
            actionbar.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun init() {
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        callMap()
        getAcademiasCidade()
    }

    private fun getAcademiasCidade(){
        asyncTask = MyAsyncTask(this, object: TaskListener {
            override fun onTaskComplete(json: String) {
                parseAcademiasCidade(json)
                adicionarMarcadores()
            }
        });
        asyncTask.execute(URL(URL_API))
    }

    private fun callMap() {
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
            || checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
        ) {
            val cesarSchool = LatLng(-8.059616, -34.8730747)
            mMap.moveCamera(CameraUpdateFactory.newLatLng(cesarSchool))

        } else {
            requestPermissions(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ), 1010
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode) {
            1010 -> {
                if (grantResults.isNotEmpty() && checkAllPermissionAreGranted(grantResults)) {
                    callMap()
                }
            }
        }
    }

    private fun checkAllPermissionAreGranted(grantResults: IntArray) : Boolean {
        var result = true
        grantResults.forEach { grant ->
            if (grant != PackageManager.PERMISSION_GRANTED) {
                result = false
            }
        }

        return result
    }

    private fun parseAcademiasCidade(json: String){
        val jsonObj = JSONObject(json)
        val successoConsulta = jsonObj.getBoolean("success")
        if(successoConsulta){
            val jsonObjResult = JSONObject(jsonObj.get("result").toString())
            val gson: Gson = Gson()
            val listType = object : TypeToken<MutableList<AcademiaCidade>>() { }.type
            academiasCidade = gson.fromJson(jsonObjResult.get("records").toString(), listType)
        }
    }

    private fun adicionarMarcadores(){
        var primeira = true
        for (academiaCidade in academiasCidade) {
            val latlng = LatLng(academiaCidade.latitude, academiaCidade.longitude)
            mMap.addMarker(MarkerOptions().position(latlng).title(academiaCidade.nome))
            if(primeira){
                val zoomLevel:Float = 12.0f;
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, zoomLevel))
                primeira = false
            }
        }
    }
}
