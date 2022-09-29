package Test.DAOTest;

import Business.ImmagineBusiness;
import DAO.Articolo.ArticoloDAO;
import DAO.Articolo.ImmagineDAO;
import DAO.Categoria.CategoriaDAO;
import DAO.Prodotto.ProdottoDAO;
import DAO.Produttore.ProduttoreDAO;
import Model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;

public class ImmagineDAOTest {
    Categoria categoria = new Categoria(1000, "categoria", null);
    Produttore produttore = new Produttore(1000, "produttore", "sito", "citta", "nazione");
    Prodotto prodotto = new Prodotto(1000, "articolo", 100F, "descrizione", categoria, produttore);
    Immagine immagine = new Immagine(1000,1000, ImmagineBusiness.getInstance().getImageByFilename("Fridge.png"));

    @Before
    public void setUp(){
        CategoriaDAO.getInstance().add(categoria);
        ProduttoreDAO.getInstance().add(produttore);
        ArticoloDAO.getInstance().addProdotto(prodotto);
        ImmagineDAO.getInstance().add(immagine);
    }
    @Test
    public void showImage(){
        Image image = ImmagineDAO.getInstance().findImageById(1000).getImage();
        ImageIcon icon = new ImageIcon(image);
        JOptionPane.showMessageDialog(null, "Images:", "title", JOptionPane.QUESTION_MESSAGE, icon);
    }
    @After
    public void tearDown(){
        ImmagineDAO.getInstance().removeSingleImageById(1000);
        ArticoloDAO.getInstance().removeById(1000);
        ProduttoreDAO.getInstance().remove(produttore);
        CategoriaDAO.getInstance().remove(categoria);
    }

}
