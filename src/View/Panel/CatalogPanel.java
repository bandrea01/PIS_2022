package View.Panel;

import Business.SessionManager;
import DAO.Articolo.ArticoloDAO;
import DAO.ArticoloPuntoVendita.ArticoloPuntoVenditaDAO;
import DAO.ClientePuntoVendita.ClientePuntoVenditaDAO;
import DAO.Magazzino.MagazzinoDAO;
import DAO.ProdottiMagazzino.ProdottiMagazzinoDAO;
import DAO.Prodotto.ProdottoDAO;
import DAO.PuntoVendita.PuntoVenditaDAO;
import DAO.Servizio.ServizioDAO;
import Model.*;
import View.ViewModel.CatalogTableModel;
import View.ViewModel.RowCatalog;
import View.ViewModel.WideComboBox;

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
        String[] vuoto = {""};
        WideComboBox puntoVenditaChooses = new WideComboBox(vuoto);
        JPanel selection = new JPanel();
        this.setLayout(new BorderLayout());
        selection.setLayout(new GridLayout(3, 10));


        if (SessionManager.getSession().isEmpty()) {

            for (Articolo a : articoloDAO.findAll()) {
                RowCatalog row = new RowCatalog();
                row.setIdArticolo(a.getId());
                row.setNome(a.getName());
                row.setCategoria(a.getCategoria().getName());
                row.setPrezzo(a.getPrezzo());
                if (articoloDAO.isProdotto(a.getId())) {
                    Prodotto p = ProdottoDAO.getInstance().findById(a.getId());
                    row.setProduttoreFornitore(p.getProduttore().getNome());
                } else {
                    Servizio s = ServizioDAO.getInstance().findById(a.getId());
                    if (s.getFornitore() != null) {
                        row.setProduttoreFornitore(s.getFornitore().getNome());
                    }
                }
                row.setChecked(false);
                row.setImageIcon(getClass().getClassLoader().getResourceAsStream("image/folder.png"));
                rows.add(row);
            }
        } else if (SessionManager.getSession().get(SessionManager.LOGGED_USER) instanceof Utente) {
            Utente utente = (Utente) SessionManager.getSession().get(SessionManager.LOGGED_USER);
            String[] puntiVenditaCliente = getPuntiVenditaCliente(utente);
            puntoVenditaChooses.removeAll();
            for (int i = 0; i < puntiVenditaCliente.length; i++) {
                puntoVenditaChooses.addItem(puntiVenditaCliente[i]);
            }
            puntoVenditaChooses.setPreferredSize(new Dimension(7,7));
            puntoVenditaChooses.setWide(true);

            puntoVenditaChooses.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    ProdottiMagazzinoDAO prodottiMagazzinoDAO = ProdottiMagazzinoDAO.getInstance();
                    String nomePunto = puntoVenditaChooses.getSelectedItem().toString();
                    PuntoVendita puntoVendita = PuntoVenditaDAO.getInstance().findByName(nomePunto);
                    Magazzino magazzino = MagazzinoDAO.getInstance().findMagazzinoByPunto(puntoVendita);
                    ArrayList<ProdottiMagazzino> prodottiMagazzino = prodottiMagazzinoDAO.findAllProdottiByMagazzino(magazzino);

                    ArrayList<Prodotto> prodotti = new ArrayList<>();
                    ProdottoDAO prodottoDAO = ProdottoDAO.getInstance();

                    for (int i = 0; i < rows.size(); i++) {
                        rows.remove(i);
                    }

                    for (int i = 0; i < prodottiMagazzino.size(); i++) {
                        prodotti.add(prodottoDAO.findById(prodottiMagazzino.get(i).getProdotto().getId()));
                    }
                    for (Prodotto p : prodotti) {
                        RowCatalog row = new RowCatalog();
                        row.setIdArticolo(p.getId());
                        row.setNome(p.getName());
                        row.setCategoria(p.getCategoria().getName());
                        row.setPrezzo(p.getPrezzo());
                        row.setProduttoreFornitore(p.getProduttore().getNome());
                        row.setChecked(false);
                        row.setImageIcon(getClass().getClassLoader().getResourceAsStream("image/folder.png"));
                        rows.add(row);
                    }
                    ArrayList<Articolo> articoli = ArticoloPuntoVenditaDAO.getInstance().findByPunto(puntoVendita);
                    for (Articolo a : articoli) {
                        if (articoloDAO.isServizio(a.getId())) {
                            Servizio s = ServizioDAO.getInstance().findById(a.getId());
                            RowCatalog row = new RowCatalog();
                            row.setIdArticolo(s.getId());
                            row.setNome(s.getName());
                            row.setCategoria(s.getCategoria().getName());
                            row.setPrezzo(s.getPrezzo());
                            row.setProduttoreFornitore(s.getFornitore().getNome());
                            row.setChecked(false);
                            row.setImageIcon(getClass().getClassLoader().getResourceAsStream("image/folder.png"));
                            rows.add(row);
                        }

                    }


                    tableModel.setRows(rows);
                    table = new JTable(tableModel);

                    table.setRowHeight(20);

                    JScrollPane scrollPane = new JScrollPane(table);
                    scrollPane.repaint(); scrollPane.validate();
                    selection.remove(puntoVenditaChooses);
                    selection.repaint(); selection.validate();
                    add(scrollPane);
                    scrollPane.repaint(); scrollPane.validate();

                    setVisible(true);
                }
            });
        }

        tableModel = new CatalogTableModel(rows);
        table = new JTable(tableModel);

        table.setRowHeight(20);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);
        if (SessionManager.getSession().get(SessionManager.LOGGED_USER) instanceof Utente) {
            selection.add(puntoVenditaChooses);
            this.add(selection, BorderLayout.NORTH);
        }

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
        this.validate(); this.repaint();
    }

    public String[] getPuntiVenditaCliente(Utente cliente) {
        ArrayList<PuntoVendita> punti = ClientePuntoVenditaDAO.getInstance().findAllbyCliente(cliente);
        String[] nomiPuntiVendita = new String[punti.size()];
        for (int i = 0; i < punti.size(); i++) {
            nomiPuntiVendita[i] = punti.get(i).getName();
        }
        return nomiPuntiVendita;
    }
}
