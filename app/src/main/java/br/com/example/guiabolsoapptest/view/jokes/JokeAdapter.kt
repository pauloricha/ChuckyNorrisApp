package br.com.example.guiabolsoapptest.view.jokes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.example.guiabolsoapptest.R
import br.com.example.guiabolsoapptest.model.Joke
import kotlinx.android.synthetic.main.item_adapter_joke.view.*
import java.util.*

class JokeAdapter(private val jokes: ArrayList<Joke>, private var isFavoriteScreen: Boolean = true,
                  private val jokeAdapterClickListener: JokeAdapterClickListener) :
    RecyclerView.Adapter<JokeAdapter.ViewHolder>() {

    var onItemClick: ((Int) -> Unit)? = null

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val jokeDescription = view.tvJokeDescription
        private val imgClick = view.imgClick

        fun bind(
            mJoke: Joke,
            isFavoriteScreen: Boolean,
            jokeAdapterClickListener: JokeAdapterClickListener
        ) {
            jokeDescription.text = mJoke.value

            if(isFavoriteScreen){
                imgClick.setBackgroundResource(R.drawable.ic_delete);
                imgClick.setOnClickListener {
                    jokeAdapterClickListener.onJokeAdapterClickListener(mJoke)
                }
            } else {
                imgClick.setBackgroundResource(R.drawable.ic_arrow_details);
                imgClick.isClickable = false
            }
        }
    }

    fun update(mJokes: List<Joke>, isFavoriteScreen: Boolean?) {
        this.isFavoriteScreen = isFavoriteScreen!!
        jokes.clear()
        jokes.addAll(mJokes)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(jokes[position], isFavoriteScreen, jokeAdapterClickListener)

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(position)
        }
    }

    override fun getItemCount(): Int = jokes.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_adapter_joke,
            parent, false)
    )
}

interface JokeAdapterClickListener {
    fun onJokeAdapterClickListener(joke: Joke)
}