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

    private CatalogTableModel tableModel;
    private JTable table;

    public CatalogPanel() {
        setLayout(new BorderLayout());
        List<RowCatalog> rows = new ArrayList<>();
        ArticoloDAO articoloDAO = ArticoloDAO.getInstance();


        for (Articolo a : articoloDAO.findAll()) {
            RowCatalog row = new RowCatalog();
            row.setIdArticolo(a.getId());
            row.setNome(a.getName());
            row.setCategoria(a.getCategoria().getName());
            row.setPrezzo(a.getPrezzo());
            if (articoloDAO.isProdotto(a.getId())) {
                Prodotto p = ProdottoDAO.getInstance().findById(a.getId());
                row.setProduttoreFornitore(p.getProduttore().getNome());
                row.setPathImage(a.getPathImmagine());
            } else {
                Servizio s = ServizioDAO.getInstance().findById(a.getId());
                if (s.getFornitore() != null) {
                    row.setProduttoreFornitore(s.getFornitore().getNome());
                    row.setPathImage("servizio.png");
                }
            }
            row.setChecked(false);
            row.setImageIcon(getClass().getClassLoader().getResourceAsStream("image/folder.png"));
            rows.add(row);
        }


        tableModel = new CatalogTableModel(rows);
        table = new JTable(tableModel);

        table.setRowHeight(200);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);




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
                    System.out.println("Select ID product: " + selectedRow.getIdArticolo());
                }
            }
        });

        //buttonsTable.add(cart);
        //add(cart, BorderLayout.SOUTH);
        this.validate(); this.repaint();
    }

}
