package school.cesar.projetoaula2.util

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import school.cesar.projetoaula2.R
import java.util.regex.Pattern

class PoliticaSenhaUtil(var edtSenha: EditText, var msgErro: String) : TextWatcher {

    override fun afterTextChanged( content: Editable) {
        val messagem = msgErro
        val sPattern = Pattern.compile("[A-Z]");
        edtSenha.error = if( content.length > 0
            && !sPattern.matcher(content.get(0).toString()).matches() )
            messagem
        else
            null

    }
    override fun beforeTextChanged(
        content: CharSequence?,
        start: Int,
        count: Int,
        after: Int ) {}

    override fun onTextChanged(
        content: CharSequence?,
        start: Int,
        before: Int,
        count: Int) {}
}