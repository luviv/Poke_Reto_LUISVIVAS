package com.example.pokereto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pokereto.model.Pokemon;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.gson.Gson;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class PokeActivity extends AppCompatActivity {

    private EditText searchCatch;
    private Button catchPoke;
    private EditText searchPoke;
    private Button searchBtn;
    private LinearLayoutManager layoutManager;
    private FirebaseFirestore db;
    private String userId;
    private PokeAdapter pokeAdapter;
    private RecyclerView pokeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poke);

        searchPoke = findViewById(R.id.searchPoke_edt);
        catchPoke = findViewById(R.id.catchPoke_btn);
        searchCatch = findViewById(R.id.catchPoke_edt);
        searchBtn = findViewById(R.id.poke_btn);
        pokeView = findViewById(R.id.pokeview);
        pokeView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        pokeView.setLayoutManager(layoutManager);
        db = FirebaseFirestore.getInstance();

        userId = getIntent().getExtras().getString("id");

        pokeAdapter = new PokeAdapter(userId);
        pokeView.setAdapter(pokeAdapter);

        catchPoke.setOnClickListener(
                (v) -> {
                    catchPokemon();
            }
        );

        searchBtn.setOnClickListener(
                (v) -> {
                    filterPokemon();
                }
        );

    }


    private void catchPokemon() {
        String pokemon = searchCatch.getText().toString().trim().toLowerCase();
        if (pokemon.isEmpty()) {
            Toast.makeText(this, "Ingresa el nombre de un Pokemon", Toast.LENGTH_LONG).show();
        }

        else  {
            new Thread(
                    () -> {
                         HTTPSWebUtilDomi http = new HTTPSWebUtilDomi();
                         String response = http.GETrequest("https://pokeapi.co/api/v2/pokemon/" + pokemon);
                         Gson gson = new Gson();
                         Pokemon poke = gson.fromJson(response, Pokemon.class);
                         runOnUiThread(
                                 () -> {
                                     if(poke == null) {
                                         Toast.makeText(this, "No existe ese Pokemon >:c", Toast.LENGTH_LONG).show();
                                     }
                                     else  {
                                         String pokename = poke.getName().substring(0, 1).toUpperCase() + poke.getName().substring(1) + "!";
                                         Toast.makeText(this, "¡Has atrapado un "+ pokename, Toast.LENGTH_LONG).show();
                                         poke.setTime(System.currentTimeMillis());
                                         poke.setDbid(UUID.randomUUID().toString());
                                         //pokeAdapter.addPokeInitial(poke);
                                         db.collection("users").document(userId)
                                         .collection("pokemons").document(UUID.randomUUID().toString()).set(poke);
                                         pokeAdapter.addPokeInitial(poke);
                                     }
                                 }
                         );
                    }
            ).start();
        }
    }

    public void updatePokemon() {
        pokeAdapter.clearList();
        Query query = db.collection("users").document(userId).collection("pokemons").orderBy("time", Query.Direction.DESCENDING);
        query.get().addOnCompleteListener(
                task -> {
                    for (DocumentSnapshot document : task.getResult().getDocuments()) {
                        pokeAdapter.addPokemon(document.toObject(Pokemon.class));

                    }
                }
        );
    }

    public void filterPokemon() {
        pokeAdapter.clearList();
        String pokeName = searchPoke.getText().toString().trim().toLowerCase();

        if(!pokeName.isEmpty()) {
            Query query = db.collection("users").document(userId).collection("pokemons").whereEqualTo("name", pokeName);
            query.get().addOnCompleteListener(
                    task -> {
                        for (DocumentSnapshot document : task.getResult().getDocuments()) {
                            pokeAdapter.addPokemon(document.toObject(Pokemon.class));
                        }
                    }
            );
        } else {
            updatePokemon();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updatePokemon();
    }
}