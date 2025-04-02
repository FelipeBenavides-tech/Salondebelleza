
package view.screens;


import java.awt.*;
import view.components.*;

public class DashboardScreen {
    private CustomFrame frame;
    
    public DashboardScreen() {
        frame = new CustomFrame("Salón de Belleza - Panel Principal", 900, 600);
        
        // Panel principal con diseño de cuadrícula
        Panel mainPanel = new Panel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(245, 245, 220)); // Beige claro
        
        // Panel de navegación izquierdo
        Panel navPanel = new Panel();
        navPanel.setLayout(new GridLayout(8, 1, 10, 10));
        navPanel.setBackground(new Color(212, 175, 55)); // Dorado
        navPanel.setPreferredSize(new Dimension(200, 600));
        
        // Agregar botones de navegación
        String[] menuItems = {
            "Inicio", "Citas", "Clientes", "Servicios", 
            "Productos", "Inventario", "Pagos", "Cerrar Sesión"
        };
        
        for (String item : menuItems) {
            Button navButton = new Button(item);
            navButton.setFont(new Font("Verdana", Font.BOLD, 14));
            navButton.setForeground(Color.WHITE);
            navButton.setBackground(new Color(212, 175, 55)); // Dorado
            navPanel.add(navButton);
        }
        
        mainPanel.add(navPanel, BorderLayout.WEST);
        
        // Panel de contenido central
        Panel contentPanel = new Panel();
        contentPanel.setLayout(null);
        contentPanel.setBackground(Color.WHITE);
        
        // Título de bienvenida
        Label welcomeLabel = new Label("Bienvenido al Sistema de Gestión", Label.CENTER);
        welcomeLabel.setFont(new Font("Georgia", Font.BOLD, 24));
        welcomeLabel.setForeground(new Color(50, 50, 50));
        welcomeLabel.setBounds(0, 30, 700, 40);
        contentPanel.add(welcomeLabel);
        
        // Subtítulo
        Label subtitleLabel = new Label("Seleccione una opción del menú para comenzar", Label.CENTER);
        subtitleLabel.setFont(new Font("Verdana", Font.PLAIN, 16));
        subtitleLabel.setForeground(new Color(100, 100, 100));
        subtitleLabel.setBounds(0, 70, 700, 30);
        contentPanel.add(subtitleLabel);
        
        // Estadísticas rápidas (ejemplo)
        String[] stats = {
            "Citas programadas hoy: 12",
            "Clientes nuevos este mes: 24",
            "Servicios más populares: Corte y Tinte",
            "Productos con stock bajo: 3"
        };
        
        int yPos = 150;
        for (String stat : stats) {
            Label statLabel = new Label(stat);
            statLabel.setFont(new Font("Verdana", Font.PLAIN, 14));
            statLabel.setBounds(50, yPos, 400, 25);
            contentPanel.add(statLabel);
            yPos += 40;
        }
        
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        frame.addComponent(mainPanel, BorderLayout.CENTER);
    }
    
    public void show() {
        frame.setVisible(true);
    }
}