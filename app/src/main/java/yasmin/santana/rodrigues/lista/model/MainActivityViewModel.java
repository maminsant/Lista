package yasmin.santana.rodrigues.lista.model;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivityViewModel extends ViewModel {
    List<MyItem> itens = new ArrayList<>(); //container que guarda tudo
    public List<MyItem> getItens(){
        return itens; //pega os itens
    }
}
