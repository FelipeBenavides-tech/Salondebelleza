
package view;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

class CustomDialog {
    private Dialog dialog;
    private String title;
    private String message;
    private Frame parent;
    private Color backgroundColor = new Color(245, 245, 245);
    private Color accentColor = new Color(219, 112, 147);
    private Color textColor = new Color(50, 50, 50);
    private Font titleFont = new Font("Georgia", Font.BOLD, 16);
    private Font messageFont = new Font("Verdana", Font.PLAIN, 14);
    private Font buttonFont = new Font("Verdana", Font.BOLD, 14);
    
    /**
     * Constructor para diálogos personalizados
     * @param parent Frame padre
     * @param title Título del diálogo
     * @param message Mensaje del diálogo
     */
    public CustomDialog(Frame parent, String title, String message) {
        this.parent = parent;
        this.title = title;
        this.message = message;
        
        initializeDialog();
    }
    
    /**
     * Inicializa el diálogo
     */
    private void initializeDialog() {
        dialog = new Dialog(parent, title, true);
        dialog.setSize(400, 200);
        dialog.setLayout(new BorderLayout());
        dialog.setBackground(backgroundColor);
        
        // Centrar diálogo
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension parentSize = parent.getSize();
        Point parentLocation = parent.getLocation();
        int x = parentLocation.x + (parentSize.width - 400) / 2;
        int y = parentLocation.y + (parentSize.height - 200) / 2;
        dialog.setLocation(x, y);
        
        // Panel de título
        Panel titlePanel = new Panel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Dibujar fondo del título
                g2d.setColor(accentColor);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                
                // Dibujar título
                g2d.setColor(Color.WHITE);
                g2d.setFont(titleFont);
                FontMetrics fm = g2d.getFontMetrics();
                g2d.drawString(title, 10, getHeight()/2 + fm.getAscent()/2 - 2);
            }
        };
        titlePanel.setPreferredSize(new Dimension(400, 40));
        
        // Panel de mensaje
        Panel messagePanel = new Panel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                g2d.setColor(textColor);
                g2d.setFont(messageFont);
                
                // Dividir mensaje en líneas si es necesario
                FontMetrics fm = g2d.getFontMetrics();
                int lineHeight = fm.getHeight();
                int x = 20;
                int y = 30;
                
                // Simple word wrap
                String[] words = message.split(" ");
                String line = "";
                
                for (String word : words) {
                    String testLine = line.isEmpty() ? word : line + " " + word;
                    if (fm.stringWidth(testLine) < getWidth() - 40) {
                        line = testLine;
                    } else {
                        g2d.drawString(line, x, y);
                        y += lineHeight;
                        line = word;
                    }
                }
                
                // Dibujar última línea
                if (!line.isEmpty()) {
                    g2d.drawString(line, x, y);
                }
            }
        };
        
        // Panel de botones
        Panel buttonPanel = new Panel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(backgroundColor);
        
        CustomButton acceptButton = new CustomButton("Aceptar", accentColor);
        acceptButton.addActionListener(e -> dialog.dispose());
        buttonPanel.add(acceptButton);
        
        // Agregar componentes al diálogo
        dialog.add(titlePanel, BorderLayout.NORTH);
        dialog.add(messagePanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        
        // Manejar cierre de ventana
        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dialog.dispose();
            }
        });
    }
    
    /**
     * Muestra el diálogo
     */
    public void show() {
        dialog.setVisible(true);
    }
}