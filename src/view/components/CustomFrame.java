
package view.components;

import java.awt.*;
import java.awt.event.*;

public class CustomFrame {
    private Frame frame;
    private Color backgroundColor;
    private Color accentColor;
    private Font titleFont;
    private Font contentFont;
    
    // Paleta de colores elegante para salón de belleza (tonos pastel y dorados)
    private static final Color BEIGE_LIGHT = new Color(245, 245, 220);
    private static final Color PINK_PASTEL = new Color(255, 209, 220);
    private static final Color GOLD_ACCENT = new Color(212, 175, 55);
    private static final Color DARK_GRAY = new Color(50, 50, 50);
    private static final Color WHITE = new Color(255, 255, 255);
    
    public CustomFrame(String title, int width, int height) {
        frame = new Frame(title);
        backgroundColor = BEIGE_LIGHT;
        accentColor = GOLD_ACCENT;
        titleFont = new Font("Georgia", Font.BOLD, 18);
        contentFont = new Font("Verdana", Font.PLAIN, 14);
        
        frame.setSize(width, height);
        frame.setLayout(new BorderLayout());
        frame.setBackground(backgroundColor);
        
        // Agregar barra de título personalizada
        Panel titleBar = new Panel();
        titleBar.setLayout(new BorderLayout());
        titleBar.setBackground(accentColor);
        titleBar.setPreferredSize(new Dimension(width, 40));
        
        Label titleLabel = new Label(title, Label.CENTER);
        titleLabel.setFont(titleFont);
        titleLabel.setForeground(WHITE);
        titleBar.add(titleLabel, BorderLayout.CENTER);
        
        // Botón de cierre personalizado
        Panel closeButtonPanel = new Panel();
        closeButtonPanel.setBackground(accentColor);
        Button closeButton = new Button("×");
        closeButton.setFont(new Font("Arial", Font.BOLD, 18));
        closeButton.setForeground(WHITE);
        closeButton.addActionListener(e -> frame.dispose());
        closeButtonPanel.add(closeButton);
        titleBar.add(closeButtonPanel, BorderLayout.EAST);
        
        // Agregar funcionalidad de arrastrar la ventana
        titleBar.addMouseMotionListener(new MouseMotionAdapter() {
            private int initialX, initialY;
            
            @Override
            public void mouseDragged(MouseEvent e) {
                int newX = frame.getLocation().x + (e.getX() - initialX);
                int newY = frame.getLocation().y + (e.getY() - initialY);
                frame.setLocation(newX, newY);
            }
            
            @Override
            public void mouseMoved(MouseEvent e) {
                initialX = e.getX();
                initialY = e.getY();
            }
        });
        
        frame.add(titleBar, BorderLayout.NORTH);
        
        // Agregar manejador de cierre de ventana
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                frame.dispose();
            }
        });
    }
    
    public void addComponent(Component component, String position) {
        frame.add(component, position);
    }
    
    public void setVisible(boolean visible) {
        frame.setVisible(visible);
        // Centrar en pantalla
        if (visible) {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int x = (screenSize.width - frame.getWidth()) / 2;
            int y = (screenSize.height - frame.getHeight()) / 2;
            frame.setLocation(x, y);
        }
    }
    
    public Frame getFrame() {
        return frame;
    }
}