
package view;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


class CustomTable extends Canvas {
    private String[] columnNames;
    private Object[][] data;
    private int selectedRow = -1;
    private int headerHeight = 40;
    private int rowHeight = 35;
    private int[] columnWidths;
    private Color headerColor = new Color(219, 112, 147);
    private Color headerTextColor = Color.WHITE;
    private Color rowEvenColor = Color.WHITE;
    private Color rowOddColor = new Color(245, 245, 245);
    private Color selectedRowColor = new Color(255, 192, 203);
    private Color borderColor = new Color(200, 200, 200);
    private Color textColor = new Color(50, 50, 50);
    private Font headerFont = new Font("Georgia", Font.BOLD, 14);
    private Font contentFont = new Font("Verdana", Font.PLAIN, 14);
    
    private int scrollPosition = 0;
    private int maxVisibleRows;
    
    /**
     * Constructor para tablas personalizadas
     * @param columnNames Nombres de columnas
     * @param data Datos de la tabla
     */
    public CustomTable(String[] columnNames, Object[][] data) {
        this.columnNames = columnNames;
        this.data = data;
        this.columnWidths = new int[columnNames.length];
        
        // Calcular anchos de columna iniciales
        initializeColumnWidths();
        
        setPreferredSize(new Dimension(800, 400));
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int y = e.getY();
                
                // Ignorar clics en el encabezado
                if (y > headerHeight) {
                    int clickedRow = (y - headerHeight) / rowHeight + scrollPosition;
                    if (clickedRow >= 0 && clickedRow < data.length) {
                        selectedRow = clickedRow;
                        repaint();
                    }
                }
            }
        });
        
        addMouseWheelListener(e -> {
            int notches = e.getWheelRotation();
            scrollPosition += notches;
            
            // Limitar el scroll
            if (scrollPosition < 0) {
                scrollPosition = 0;
            }
            
            int maxScroll = Math.max(0, data.length - maxVisibleRows);
            if (scrollPosition > maxScroll) {
                scrollPosition = maxScroll;
            }
            
            repaint();
        });
    }
    
    /**
     * Inicializa los anchos de columna
     */
    private void initializeColumnWidths() {
        // Distribución uniforme de las columnas
        for (int i = 0; i < columnNames.length; i++) {
            columnWidths[i] = 1; // Proporción relativa
        }
    }
    
    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int width = getWidth();
        int height = getHeight();
        
        // Calcular tamaño de la tabla
        maxVisibleRows = (height - headerHeight) / rowHeight;
        
        // Calcular anchos de columna reales
        int totalColumnWidth = 0;
        for (int i = 0; i < columnWidths.length; i++) {
            totalColumnWidth += columnWidths[i];
        }
        
        int[] actualColumnWidths = new int[columnWidths.length];
        int accumulatedWidth = 0;
        
        for (int i = 0; i < columnWidths.length; i++) {
            actualColumnWidths[i] = (i == columnWidths.length - 1)
                ? width - accumulatedWidth
                : (int)(width * ((double)columnWidths[i] / totalColumnWidth));
            accumulatedWidth += actualColumnWidths[i];
        }
        
        // Dibujar encabezado
        g2d.setColor(headerColor);
        g2d.fillRect(0, 0, width, headerHeight);
        
        g2d.setColor(headerTextColor);
        g2d.setFont(headerFont);
        
        int x = 0;
        for (int i = 0; i < columnNames.length; i++) {
            // Centrar texto en la columna
            FontMetrics fm = g2d.getFontMetrics();
            int textWidth = fm.stringWidth(columnNames[i]);
            int textX = x + (actualColumnWidths[i] - textWidth) / 2;
            
            g2d.drawString(columnNames[i], textX, headerHeight/2 + fm.getAscent()/2 - 2);
            x += actualColumnWidths[i];
        }
        
        // Dibujar filas
        g2d.setFont(contentFont);
        
        int lastRow = Math.min(scrollPosition + maxVisibleRows, data.length);
        
        for (int row = scrollPosition; row < lastRow; row++) {
            int y = headerHeight + (row - scrollPosition) * rowHeight;
            
            // Color de fondo de la fila
            if (row == selectedRow) {
                g2d.setColor(selectedRowColor);
            } else if (row % 2 == 0) {
                g2d.setColor(rowEvenColor);
            } else {
                g2d.setColor(rowOddColor);
            }
            
            g2d.fillRect(0, y, width, rowHeight);
            
            // Dibujar datos
            g2d.setColor(textColor);
            
            x = 0;
            for (int col = 0; col < columnNames.length; col++) {
                // Centrar texto en la columna
                FontMetrics fm = g2d.getFontMetrics();
                String text = data[row][col] != null ? data[row][col].toString() : "";
                int textWidth = fm.stringWidth(text);
                int textX = x + 10; // Alineado a la izquierda con un pequeño margen
                
                g2d.drawString(text, textX, y + rowHeight/2 + fm.getAscent()/2 - 2);
                
                x += actualColumnWidths[col];
            }
            
            // Dibujar línea horizontal debajo de la fila
            g2d.setColor(borderColor);
            g2d.drawLine(0, y + rowHeight, width, y + rowHeight);
        }
        
        // Dibujar líneas verticales
        g2d.setColor(borderColor);
        
        x = 0;
        for (int i = 0; i < columnNames.length; i++) {
            x += actualColumnWidths[i];
            g2d.drawLine(x, 0, x, headerHeight + (lastRow - scrollPosition) * rowHeight);
        }
        
        // Dibujar indicador de desplazamiento si es necesario
        if (data.length > maxVisibleRows) {
            int scrollBarWidth = 15;
            int scrollBarHeight = height - headerHeight;
            int scrollBarX = width - scrollBarWidth;
            
            // Fondo de la barra de desplazamiento
            g2d.setColor(new Color(240, 240, 240));
            g2d.fillRect(scrollBarX, headerHeight, scrollBarWidth, scrollBarHeight);
            
            // Calculamos el tamaño y posición del slider
            int sliderHeight = Math.max(50, scrollBarHeight * maxVisibleRows / data.length);
            int sliderY = headerHeight + (scrollBarHeight - sliderHeight) * scrollPosition / (data.length - maxVisibleRows);
            
            g2d.setColor(new Color(200, 200, 200));
            g2d.fillRoundRect(scrollBarX + 2, sliderY, scrollBarWidth - 4, sliderHeight, 5, 5);
        }
    }
    
    /**
     * Establece los datos de la tabla
     * @param data Nuevos datos
     */
    public void setData(Object[][] data) {
        this.data = data;
        scrollPosition = 0;
        selectedRow = -1;
        repaint();
    }
    
    /**
     * Obtiene la fila seleccionada
     * @return Índice de la fila seleccionada o -1 si no hay selección
     */
    public int getSelectedRow() {
        return selectedRow;
    }
    
    /**
     * Obtiene los datos de la fila seleccionada
     * @return Array con los datos de la fila seleccionada o null si no hay selección
     */
    public Object[] getSelectedRowData() {
        if (selectedRow >= 0 && selectedRow < data.length) {
            return data[selectedRow];
        }
        return null;
    }
    
    /**
     * Establece los anchos de columna
     * @param widths Array con las proporciones de ancho de cada columna
     */
    public void setColumnWidths(int[] widths) {
        if (widths.length == columnNames.length) {
            this.columnWidths = widths;
            repaint();
        }
    }
}