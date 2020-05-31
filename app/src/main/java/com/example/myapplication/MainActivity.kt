package com.example.myapplication

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {

    var vidaCompleta: Int = 745

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun clickBtnJugar(view: View?) {
        var tilNombreJugador = findViewById<TextInputLayout>(R.id.jugadorText);
        var liDuelo = findViewById<LinearLayout>(R.id.duelo);
        var tvNombreJugador =  findViewById<TextView>(R.id.jugador);
        var tvEspacioBlanco =  findViewById<TextView>(R.id.textView);

        var nombreIngresado = tilNombreJugador.getEditText()?.getText().toString();

        var tvVidaJugador = findViewById<TextView>(R.id.vidaPorcentajeJugador)
        var tvVidaMonstruo =  findViewById<TextView>(R.id.vidaPorcentajeMosntruo)
        var tvMensajeFinal = findViewById<TextView>(R.id.mensajeFinal)

        if (!nombreIngresado.trim().isEmpty()) {
            tvEspacioBlanco.visibility = View.GONE;
            liDuelo.visibility = View.VISIBLE;
            tvNombreJugador.text = nombreIngresado;

            tvVidaMonstruo.measure(0, 0)
            tvVidaMonstruo.getLayoutParams().width = 745
            tvVidaMonstruo.requestLayout();
            tvVidaMonstruo.setText("100%");

            tvVidaJugador.measure(0, 0)
            tvVidaJugador.getLayoutParams().width = 745
            tvVidaJugador.requestLayout();
            tvVidaJugador.setText("100%");

            tvMensajeFinal.visibility = View.GONE;
        }
    }

    fun clickBtnAtacar(view: View?) {
        var tvVidaJugador = findViewById<TextView>(R.id.vidaPorcentajeJugador)
        var tvVidaMonstruo =  findViewById<TextView>(R.id.vidaPorcentajeMosntruo)

        var porcentajeVidaJugador = tvVidaJugador.getText().toString()
        var porcentajeVidaMonstruo = tvVidaMonstruo.getText().toString()

        if (porcentajeVidaJugador != "" && porcentajeVidaMonstruo != "") {
            val porcentajeAtaqueJugador = (5..15).random()
            val porcentajeAtaqueMonstruo = (5..20).random()

            tvVidaMonstruo.measure(0, 0)
            tvVidaJugador.measure(0, 0)

            var widthJugador = calcularAccion(tvVidaJugador.getLayoutParams().width, porcentajeAtaqueMonstruo, 1)
            var widthMonstruo = calcularAccion(tvVidaMonstruo.getLayoutParams().width, porcentajeAtaqueJugador, 1)

            tvVidaMonstruo.getLayoutParams().width = widthMonstruo
            tvVidaMonstruo.requestLayout();

            tvVidaJugador.getLayoutParams().width = widthJugador
            tvVidaJugador.requestLayout();

            if(calcularPorcentajeVida(porcentajeVidaJugador, porcentajeAtaqueMonstruo, 1) <= 0){
                var tvMensajeFinal = findViewById<TextView>(R.id.mensajeFinal)
                tvMensajeFinal.visibility = View.VISIBLE;
                tvMensajeFinal.setText("P E R D I S T E");
                tvVidaJugador.setText("")
                tvVidaJugador.getLayoutParams().width = 0
                tvVidaJugador.requestLayout();
            }
            else if (calcularPorcentajeVida(porcentajeVidaMonstruo, porcentajeAtaqueJugador, 1) <= 0){
                var tvMensajeFinal = findViewById<TextView>(R.id.mensajeFinal)
                tvMensajeFinal.visibility = View.VISIBLE;
                tvMensajeFinal.setText("Ganaste");
                tvVidaMonstruo.setText("")
                tvVidaMonstruo.getLayoutParams().width = 0
                tvVidaMonstruo.requestLayout();
            } else {
                tvVidaJugador.
                    setText(calcularPorcentajeVida(porcentajeVidaJugador, porcentajeAtaqueMonstruo, 1).toString() + "%")
                tvVidaMonstruo.
                    setText(calcularPorcentajeVida(porcentajeVidaMonstruo, porcentajeAtaqueJugador, 1).toString() + "%")
            }
        }
    }

    fun clickBtnCurar(view: View?) {
        var tvVidaJugador = findViewById<TextView>(R.id.vidaPorcentajeJugador)
        var tvVidaMonstruo =  findViewById<TextView>(R.id.vidaPorcentajeMosntruo)

        var porcentajeVidaJugador = tvVidaJugador.getText().toString()
        var porcentajeVidaMonstruo = tvVidaMonstruo.getText().toString()

        if (porcentajeVidaJugador != "" && porcentajeVidaMonstruo != "") {
            val porcentajeAtaqueMonstruo = (5..20).random()
            val porcentajeCurar = (10..25).random()

            tvVidaJugador.measure(0, 0)

            var widthJugador = calcularAccion(tvVidaJugador.getLayoutParams().width, porcentajeCurar, 2)

            tvVidaJugador.getLayoutParams().width = widthJugador
            tvVidaJugador.requestLayout();

            var nuevoPorcentajeVida = calcularPorcentajeVida(porcentajeVidaJugador, porcentajeCurar, 2).toString() + "%"

            widthJugador = calcularAccion(tvVidaJugador.getLayoutParams().width, porcentajeAtaqueMonstruo, 1)

            tvVidaJugador.getLayoutParams().width = widthJugador
            tvVidaJugador.requestLayout();

            if(calcularPorcentajeVida(nuevoPorcentajeVida, porcentajeAtaqueMonstruo, 1) <= 0){
                var tvMensajeFinal = findViewById<TextView>(R.id.mensajeFinal)
                tvMensajeFinal.visibility = View.VISIBLE;
                tvMensajeFinal.setText("P E R D I S T E");
                tvVidaJugador.setText("")
                tvVidaJugador.getLayoutParams().width = 0
                tvVidaJugador.requestLayout();
            }
            else {
                tvVidaJugador.
                setText(calcularPorcentajeVida(nuevoPorcentajeVida, porcentajeAtaqueMonstruo, 1).toString() + "%")
            }
        }
    }

    fun clickBtnRendirse(view: View?) {
        var tvVidaJugador = findViewById<TextView>(R.id.vidaPorcentajeJugador)
        var tvVidaMonstruo =  findViewById<TextView>(R.id.vidaPorcentajeMosntruo)

        var porcentajeVidaJugador = tvVidaJugador.getText().toString()
        var porcentajeVidaMonstruo = tvVidaMonstruo.getText().toString()

        if (porcentajeVidaJugador != "" && porcentajeVidaMonstruo != "") {
            var tvMensajeFinal = findViewById<TextView>(R.id.mensajeFinal)
            tvMensajeFinal.visibility = View.VISIBLE;
            tvMensajeFinal.setText("P E R D I S T E");
            tvVidaJugador.setText("")
            tvVidaJugador.getLayoutParams().width = 0
            tvVidaJugador.requestLayout();
        }
    }

    fun calcularAccion(width: Int, porcentajeAtaque: Int, accion: Int): Int {
        var porcentajeVida = (porcentajeAtaque * vidaCompleta) / 100
        var vidaDespAccion = 0
        if(accion == 1){
            vidaDespAccion = width - porcentajeVida
        }
        else {
            vidaDespAccion = width + porcentajeVida
        }
        return vidaDespAccion
    }

    fun calcularPorcentajeVida(porcentajeActual: String, porcentajeAccion: Int, accion: Int): Int {
        val strs = porcentajeActual.split("%").toTypedArray()
        var nuevoPorcentaje = 0
        if(accion == 1){
            nuevoPorcentaje = strs[0].toInt() - porcentajeAccion
        }
        else {
            nuevoPorcentaje = strs[0].toInt() + porcentajeAccion
        }
        return nuevoPorcentaje
    }
}
