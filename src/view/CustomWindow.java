

package view;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Clase base para crear ventanas personalizadas sin usar JFrame
 * para el sistema de gestión del salón de belleza
 */
public class CustomWindow {
    private Frame mainFrame;
    private Panel headerPanel, contentPanel, footerPanel;
    private Color primaryColor = new Color(245, 245, 245);      // Gris muy claro
    private Color secondaryColor = new Color(255, 192, 203);    // Rosa claro (sutil)
    private Color accentColor = new Color(219, 112, 147);       // Rosa más intenso
    private Color textColor = new Color(50, 50, 50);            // Gris oscuro
    private Color textLightColor = new Color(245, 245, 245);    // Texto claro
    
    private Font titleFont = new Font("Georgia", Font.BOLD, 22);
    private Font subtitleFont = new Font("Georgia", Font.BOLD, 16);
    private Font contentFont = new Font("Verdana", Font.PLAIN, 14);
    private Font buttonFont = new Font("Verdana", Font.BOLD, 14);
    
    private String windowTitle;
    private int windowWidth, windowHeight;
    private BufferedImage logoImage;
    
    /**
     * Constructor que inicializa la ventana personalizada
     * @param title Título de la ventana
     * @param width Ancho de la ventana
     * @param height Alto de la ventana
     */
    public CustomWindow(String title, int width, int height) {
        this.windowTitle = title;
        this.windowWidth = width;
        this.windowHeight = height;
        
        initializeFrame();
        initializeComponents();
        setupLayout();
        
        // Cargar el logo si existe
        try {
            logoImage = ImageIO.read(new File("src/resources/salon_logo.png"));
        } catch (IOException e) {
            System.out.println("No se pudo cargar el logo: " + e.getMessage());
            // Continuamos sin el logo
        }
    }
    
    /**
     * Inicializa el marco principal
     */
    private void initializeFrame() {
        mainFrame = new Frame(windowTitle);
        mainFrame.setSize(windowWidth, windowHeight);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setBackground(primaryColor);
        
        // Centrar en pantalla
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - windowWidth) / 2;
        int y = (screenSize.height - windowHeight) / 2;
        mainFrame.setLocation(x, y);
        
