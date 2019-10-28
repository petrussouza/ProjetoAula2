package school.cesar.projetoaula2.extension

fun String.isEmailValido(): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

