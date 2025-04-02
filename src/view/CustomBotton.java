
package view;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


class CustomButton extends Canvas {
    private String text;
    private Color normalColor;
    private Color hoverColor;
    private Color pressedColor;
    private Color textColor;
    private Font buttonFont;
    private boolean isHover = false;
    private boolean isPressed = false;
    private ActionListener actionListener;
    
    /**
     * Constructor para botones personalizados
     * @param text Texto del botón
     * @param color Color base del botón
     */
    public CustomButton(String text, Color color) {
        this.text = text;
        this.normalColor = color;
        this.hoverColor = new Color(
            Math.min(color.getRed() + 20, 255),
            Math.min(color.getGreen() + 20, 255),
            Math.min(color.getBlue() + 20, 255)
        );
        this.pressedColor = new Color(
            Math.max(color.getRed() - 20, 0),
            Math.max(color.getGreen() - 20, 0),
            Math.max(color.getBlue() - 20, 0)
        );
        this.textColor = Color.WHITE;
        this.buttonFont = new Font("Verdana", Font.BOLD, 14);
        
        setPreferredSize(new Dimension(120, 40));
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                isHover = true;
                repaint();
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                isHover = false;
                isPressed = false;
                repaint();
            }
            
            @Override
            public void mousePressed(MouseEvent e) {
                isPressed = true;
                repaint();
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                if (isHover && isPressed && actionListener != null) {
                    actionListener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "click"));
                }
                isPressed = false;
                repaint();
            }
        });
    }
    
    /**
     * Agrega un ActionListener al botón
     * @param listener Listener a agregar
     */
    public void addActionListener(ActionListener listener) {
        this.actionListener = listener;
    }
    
    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Determinar el color del botón basado en estado
        Color currentColor;
        if (isPressed) {
            currentColor = pressedColor;
        } else if (isHover) {
            currentColor = hoverColor;
        } else {
            currentColor = normalColor;
        }
        
        // Dibujar fondo del botón
        g2d.setColor(currentColor);
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
        
        // Dibujar borde sutil
        g2d.setColor(new Color(0, 0, 0, 50));
        g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
        
        // Dibujar texto
        g2d.setFont(buttonFont);
        g2d.setColor(textColor);
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getHeight();
        int x = (getWidth() - textWidth) / 2;
        int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
        
        g2d.drawString(text, x, y);
    }
    
    /**
     * Cambia el texto del botón
     * @param text Nuevo texto
     */
    public void setText(String text) {
        this.text = text;
        repaint();
    }
}