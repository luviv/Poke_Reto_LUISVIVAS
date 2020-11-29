package com.example.pokereto;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.bumptech.glide.Glide;
import com.example.pokereto.model.Pokemon;

import java.util.ArrayList;

public class PokeAdapter extends RecyclerView.Adapter<PokeView> implements PokeView.OnPokemonClicked {

    private ArrayList<Pokemon> pokemons;
    private String id;

    public PokeAdapter(String id) {
        pokemons = new ArrayList<>();
        this.id = id;
    }

    @NonNull
    @Override
    public PokeView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View row = inflater.inflate(R.layout.pokelayout, parent, false);
        ConstraintLayout rowcontainer = (ConstraintLayout) row;
        PokeView pokeView = new PokeView(rowcontainer);
        return pokeView;
    }

    @Override
    public void onBindViewHolder(@NonNull PokeView holder, int position) {
        Pokemon pokemon = pokemons.get(position);
        holder.getName().setText(pokemon.getName().substring(0, 1).toUpperCase()+pokemon.getName().substring(1));
        holder.getId().setText("" + pokemon.getId());
        holder.setPokemon(pokemon);
        Glide.with(holder.getRoot()).load(pokemon.getSprites().getSprite()).into(holder.getPokeimg());
        holder.setListener(this);
    }

    @Override
    public int getItemCount() {
        return pokemons.size();
    }

    public void addPokemon(Pokemon pokemon) {
        pokemons.add(pokemon);
        this.notifyDataSetChanged();
    }

    public void addPokeInitial(Pokemon pokemon) {
        pokemons.add(0,pokemon);
        this.notifyDataSetChanged();
    }

    public void clearList() {
        pokemons.clear();
        this.notifyDataSetChanged();
    }

    @Override
    public void OnPokemonClicked(Pokemon pokemon, View v) {
        Intent i = new Intent(v.getContext(), DetailedPokemon.class);
        i.putExtra("pokemon", pokemon);
        i.putExtra("id", id);
        v.getContext().startActivity(i);
    }
}
