
package view.components;

import java.awt.*;
import java.awt.event.*;

public class CustomTextField extends Canvas {
    private String text = "";
    private String placeholder;
    private Color textColor;
    private Color placeholderColor;
    private Color backgroundColor;
    private Color borderColor;
    private Font textFont;
    private boolean focused = false;
    private int cursorPosition = 0;
    private boolean passwordField = false;
    
    // Colores predefinidos para el salón de belleza
    private static final Color BEIGE_DARK = new Color(225, 225, 200);
    private static final Color DARK_GRAY = new Color(50, 50, 50);
    private static final Color GOLD = new Color(212, 175, 55);
    private static final Color GRAY_LIGHT = new Color(180, 180, 180);
    
    public CustomTextField(String placeholder) {
        this.placeholder = placeholder;
        this.textColor = DARK_GRAY;
        this.placeholderColor = GRAY_LIGHT;
        this.backgroundColor = Color.WHITE;
        this.borderColor = BEIGE_DARK;
        this.textFont = new Font("Verdana", Font.PLAIN, 14);
        
        setPreferredSize(new Dimension(200, 35));
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                focused = true;
                cursorPosition = text.length();
                repaint();
                requestFocus();
            }
        });
        
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (focused) {
                    char c = e.getKeyChar();
                    
                    if (c == KeyEvent.VK_BACK_SPACE) {
                        if (text.length() > 0 && cursorPosition > 0) {
                            text = text.substring(0, cursorPosition - 1) + text.substring(cursorPosition);
                            cursorPosition--;
                        }
                    } else if (c == KeyEvent.VK_DELETE) {
                        if (text.length() > 0 && cursorPosition < text.length()) {
                            text = text.substring(0, cursorPosition) + text.substring(cursorPosition + 1);
                        }
                    } else if (c >= 32 && c <= 126) { // Caracteres imprimibles
                        text = text.substring(0, cursorPosition) + c + text.substring(cursorPosition);
                        cursorPosition++;
                    }
                    
                    repaint();
                }
            }
            
            @Override
            public void keyPressed(KeyEvent e) {
                if (focused) {
                    int keyCode = e.getKeyCode();
                    
                    if (keyCode == KeyEvent.VK_LEFT && cursorPosition > 0) {
                        cursorPosition--;
                        repaint();
                    } else if (keyCode == KeyEvent.VK_RIGHT && cursorPosition < text.length()) {
                        cursorPosition++;
                        repaint();
                    }
                }
            }
        });
        
        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                focused = true;
                borderColor = GOLD;
                repaint();
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                focused = false;
                borderColor = BEIGE_DARK;
                repaint();
            }
        });
        
        setFocusable(true);
    }
    
    public void setPasswordField(boolean passwordField) {
        this.passwordField = passwordField;
    }
    
    public String getText() {
        return text;
    }
    
    public void setText(String text) {
        this.text = text;
        cursorPosition = text.length();
        repaint();
    }
    
    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int width = getWidth();
        int height = getHeight();
        
        // Dibujar fondo
        g2d.setColor(backgroundColor);
        g2d.fillRoundRect(0, 0, width, height, 8, 8);
        
        // Dibujar borde
        g2d.setColor(borderColor);
        g2d.drawRoundRect(0, 0, width - 1, height - 1, 8, 8);
        
        // Dibujar texto o placeholder
        g2d.setFont(textFont);
        FontMetrics metrics = g2d.getFontMetrics(textFont);
        
        int textY = ((height - metrics.getHeight()) / 2) + metrics.getAscent();
        int textX = 10; // Margen izquierdo
        
        if (text.isEmpty()) {
            g2d.setColor(placeholderColor);
            g2d.drawString(placeholder, textX, textY);
        } else {
            g2d.setColor(textColor);
            
            String displayText;
            if (passwordField) {
                displayText = "•".repeat(text.length());
            } else {
                displayText = text;
            }
            
            g2d.drawString(displayText, textX, textY);
            
            // Dibujar cursor si tiene el foco
            if (focused) {
                String textBeforeCursor = passwordField ? 
                        "•".repeat(cursorPosition) : 
                        text.substring(0, cursorPosition);
                        
                int cursorX = textX + metrics.stringWidth(textBeforeCursor);
                g2d.drawLine(cursorX, textY - metrics.getAscent() + 3, cursorX, textY + 3);
            }
        }
    }
}
