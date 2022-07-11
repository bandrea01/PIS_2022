package View;

import View.ViewModel.RowCatalog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class CatalogoPanel extends JPanel {

    public CatalogoPanel() {
        setLayout(new BorderLayout());

        String[][] dati = new String[3][5];
        dati[0][0] = "a";
        dati[0][1] = "b";
        dati[0][2] = "c";
        dati[0][3] = "d";
        dati[0][4] = "e";
        dati[1][0] = "f";
        dati[1][1] = "g";
        dati[1][2] = "h";
        dati[1][3] = "i";
        dati[1][4] = "j";
        dati[2][0] = "k";
        dati[2][1] = "l";
        dati[2][2] = "m";
        dati[2][3] = "n";
        dati[2][4] = "o";
        String[] nomiColonne = new String[] {"Colonna 1", "Colonna 2", "Colonna 3", "Colonna 4", "Colonna 5"};

        List<RowCatalog> rows = new ArrayList<RowCatalog>();
        //popoliamo dati fake (poi DAO) TODO
        RowCatalog row1 = new RowCatalog();
        row1.setIdProdotto(234);
        row1.setNomeProdotto("Tavolo giardino");
        row1.setNomeProduttore("X");
        row1.setNomeCategoria("Esterni");
        row1.setPrezzo(99.9F);
        row1.setSelezionato(false);

        RowCatalog row2 = new RowCatalog();
        row2.setIdProdotto(123);
        row2.setNomeProdotto("Lampadario");
        row2.setNomeProduttore("Y");
        row2.setNomeCategoria("Interni");
        row2.setPrezzo(119.9F);
        row2.setSelezionato(false);

        RowCatalog row3 = new RowCatalog();
        row3.setIdProdotto(678);
        row3.setNomeProdotto("Frigorifero");
        row3.setNomeProduttore("Z");
        row3.setNomeCategoria("Cucina");
        row3.setPrezzo(299.9F);
        row3.setSelezionato(false);

        rows.add(row1);
        rows.add(row2);
        rows.add(row3);

        CatalogoTableModel tableModel = new CatalogoTableModel(rows);
        JTable table = new JTable(tableModel);

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
                CatalogoTableModel tModel = (CatalogoTableModel) table.getModel();

                for (int i = 0; i < selectedRows.length; i++) {
                    RowCatalog selectedRow = tModel.getRows().get(selectedRows[i]);
                    System.out.println("Id Prodotto selezionato: " + selectedRow.getIdProdotto());
                }
            }
        });

        buttonsTable.add(cart);
        add(cart, BorderLayout.SOUTH);

    }
}
