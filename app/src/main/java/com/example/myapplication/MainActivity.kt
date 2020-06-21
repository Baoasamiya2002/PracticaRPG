package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {

    var vidaCompleta: Int = 745
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private var listaValoresAccion: Array<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTitle("Práctica RPG ®")
    }

    fun clickBtnJugar(view: View?) {
        var tilNombreJugador = findViewById<TextInputLayout>(R.id.jugadorText)
        var liDuelo = findViewById<LinearLayout>(R.id.duelo)
        var tvNombreJugador =  findViewById<TextView>(R.id.jugador)

        var nombreIngresado = tilNombreJugador.getEditText()?.getText().toString()

        var tvVidaJugador = findViewById<TextView>(R.id.vidaPorcentajeJugador)
        var tvVidaMonstruo =  findViewById<TextView>(R.id.vidaPorcentajeMosntruo)
        var tvMensajeFinal = findViewById<TextView>(R.id.mensajeFinal)

        if (!nombreIngresado.trim().isEmpty()) {
            liDuelo.visibility = View.VISIBLE
            tvNombreJugador.text = nombreIngresado

            tvVidaMonstruo.measure(0, 0)
            tvVidaMonstruo.getLayoutParams().width = 745
            tvVidaMonstruo.requestLayout()
            tvVidaMonstruo.setText("100%")

            tvVidaJugador.measure(0, 0)
            tvVidaJugador.getLayoutParams().width = 745
            tvVidaJugador.requestLayout()
            tvVidaJugador.setText("100%")

            tvMensajeFinal.visibility = View.INVISIBLE
            mostrarListaRecycleView("vacio", 0)
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

            mostrarListaRecycleView("Golpeaste al monstruo por ", porcentajeAtaqueJugador)
            mostrarListaRecycleView("Monstruo golpeó por ", porcentajeAtaqueMonstruo)

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
                tvVidaJugador.requestLayout()
            }
            else if (calcularPorcentajeVida(porcentajeVidaMonstruo, porcentajeAtaqueJugador, 1) <= 0){
                var tvMensajeFinal = findViewById<TextView>(R.id.mensajeFinal)
                tvMensajeFinal.visibility = View.VISIBLE
                tvMensajeFinal.setText("Ganaste")
                tvVidaMonstruo.setText("")
                tvVidaMonstruo.getLayoutParams().width = 0
                tvVidaMonstruo.requestLayout()
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

            mostrarListaRecycleView("El jugador se curó por ", porcentajeCurar)
            mostrarListaRecycleView("Monstruo golpeó por ", porcentajeAtaqueMonstruo)

            tvVidaJugador.measure(0, 0)

            var widthJugador = calcularAccion(tvVidaJugador.getLayoutParams().width, porcentajeCurar, 2)

            tvVidaJugador.getLayoutParams().width = widthJugador
            tvVidaJugador.requestLayout()

            var nuevoPorcentajeVida = calcularPorcentajeVida(porcentajeVidaJugador, porcentajeCurar, 2).toString() + "%"

            widthJugador = calcularAccion(tvVidaJugador.getLayoutParams().width, porcentajeAtaqueMonstruo, 1)

            tvVidaJugador.getLayoutParams().width = widthJugador
            tvVidaJugador.requestLayout()

            if(calcularPorcentajeVida(nuevoPorcentajeVida, porcentajeAtaqueMonstruo, 1) <= 0){
                var tvMensajeFinal = findViewById<TextView>(R.id.mensajeFinal)
                tvMensajeFinal.visibility = View.VISIBLE
                tvMensajeFinal.setText("P E R D I S T E")
                tvVidaJugador.setText("")
                tvVidaJugador.getLayoutParams().width = 0
                tvVidaJugador.requestLayout()
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
            tvMensajeFinal.visibility = View.VISIBLE
            tvMensajeFinal.setText("P E R D I S T E")
            tvVidaJugador.setText("")
            tvVidaJugador.getLayoutParams().width = 0
            tvVidaJugador.requestLayout()
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

    fun mostrarListaRecycleView(accion: String, porcentajeAccion: Int) {
        generarLista(accion, porcentajeAccion)

        viewManager = LinearLayoutManager(this)
        viewAdapter = listaValoresAccion?.let { MyAdapter(it) }!!

        recyclerView = findViewById<RecyclerView>(R.id.my_recycler_view).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter

        }
    }

    private fun generarLista(accion: String, porcentajeAccion: Int){
        if(accion == "vacio"){
            listaValoresAccion = emptyArray()
        } else {
            val list: MutableList<String> = listaValoresAccion!!.toMutableList()
            list.add(0, accion + porcentajeAccion)
            listaValoresAccion = list.toTypedArray()
        }
    }

    class MyAdapter(private val myDataset: Array<String>) :
        RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

        class MyViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)

        override fun getItemViewType(position: Int): Int {
            val accion = myDataset[position].split(" ").toTypedArray()
            var viewType = 0 //Default is 1
            if(accion[0] == "Monstruo") viewType = 1
            if(accion[0] == "El") viewType = 2
            return viewType
        }

        override fun onCreateViewHolder(parent: ViewGroup,
                                        viewType: Int): MyViewHolder {
            if(viewType == 0)
            {
                var textView = LayoutInflater.from(parent.context)
                        .inflate(R.layout.list_item_ataque_j, parent, false) as TextView
                return MyViewHolder(textView)

            }
            else if (viewType == 1) {
                var textView = LayoutInflater.from(parent.context)
                        .inflate(R.layout.list_item_ataque_m, parent, false) as TextView
                return MyViewHolder(textView)
            }
            else
            {
                var textView = LayoutInflater.from(parent.context)
                        .inflate(R.layout.list_item_curar_j, parent, false) as TextView
                return MyViewHolder(textView)
            }
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.textView.text = myDataset[position]
        }

        // Return the size of your dataset (invoked by the layout manager)
        override fun getItemCount() = myDataset.size
    }

}
