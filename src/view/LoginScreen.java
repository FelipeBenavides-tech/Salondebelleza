
package view;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;



class LoginScreen {
    private CustomWindow window;
    private CustomTextField usernameField;
    private CustomTextField passwordField;
    private CustomButton loginButton;
    private Panel mainPanel;
    
    /**
     * Constructor de la pantalla de login
     */
    public LoginScreen() {
        window = new CustomWindow("Salón de Belleza - Iniciar Sesión", 500, 600);
        
        mainPanel = new Panel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(window.getPrimaryColor());
        
        // Logo (círculo simulado ya que no tenemos la imagen)
        Canvas logoCanvas = new Canvas() {
            @Override
            public void paint(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Círculo exterior
                g2d.setColor(window.getAccentColor());
                g2d.fillOval(0, 0, 150, 150);
                
                // Círculo interior
                g2d.setColor(window.getSecondaryColor());
                g2d.fillOval(20, 20, 110, 110);
                
                // Texto
                g2d.setColor(window.getTextColor());
                g2d.setFont(new Font("Script MT Bold", Font.BOLD, 24));
                FontMetrics fm = g2d.getFontMetrics();
                
                String text1 = "Salon";
                String text2 = "Belleza";
                
                int textWidth1 = fm.stringWidth(text1);
                int textWidth2 = fm.stringWidth(text2);
                
                g2d.drawString(text1, (150 - textWidth1) / 2, 70);
                g2d.drawString(text2, (150 - textWidth2) / 2, 100);
            }
        };
        logoCanvas.setBounds(175, 50, 150, 150);
        mainPanel.add(logoCanvas);
        
        // Título
        Label titleLabel = new Label("Iniciar Sesión", Label.CENTER);
        titleLabel.setFont(window.getTitleFont());
        titleLabel.setBounds(100, 220, 300, 30);
        mainPanel.add(titleLabel);
        
        // Campo de usuario
        usernameField = new CustomTextField("Usuario o correo");
        usernameField.setBounds(150, 270, 200, 35);
        mainPanel.add(usernameField);
        
        // Campo de contraseña
        passwordField = new CustomTextField("Contraseña");
        passwordField.setPassword(true);
        passwordField.setBounds(150, 320, 200, 35);
        mainPanel.add(passwordField);
        
        // Botón de login
        loginButton = new CustomButton("Iniciar Sesión", window.getAccentColor());
        loginButton.setBounds(190, 370, 120, 40);
        loginButton.addActionListener(e -> attemptLogin());
        mainPanel.add(loginButton);
        
        window.addToContent(mainPanel, BorderLayout.CENTER);
    }
    
    /**
     * Intenta iniciar sesión con las credenciales ingresadas
     */
    private void attemptLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        
        // Aquí deberías implementar la lógica de autenticación
        // Por ahora, un simple ejemplo
        if (username.equals("admin") && password.equals("admin")) {
            window.close();
            DashboardScreen dashboard = new DashboardScreen();
            dashboard.show();
        } else {
            window.showMessage("Credenciales incorrectas", "Error de inicio de sesión");
        }
    }
    
    /**
     * Muestra la pantalla de login
     */
    public void show() {
        window.show();
    }
}