package View.ViewModel;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class CatalogTableModel extends AbstractTableModel {

    private List<RowCatalog> rows;

    public CatalogTableModel(List<RowCatalog> rows) {
        this.rows = rows;
    }

    public List<RowCatalog> getRows() {
        return rows;
    }

    public void setRows(List<RowCatalog> rows) {
        this.rows = rows;
    }

    @Override
    public int getRowCount() {
        return rows.size();
    }
    @Override
    public int getColumnCount() {
        return 7;
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        RowCatalog row = rows.get(rowIndex);

        switch(columnIndex) {
            case 0: return row.getIdArticolo();
            case 1: return row.getNome();
            case 2: return row.getProduttoreFornitore();
            case 3: return row.getCategoria();
            case 4: return row.getPrezzo();
            case 5: return row.getChecked();
            case 6:
                InputStream stream = getClass().getResourceAsStream("/" + row.getPathImage());
                try {
                    ImageIcon icon = new ImageIcon(ImageIO.read(stream));
                    return icon;
                } catch (IOException e) {
                    e.printStackTrace();
                    return new ImageIcon();
                }
        }
        return null;
    }
    @Override
    public String getColumnName(int columnIndex) {
        switch(columnIndex) {
            case 0: return "ID";
            case 1: return "Name";
            case 2: return "Productor/Supplier";
            case 3: return "Category";
            case 4: return "Price (€)";
            case 5: return "Select";
            case 6: return "Images";
        }
        return null;
    }
    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex){
        RowCatalog row = rows.get(rowIndex);
        switch(columnIndex) {
            case 0: row.setIdArticolo(Integer.parseInt(value.toString()));
            case 1: row.setNome(value.toString());
            case 2: row.setProduttoreFornitore(value.toString());
            case 3: row.setCategoria(value.toString());
            case 4: row.setPrezzo(Float.parseFloat(value.toString()));
            case 5: row.setChecked(Boolean.parseBoolean(value.toString()));
        }
    }
    @Override
    public Class getColumnClass(int columnIndex) {
        if (columnIndex == 5) return Boolean.class;
        if (columnIndex == 6) return ImageIcon.class;
        return Object.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 4;
    }
}
