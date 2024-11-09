package dvi.proyectoTarea.recursos.varios;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class CustomTableCellRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        if (column == table.getColumn("Estat").getModelIndex()) {
            String status = (String) value;
            switch (status) {
                case "Suspenso":
                    cell.setBackground(Color.RED);
                    break;
                case "Aprovado":
                    cell.setBackground(Color.GREEN);
                    break;
                case "Pendiente":
                    cell.setBackground(Color.YELLOW);
                    break;
                default:
                    cell.setBackground(Color.WHITE);
                    break;
            }
        } else {
            cell.setBackground(Color.WHITE);
        }

        return cell;
    }
}
