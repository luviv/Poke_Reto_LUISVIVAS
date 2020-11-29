package com.example.pokereto;

import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokereto.model.Pokemon;

public class PokeView extends RecyclerView.ViewHolder implements View.OnClickListener{
    private Pokemon pokemon;
    private ImageView pokeimg;
    private ConstraintLayout root;
    private TextView id;
    private TextView name;
    private OnPokemonClicked listener;

    public PokeView(ConstraintLayout root) {
        super(root);
        this.root = root;
        pokeimg = root.findViewById(R.id.poke_img);
        id = root.findViewById(R.id.poke_id);
        name = root.findViewById(R.id.poke_name);
        root.setOnClickListener(this);
    }

    public ImageView getPokeimg() {
        return pokeimg;
    }

    public ConstraintLayout getRoot() {
        return root;
    }

    public TextView getId() {
        return id;
    }

    public TextView getName() {
        return name;
    }

    @Override
    public void onClick(View view) {
        if (listener != null) listener.OnPokemonClicked(this.pokemon, this.root);
    }

    public interface OnPokemonClicked {
        void OnPokemonClicked(Pokemon pokemon, View v);
    }

    public OnPokemonClicked getListener() {
        return listener;
    }

    public void setListener(OnPokemonClicked listener) {
        this.listener = listener;
    }

    public Pokemon getPokemon() {
        return pokemon;
    }

    public void setPokemon(Pokemon pokemon) {
        this.pokemon = pokemon;
    }
}
