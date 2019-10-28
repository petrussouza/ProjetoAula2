package school.cesar.projetoaula2.extension

import android.widget.EditText

fun EditText.emBranco(): Boolean{
    val valor = this.text.toString();
    if(valor.equals("")){
        this.error = "Campo obrigatório!"
        return true
    }
    return false
}

fun EditText.minCaracteres(tam: Int): Boolean{
    val valor = this.text.toString();
    if(valor.length < tam){
        this.error = "Mínimo de $tam caracteres!"
        return false
    }
    return true
}

fun EditText.isEmailValido(): Boolean{
    if(!this.text.toString().isEmailValido()){
        this.error = "Email inválido!"
        return false
    }
    return true
}