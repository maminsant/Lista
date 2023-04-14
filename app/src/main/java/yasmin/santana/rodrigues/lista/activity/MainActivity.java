package yasmin.santana.rodrigues.lista.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import yasmin.santana.rodrigues.lista.R;
import yasmin.santana.rodrigues.lista.adapter.MyAdapter;
import yasmin.santana.rodrigues.lista.model.MyItem;

public class MainActivity extends AppCompatActivity {
    static int NEW_ITEM_REQUEST = 1;
    List<MyItem> itens = new ArrayList<>();
    MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionButton fabAddItem= findViewById(R.id.fabAddNewItem);
        fabAddItem.setOnClickListener(new View.OnClickListener(){ //pega clicks
            @Override
            public void onClick(View v){
                Intent i = new Intent(MainActivity.this, NewItemActivity.class);
                startActivityForResult(i, NEW_ITEM_REQUEST); //assume que a new item classe vai enviar dados em algum momento para a main
            }
        });
        RecyclerView rvItens = findViewById(R.id.rvItens); // recyclerview: os itens que ficaram fora da parte visível da tela são “excluídos” e reutilizados para gerar //
        // os novos itens que estão na sequência da lista, aumentando a fluidez da lista

        myAdapter = new MyAdapter(this, itens);
        rvItens.setAdapter(myAdapter);

        rvItens.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvItens.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvItens.getContext(), DividerItemDecoration.VERTICAL);
        rvItens.addItemDecoration(dividerItemDecoration);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) { //função
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == NEW_ITEM_REQUEST){ //ve se o resultado está dentro dos parãmetros esperados
            if(resultCode == Activity.RESULT_OK){
                MyItem myItem = new MyItem(); //recebe os dados que new item trouxr
                myItem.title = data.getStringExtra("title");
                myItem.desc = data.getStringExtra("desc");
                myItem.photo = data.getData();

                itens.add(myItem); //add as informações na lista
                myAdapter.notifyItemInserted(itens.size()-1); //atualiza o recycle view
            }
        }
    }


}