        // Manejar cierre de ventana
        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                mainFrame.dispose();
            }
        });
    }
    
    /**
     * Inicializa los componentes principales de la ventana
     */
    private void initializeComponents() {
        // Panel de encabezado
        headerPanel = new Panel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Dibujar fondo del header
                g2d.setColor(accentColor);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                
                // Dibujar título
                g2d.setColor(textLightColor);
                g2d.setFont(titleFont);
                FontMetrics fm = g2d.getFontMetrics();
                
                // Si hay logo, ajustamos la posición del texto
                int xOffset = 0;
                if (logoImage != null) {
                    int logoSize = getHeight() - 10;
                    g2d.drawImage(logoImage, 10, 5, logoSize, logoSize, null);
                    xOffset = logoSize + 20;
                }
                
                g2d.drawString(windowTitle, xOffset + 10, getHeight()/2 + fm.getAscent()/2 - 2);
                
                // Dibujar botón de cerrar
                g2d.setColor(new Color(255, 255, 255, 180));
                int btnX = getWidth() - 30;
                int btnY = 10;
                int btnSize = getHeight() - 20;
                g2d.fillOval(btnX, btnY, btnSize, btnSize);
                
                g2d.setColor(accentColor.darker());
                g2d.setStroke(new BasicStroke(2));
                g2d.drawLine(btnX + 7, btnY + 7, btnX + btnSize - 7, btnY + btnSize - 7);
                g2d.drawLine(btnX + btnSize - 7, btnY + 7, btnX + 7, btnY + btnSize - 7);
            }
            
            @Override
            public boolean contains(int x, int y) {
                // Detectar si se hizo clic en el botón de cerrar
                int btnX = getWidth() - 30;
                int btnY = 10;
                int btnSize = getHeight() - 20;
                
                return x >= btnX && x <= btnX + btnSize && y >= btnY && y <= btnY + btnSize;
            }
        };
        
        headerPanel.setPreferredSize(new Dimension(windowWidth, 60));
        headerPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (headerPanel.contains(e.getX(), e.getY())) {
                    mainFrame.dispose();
                }
            }
            
            // Para arrastrar la ventana
            private int initialX, initialY;
            
            @Override
            public void mousePressed(MouseEvent e) {
                initialX = e.getX();
                initialY = e.getY();
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                initialX = 0;
                initialY = 0;
            }
        });
        
        headerPanel.addMouseMotionListener(new MouseMotionAdapter() {
            private int initialX, initialY;
            
            @Override
            public void mouseDragged(MouseEvent e) {
                // Solo permitimos arrastrar si el clic no fue en el botón de cerrar
                if (!headerPanel.contains(e.getX(), e.getY())) {
                    int newX = mainFrame.getLocation().x + (e.getX() - initialX);
                    int newY = mainFrame.getLocation().y + (e.getY() - initialY);
                    mainFrame.setLocation(newX, newY);
                }
            }
            
            @Override
            public void mouseMoved(MouseEvent e) {
                initialX = e.getX();
                initialY = e.getY();
            }
        });
        
        // Panel de contenido principal
        contentPanel = new Panel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBackground(primaryColor);
        
        // Panel de pie de página
        footerPanel = new Panel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Dibujar fondo del footer
                g2d.setColor(secondaryColor);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                
                // Dibujar texto de copyright
                g2d.setColor(textColor);
                g2d.setFont(new Font("Verdana", Font.PLAIN, 12));
                String copyright = "© 2025 Salón de Belleza - Todos los derechos reservados";
                FontMetrics fm = g2d.getFontMetrics();
                int textWidth = fm.stringWidth(copyright);
                g2d.drawString(copyright, (getWidth() - textWidth) / 2, getHeight()/2 + fm.getAscent()/2 - 2);
            }
        };
        footerPanel.setPreferredSize(new Dimension(windowWidth, 40));
    }
    
    /**
     * Configura el diseño de la ventana
     */
    private void setupLayout() {
        mainFrame.add(headerPanel, BorderLayout.NORTH);
        mainFrame.add(contentPanel, BorderLayout.CENTER);
        mainFrame.add(footerPanel, BorderLayout.SOUTH);
    }
    
    /**
     * Agrega un componente al panel de contenido
     * @param component Componente a agregar
     * @param position Posición en el BorderLayout
     */
    public void addToContent(Component component, String position) {
        contentPanel.add(component, position);
    }
    
    /**
     * Muestra la ventana
     */
    public void show() {
        mainFrame.setVisible(true);
    }
    
    /**
     * Cierra la ventana
     */
    public void close() {
        mainFrame.dispose();
    }
    
    /**
     * Cambia el título de la ventana
     * @param newTitle Nuevo título
     */
    public void setWindowTitle(String newTitle) {
        this.windowTitle = newTitle;
        mainFrame.setTitle(newTitle);
        headerPanel.repaint();
    }
    
    /**
     * Obtiene el ancho de la ventana
     * @return Ancho de ventana
     */
    public int getWindowWidth() {
        return windowWidth;
    }
    
    /**
     * Obtiene el alto de la ventana
     * @return Alto de ventana
     */
    public int getWindowHeight() {
        return windowHeight;
    }
    
    /**
     * Obtiene el objeto Frame subyacente
     * @return Frame
     */
    public Frame getFrame() {
        return mainFrame;
    }
    
    /**
     * Muestra un mensaje de diálogo
     * @param message Mensaje a mostrar
     * @param title Título del diálogo
     */
    public void showMessage(String message, String title) {
        CustomDialog dialog = new CustomDialog(mainFrame, title, message);
        dialog.show();
    }
    
    /**
     * Obtiene el color primario
     * @return Color primario
     */
    public Color getPrimaryColor() {
        return primaryColor;
    }
    
    /**
     * Obtiene el color secundario
     * @return Color secundario
     */
    public Color getSecondaryColor() {
        return secondaryColor;
    }
    
    /**
     * Obtiene el color de acento
     * @return Color acento
     */
    public Color getAccentColor() {
        return accentColor;
    }
    
    /**
     * Obtiene el color del texto
     * @return Color texto
     */
    public Color getTextColor() {
        return textColor;
    }
    
    /**
     * Obtiene la fuente para títulos
     * @return Fuente para títulos
     */
    public Font getTitleFont() {
        return titleFont;
    }
    
    /**
     * Obtiene la fuente para subtítulos
     * @return Fuente para subtítulos
     */
    public Font getSubtitleFont() {
        return subtitleFont;
    }
    
    /**
     * Obtiene la fuente para contenido
     * @return Fuente para contenido
     */
    public Font getContentFont() {
        return contentFont;
    }
    
    /**
     * Obtiene la fuente para botones
     * @return Fuente para botones
     */
    public Font getButtonFont() {
        return buttonFont;
    }
}
