package View.Panel;

import DAO.Articolo.ArticoloDAO;
import DAO.Prodotto.ProdottoDAO;
import DAO.Servizio.ServizioDAO;
import Model.Articolo;
import Model.Prodotto;
import Model.Servizio;
import View.ViewModel.CatalogTableModel;
import View.ViewModel.RowCatalog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class CatalogPanel extends JPanel {

    public CatalogPanel() {
        setLayout(new BorderLayout());
        List<RowCatalog> rows = new ArrayList<>();
        ArticoloDAO articoloDAO = ArticoloDAO.getInstance();

        for (Articolo a : articoloDAO.findAll()){
            RowCatalog row = new RowCatalog();
            row.setIdArticolo(a.getId());
            row.setNome(a.getName());
            row.setCategoria(a.getCategoria().getName());
            row.setPrezzo(a.getPrezzo());
            if (articoloDAO.isProdotto(a.getId())){
                Prodotto p = ProdottoDAO.getInstance().findById(a.getId());
                row.setProduttoreFornitore(p.getProduttore().getNome());
            } else {
                Servizio s = ServizioDAO.getInstance().findById(a.getId());
                row.setProduttoreFornitore(s.getFornitore().getNome());
            }
            row.setChecked(false);
            row.setImageIcon(getClass().getClassLoader().getResourceAsStream("image/folder.png"));
            rows.add(row);
        }

        CatalogTableModel tableModel = new CatalogTableModel(rows);
        JTable table = new JTable(tableModel);

        table.setRowHeight(20);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);

        JScrollBar bar = new JScrollBar();
        this.add(bar, BorderLayout.EAST);

        JPanel buttonsTable = new JPanel();
        buttonsTable.setLayout(new FlowLayout());
        JButton cart = new JButton("Put in cart");

        cart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] selectedRows = table.getSelectedRows();
                System.out.println(selectedRows[0]);
                CatalogTableModel tModel = (CatalogTableModel) table.getModel();

                for (int row : selectedRows) {
                    RowCatalog selectedRow = tModel.getRows().get(row);
                    System.out.println("Id Prodotto selezionato: " + selectedRow.getIdArticolo());
                }
            }
        });

        buttonsTable.add(cart);
        add(cart, BorderLayout.SOUTH);

    }
}