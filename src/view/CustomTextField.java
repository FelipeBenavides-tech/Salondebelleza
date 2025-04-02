
package view;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


class CustomTextField extends Canvas {
    private String text = "";
    private String placeholder;
    private int caretPosition = 0;
    private boolean focused = false;
    private Color backgroundColor = Color.WHITE;
    private Color borderColor = new Color(200, 200, 200);
    private Color focusedBorderColor = new Color(219, 112, 147);
    private Color textColor = new Color(50, 50, 50);
    private Color placeholderColor = new Color(180, 180, 180);
    private Font textFont = new Font("Verdana", Font.PLAIN, 14);
    private boolean isPassword = false;
    
    private long lastBlinkTime = 0;
    private boolean showCaret = true;
    
    /**
     * Constructor para campos de texto personalizados
     * @param placeholder Texto de placeholder
     */
    public CustomTextField(String placeholder) {
        this.placeholder = placeholder;
        
        setPreferredSize(new Dimension(200, 35));
        setBackground(backgroundColor);
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                requestFocus();
                focused = true;
                
                // Posicionar el caret en la posición del clic
                FontMetrics fm = getFontMetrics(textFont);
                int clickX = e.getX() - 10; // 10 px de padding
                
                if (clickX <= 0) {
                    caretPosition = 0;
                } else if (clickX >= fm.stringWidth(text)) {
                    caretPosition = text.length();
                } else {
                    // Encontrar la posición más cercana
                    for (int i = 1; i <= text.length(); i++) {
                        int width = fm.stringWidth(text.substring(0, i));
                        if (width >= clickX) {
                            caretPosition = i - 1;
                            break;
                        }
                        caretPosition = i;
                    }
                }
                
                repaint();
            }
        });
        
        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                focused = true;
                showCaret = true;
                lastBlinkTime = System.currentTimeMillis();
                repaint();
                
                // Iniciar el parpadeo del caret
                new Thread(() -> {
                    while (focused) {
                        long currentTime = System.currentTimeMillis();
                        if (currentTime - lastBlinkTime > 500) {
                            showCaret = !showCaret;
                            lastBlinkTime = currentTime;
                            repaint();
                        }
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException ex) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }).start();
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                focused = false;
                repaint();
            }
        });
        
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                
                if (c == KeyEvent.VK_BACK_SPACE) {
                    if (caretPosition > 0) {
                        text = text.substring(0, caretPosition - 1) + text.substring(caretPosition);
                        caretPosition--;
                    }
                } else if (c == KeyEvent.VK_DELETE) {
                    if (caretPosition < text.length()) {
                        text = text.substring(0, caretPosition) + text.substring(caretPosition + 1);
                    }
                } else if (c >= 32 && c < 127) { // Caracteres imprimibles ASCII
                    text = text.substring(0, caretPosition) + c + text.substring(caretPosition);
                    caretPosition++;
                }
                
                repaint();
            }
            
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT && caretPosition > 0) {
                    caretPosition--;
                    showCaret = true;
                    lastBlinkTime = System.currentTimeMillis();
                    repaint();
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && caretPosition < text.length()) {
                    caretPosition++;
                    showCaret = true;
                    lastBlinkTime = System.currentTimeMillis();
                    repaint();
                } else if (e.getKeyCode() == KeyEvent.VK_HOME) {
                    caretPosition = 0;
                    showCaret = true;
                    lastBlinkTime = System.currentTimeMillis();
                    repaint();
                } else if (e.getKeyCode() == KeyEvent.VK_END) {
                    caretPosition = text.length();
                    showCaret = true;
                    lastBlinkTime = System.currentTimeMillis();
                    repaint();
                }
            }
        });
        
        setFocusable(true);
    }
    
    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Dibujar fondo
        g2d.setColor(backgroundColor);
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 5, 5);
        
        // Dibujar borde
        g2d.setColor(focused ? focusedBorderColor : borderColor);
        g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 5, 5);
        
        g2d.setFont(textFont);
        
        // Dibujar texto o placeholder
        if (text.isEmpty()) {
            g2d.setColor(placeholderColor);
            g2d.drawString(placeholder, 10, getHeight()/2 + g2d.getFontMetrics().getAscent()/2 - 2);
        } else {
            g2d.setColor(textColor);
            
            String displayText;
            if (isPassword) {
                displayText = "•".repeat(text.length());
            } else {
                displayText = text;
            }
            
            g2d.drawString(displayText, 10, getHeight()/2 + g2d.getFontMetrics().getAscent()/2 - 2);
            
            // Dibujar caret si tiene el foco
            if (focused && showCaret) {
                FontMetrics fm = g2d.getFontMetrics();
                int caretX;
                
                if (isPassword) {
                    caretX = 10 + fm.stringWidth("•".repeat(caretPosition));
                } else {
                    caretX = 10 + fm.stringWidth(text.substring(0, caretPosition));
                }
                
                g2d.drawLine(caretX, 8, caretX, getHeight() - 8);
            }
        }
    }
    
    /**
     * Establece si es un campo de contraseña
     * @param isPassword true si es campo de contraseña
     */
    public void setPassword(boolean isPassword) {
        this.isPassword = isPassword;
        repaint();
    }
    
    /**
     * Obtiene el texto del campo
     * @return Texto actual
     */
    public String getText() {
        return text;
    }
    
    /**
     * Establece el texto del campo
     * @param text Nuevo texto
     */
    public void setText(String text) {
        this.text = text != null ? text : "";
        caretPosition = this.text.length();
        repaint();
    }
}
