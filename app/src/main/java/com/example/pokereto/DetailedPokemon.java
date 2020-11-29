package com.example.pokereto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.pokereto.model.Pokemon;
import com.google.firebase.firestore.FirebaseFirestore;

public class DetailedPokemon extends AppCompatActivity {

    private TextView pokeName;
    private TextView pokeType;
    private TextView defense;
    private TextView attack;
    private TextView velocity;
    private TextView life;
    private TextView defenseStat;
    private TextView attackStat;
    private TextView velocityStat;
    private TextView lifeStat;
    private ImageView spriteImg;
    private Button releaseBtn;
    private Pokemon pokemon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_pokemon);

        pokeName = findViewById(R.id.pokeName_text);
        pokeType = findViewById(R.id.typePokemon_text);
        defense = findViewById(R.id.defensePokemon_text);
        attack = findViewById(R.id.attackPokemon_text);
        velocity = findViewById(R.id.velocityPokemon_text);
        life = findViewById(R.id.lifePokemon_text);
        defenseStat = findViewById(R.id.attackStat);
        attackStat = findViewById(R.id.attackStat);
        velocityStat = findViewById(R.id.velocityStat);
        lifeStat = findViewById(R.id.lifeStat);
        spriteImg = findViewById(R.id.spriteImage_img);
        releaseBtn = findViewById(R.id.release_btn);
        pokemon = (Pokemon) getIntent().getExtras().getSerializable("pokemon");

        pokeName.setText(pokemon.getName().substring(0, 1).toUpperCase() + pokemon.getName().substring(1));
        lifeStat.setText(""+pokemon.getStats().get(0).getBaseStat());
        attackStat.setText(""+pokemon.getStats().get(1).getBaseStat());
        defenseStat.setText(""+pokemon.getStats().get(2).getBaseStat());
        velocityStat.setText(""+pokemon.getStats().get(5).getBaseStat());

        Glide.with(this).load(pokemon.getSprites().getSprite()).into(spriteImg);

        String type = pokemon.getTypes().get(0).getType().getName();
        if (pokemon.getTypes().size() > 1) {
            type += ", " + pokemon.getTypes().get(1).getType().getName();
        }

        releaseBtn.setOnClickListener(this::removePokemon);
        
    }

    private void removePokemon(View v) {
        String uid = getIntent().getExtras().getString("id");
        FirebaseFirestore.getInstance().collection("users").document(uid).collection("pokemons").document(pokemon.getDbid()).delete();
        finish();
    }
}