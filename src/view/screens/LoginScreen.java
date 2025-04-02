
package view.screens;

import java.awt.*;
import java.awt.event.*;
import view.components.*;

public class LoginScreen {
    private CustomFrame frame;
    private CustomTextField usernameField;
    private CustomTextField passwordField;
    private CustomButton loginButton;
    
    public LoginScreen() {
        // Crear marco personalizado
        frame = new CustomFrame("Salón de Belleza - Iniciar Sesión", 400, 450);
        
        // Panel principal
        Panel mainPanel = new Panel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(new Color(245, 245, 220)); // Beige claro
        
        // Imagen de logo (usar Canvas para dibujar)
        Canvas logoCanvas = new Canvas() {
            @Override
            public void paint(Graphics g) {
                // Aquí podrías cargar y dibujar una imagen de logo
                // Para este ejemplo, dibujaremos un simple círculo dorado
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                g2d.setColor(new Color(212, 175, 55)); // Dorado
                g2d.fillOval(25, 10, 150, 150);
                
                g2d.setColor(Color.WHITE);
                g2d.setFont(new Font("Script MT Bold", Font.BOLD, 28));
                g2d.drawString("Beauty", 65, 85);
                g2d.drawString("Salon", 70, 115);
            }
        };
        logoCanvas.setBounds(100, 20, 200, 170);
        mainPanel.add(logoCanvas);
        
        // Título
        Label titleLabel = new Label("Iniciar Sesión", Label.CENTER);
        titleLabel.setFont(new Font("Georgia", Font.BOLD, 20));
        titleLabel.setForeground(new Color(50, 50, 50));
        titleLabel.setBounds(0, 200, 400, 30);
        mainPanel.add(titleLabel);
        
        // Campo de usuario
        usernameField = new CustomTextField("Correo o nombre de usuario");
        usernameField.setBounds(100, 250, 200, 35);
        mainPanel.add(usernameField);
        
        // Campo de contraseña
        passwordField = new CustomTextField("Contraseña");
        passwordField.setPasswordField(true);
        passwordField.setBounds(100, 300, 200, 35);
        mainPanel.add(passwordField);
        
        // Botón de inicio de sesión
        loginButton = new CustomButton("Iniciar Sesión");
        loginButton.setBounds(140, 350, 120, 40);
        loginButton.setActionListener(e -> attemptLogin());
        mainPanel.add(loginButton);
        
        frame.addComponent(mainPanel, BorderLayout.CENTER);
    }
    
    private void attemptLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        
        // Aquí iría la lógica de validación de login
        System.out.println("Intento de inicio de sesión: " + username);
        
        // Por ahora, simplemente mostramos un mensaje
        if (username.equals("admin") && password.equals("admin")) {
            showMessage("¡Inicio de sesión exitoso!");
            // Abrir dashboard
            frame.setVisible(false);
            DashboardScreen dashboard = new DashboardScreen();
            dashboard.show();
        } else {
            showMessage("Credenciales incorrectas");
        }
    }
    
    private void showMessage(String message) {
        Dialog dialog = new Dialog(frame.getFrame(), "Mensaje", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(300, 150);
        dialog.setBackground(new Color(245, 245, 220)); // Beige claro
        
        Label messageLabel = new Label(message, Label.CENTER);
        messageLabel.setFont(new Font("Verdana", Font.PLAIN, 14));
        dialog.add(messageLabel, BorderLayout.CENTER);
        
        Button okButton = new Button("Aceptar");
        okButton.addActionListener(e -> dialog.dispose());
        Panel buttonPanel = new Panel();
        buttonPanel.add(okButton);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        
        // Centrar diálogo
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = screenSize.width / 2 - dialog.getWidth() / 2;
        int y = screenSize.height / 2 - dialog.getHeight() / 2;
        dialog.setLocation(x, y);
        
        dialog.setVisible(true);
    }
    
    public void show() {
        frame.setVisible(true);
    }
    
    // Para pruebas
    public static void main(String[] args) {
        LoginScreen login = new LoginScreen();
        login.show();
    }
}
