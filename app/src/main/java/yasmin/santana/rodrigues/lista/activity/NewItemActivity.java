package yasmin.santana.rodrigues.lista.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;


import yasmin.santana.rodrigues.lista.R;
import yasmin.santana.rodrigues.lista.model.NewItemActivityViewModel;

public class NewItemActivity extends AppCompatActivity {
    static int PHOTO_PICKER_REQUEST = 1;
    //Uri photoSelected = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);

        NewItemActivityViewModel vm = new ViewModelProvider(this).get(NewItemActivityViewModel.class); //declarando o vm
        Uri selectPhotoLocation = vm.getSelectPhotoLocation(); //pegando o enderço da imagem no vm
        if(selectPhotoLocation != null){
            ImageView imvphotoPreview = findViewById(R.id.imvPhotoPreview);
            imvphotoPreview.setImageURI(selectPhotoLocation);
        }

        ImageButton imgCI = findViewById(R.id.imbCl); //conecta com o butão
        imgCI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT); //abertura da galeria
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, PHOTO_PICKER_REQUEST);
            }
        });


        Button btnAddItem = findViewById(R.id.btnAddItem);

        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewItemActivityViewModel vm = new ViewModelProvider(NewItemActivity.this).get(NewItemActivityViewModel.class);
                Uri selectPhotoLocation = vm.getSelectPhotoLocation();
                if (selectPhotoLocation == null) { //bloco de erros, se algum campo estiver vazio
                    Toast.makeText(NewItemActivity.this, "É necessário selecionar uma imagem!", Toast.LENGTH_SHORT).show();
                    return;
                }
                EditText etTitle = findViewById(R.id.etTitle);
                String title = etTitle.getText().toString();
                if (title.isEmpty()) {
                    Toast.makeText(NewItemActivity.this, "É necessário inserir um título", Toast.LENGTH_LONG).show();
                    return;
                }
                EditText etDesc = findViewById(R.id.etDesc);
                String desc = etDesc.getText().toString();
                if (desc.isEmpty()) {
                    Toast.makeText(NewItemActivity.this, "É necessário inserir uma descrição", Toast.LENGTH_LONG).show();
                    return;

                }
                Intent i = new Intent(); //guarda dados que irão retornar para MainAct
                i.setData(selectPhotoLocation); //setando os itens
                i.putExtra("title", title);
                i.putExtra("desc", desc);
                setResult(Activity.RESULT_OK, i); //mostrando result se condições estiverem corretas
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PHOTO_PICKER_REQUEST){ // ve se selecionou alguma foto
            if(resultCode == Activity.RESULT_OK){
                Uri photoSelected = data.getData();
                ImageView imvphotoPreview = findViewById(R.id.imvPhotoPreview);
                imvphotoPreview.setImageURI(photoSelected); //guarda o lugar da sua foto

                NewItemActivityViewModel vm = new ViewModelProvider(this).get(NewItemActivityViewModel.class);
                vm.setSelectPhotoLocation(photoSelected); //setando a imagem na tela
            }
        }
    }
}