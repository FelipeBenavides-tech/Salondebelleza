
package view.components;

import java.awt.*;

public class CustomPanel extends Canvas {
    private Color backgroundColor;
    private Color borderColor;
    private String title;
    private Font titleFont;
    private boolean showTitle;
    private Component[] components = new Component[50]; // Capacidad inicial
    private int componentCount = 0;
    
    // Colores predefinidos para el salón de belleza
    private static final Color BEIGE_LIGHT = new Color(245, 245, 220);
    private static final Color GOLD = new Color(212, 175, 55);
    private static final Color DARK_GRAY = new Color(50, 50, 50);
    
    public CustomPanel() {
        this(null);
    }
    
    public CustomPanel(String title) {
        this.title = title;
        this.showTitle = (title != null && !title.isEmpty());
        this.backgroundColor = BEIGE_LIGHT;
        this.borderColor = GOLD;
        this.titleFont = new Font("Georgia", Font.BOLD, 16);
        
        setLayout(null); // Usaremos posicionamiento absoluto
    }
    
    public void addComponent(Component component, int x, int y, int width, int height) {
        component.setBounds(x, y, width, height);
        components[componentCount++] = component;
        add(component);
    }
    
    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int width = getWidth();
        int height = getHeight();
        
        // Dibujar fondo
        g2d.setColor(backgroundColor);
        g2d.fillRoundRect(0, 0, width, height, 15, 15);
        
        // Dibujar borde
        g2d.setColor(borderColor);
        g2d.drawRoundRect(0, 0, width - 1, height - 1, 15, 15);
        
        // Dibujar título si existe
        if (showTitle) {
            int titleHeight = 30;
            g2d.setColor(borderColor);
            g2d.fillRoundRect(0, 0, width, titleHeight, 15, 15);
            g2d.fillRect(0, titleHeight / 2, width, titleHeight / 2);
            
            g2d.setColor(Color.WHITE);
            g2d.setFont(titleFont);
            FontMetrics metrics = g2d.getFontMetrics(titleFont);
            int x = (width - metrics.stringWidth(title)) / 2;
            int y = titleHeight - (titleHeight - metrics.getHeight()) / 2 - metrics.getDescent();
            g2d.drawString(title, x, y);
        }
        
        // Dibujar componentes
        for (int i = 0; i < componentCount; i++) {
            components[i].paint(g);
        }
    }
}