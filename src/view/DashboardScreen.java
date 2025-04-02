
package view;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


class DashboardScreen {
    private CustomWindow window;
    private Panel navigationPanel;
    private Panel contentPanel;
    private String currentSection = "Inicio";
    
    /**
     * Constructor de la pantalla principal
     */
    public DashboardScreen() {
        window = new CustomWindow("Salón de Belleza - Panel Principal", 1400, 700);
        
        // Panel principal con diseño de BorderLayout
        Panel mainPanel = new Panel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(window.getPrimaryColor());
        
        // Panel de navegación (izquierda)
        navigationPanel = new Panel();
        navigationPanel.setLayout(new GridLayout(8, 1, 0, 10));
        navigationPanel.setBackground(window.getSecondaryColor());
        
        // Botones de navegación
        String[] menuItems = {
            "Inicio", "Clientes", "Citas", "Servicios", 
            "Productos", "Inventario", "Reportes", "Cerrar Sesión"
        };
        
        for (String item : menuItems) {
            CustomButton navButton = new CustomButton(item, 
                item.equals(currentSection) ? window.getAccentColor() : window.getSecondaryColor().darker());
            
            navButton.addActionListener(e -> {
                currentSection = item;
                updateContentPanel();
                updateNavigationButtons();
            });
            
            navigationPanel.add(navButton);
        }
        
        // Panel de contenido (centro)
        contentPanel = new Panel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBackground(window.getPrimaryColor());
        
        // Configuración inicial del contenido
        updateContentPanel();
        
        // Agregar paneles al panel principal
        mainPanel.add(navigationPanel, BorderLayout.WEST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        window.addToContent(mainPanel, BorderLayout.CENTER);
    }
    
    /**
     * Actualiza los botones de navegación para destacar la sección actual
     */
    private void updateNavigationButtons() {
        Component[] components = navigationPanel.getComponents();
        for (int i = 0; i < components.length; i++) {
            if (components[i] instanceof CustomButton) {
                CustomButton button = (CustomButton) components[i];
                if (button.getText().equals(currentSection)) {
                    button.setBackground(window.getAccentColor());
                } else {
                    button.setBackground(window.getSecondaryColor().darker());
                }
                button.repaint();
            }
        }
    }
    
    /**
     * Actualiza el panel de contenido según la sección seleccionada
     */
    private void updateContentPanel() {
        contentPanel.removeAll();
        
        // Panel para el contenido específico de cada sección
        Panel sectionPanel = new Panel();
        sectionPanel.setLayout(null);
        sectionPanel.setBackground(window.getPrimaryColor());
        
        // Título de la sección
        Label titleLabel = new Label(currentSection, Label.CENTER);
        titleLabel.setFont(window.getTitleFont());
        titleLabel.setBounds(0, 20, 800, 40);
        sectionPanel.add(titleLabel);
        
        // Contenido específico para cada sección
        switch (currentSection) {
            case "Inicio":
                setupHomeSection(sectionPanel);
                break;
            case "Clientes":
                setupClientsSection(sectionPanel);
                break;
            case "Citas":
                setupAppointmentsSection(sectionPanel);
                break;
            case "Servicios":
                setupServicesSection(sectionPanel);
                break;
            case "Productos":
                setupProductsSection(sectionPanel);
                break;
            case "Inventario":
                setupInventorySection(sectionPanel);
                break;
            case "Reportes":
                setupReportsSection(sectionPanel);
                break;
            case "Cerrar Sesión":
                window.close();
                LoginScreen login = new LoginScreen();
                login.show();
                return;
        }
        
        contentPanel.add(sectionPanel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    
    /**
     * Configura la sección de inicio
     * @param panel Panel donde se agregarán los componentes
     */
    private void setupHomeSection(Panel panel) {
        // Resumen de citas para hoy
        Label appointmentsLabel = new Label("Citas para hoy: 12");
        appointmentsLabel.setFont(window.getSubtitleFont());
        appointmentsLabel.setBounds(50, 100, 300, 30);
        panel.add(appointmentsLabel);
        
        // Clientes nuevos este mes
        Label clientsLabel = new Label("Clientes nuevos este mes: 24");
        clientsLabel.setFont(window.getSubtitleFont());
        clientsLabel.setBounds(50, 150, 300, 30);
        panel.add(clientsLabel);
        
        // Servicios más populares
        Label servicesLabel = new Label("Servicios más populares: Corte y Tinte");
        servicesLabel.setFont(window.getSubtitleFont());
        servicesLabel.setBounds(50, 200, 400, 30);
        panel.add(servicesLabel);
        
        // Productos con bajo stock
        Label productsLabel = new Label("Productos con stock bajo: 3");
        productsLabel.setFont(window.getSubtitleFont());
        productsLabel.setBounds(50, 250, 300, 30);
        panel.add(productsLabel);
        
        // Botón de acceso rápido a citas
        CustomButton appointmentsButton = new CustomButton("Gestionar Citas", window.getAccentColor());
        appointmentsButton.setBounds(50, 350, 150, 40);
        appointmentsButton.addActionListener(e -> {
            currentSection = "Citas";
            updateContentPanel();
            updateNavigationButtons();
        });
        panel.add(appointmentsButton);
        
        // Botón de acceso rápido a servicios
        CustomButton servicesButton = new CustomButton("Ver Servicios", window.getAccentColor());
        servicesButton.setBounds(250, 350, 150, 40);
        servicesButton.addActionListener(e -> {
            currentSection = "Servicios";
            updateContentPanel();
            updateNavigationButtons();
        });
        panel.add(servicesButton);
    }
    
    /**
     * Configura la sección de clientes
     * @param panel Panel donde se agregarán los componentes
     */
    private void setupClientsSection(Panel panel) {
        // Título subsección
        Label subtitleLabel = new Label("Administración de Clientes", Label.CENTER);
        subtitleLabel.setFont(window.getSubtitleFont());
        subtitleLabel.setBounds(0, 70, 800, 30);
        panel.add(subtitleLabel);
        
        // Tabla de clientes (ejemplo)
        String[] columnNames = {"ID", "Nombre", "Apellido", "Correo", "Teléfono", "Tipo"};
        Object[][] data = {
            {1, "Juan", "Pérez", "juan@ejemplo.com", "3031234567", "Cliente"},
            {2, "María", "López", "maria@ejemplo.com", "3041234567", "Cliente"},
            {3, "Carlos", "González", "carlos@ejemplo.com", "3051234567", "Cliente"},
            {4, "Ana", "Martínez", "ana@ejemplo.com", "3061234567", "Cliente"},
            {5, "Pedro", "Sánchez", "pedro@ejemplo.com", "3071234567", "Cliente"}
        };
        
        CustomTable clientsTable = new CustomTable(columnNames, data);
        clientsTable.setBounds(50, 110, 1200, 300);
        panel.add(clientsTable);
        
        // Botones de acción
        CustomButton addButton = new CustomButton("Agregar", window.getAccentColor());
        addButton.setBounds(50, 420, 120, 40);
        panel.add(addButton);
        
        CustomButton editButton = new CustomButton("Editar", window.getAccentColor());
        editButton.setBounds(190, 420, 120, 40);
        panel.add(editButton);
        
        CustomButton deleteButton = new CustomButton("Eliminar", window.getAccentColor());
        deleteButton.setBounds(330, 420, 120, 40);
        panel.add(deleteButton);
    }
    
    /**
     * Configura la sección de citas
     * @param panel Panel donde se agregarán los componentes
     */
    private void setupAppointmentsSection(Panel panel) {
        // Implementación similar a la sección de clientes pero para citas
        Label subtitleLabel = new Label("Gestión de Citas", Label.CENTER);
        subtitleLabel.setFont(window.getSubtitleFont());
        subtitleLabel.setBounds(0, 70, 800, 30);
        panel.add(subtitleLabel);
        
        // Tabla de citas (ejemplo)
        String[] columnNames = {"ID", "Fecha", "Hora", "Cliente", "Servicio", "Estilista", "Estado"};
        Object[][] data = {
            {1, "01/04/2025", "10:00", "Juan Pérez", "Corte Clásico", "Carlos Rodríguez", "Programada"},
            {2, "01/04/2025", "11:30", "María López", "Tinte y Mechas", "Ana Gómez", "Programada"},
            {3, "01/04/2025", "14:00", "Pedro Sánchez", "Barbería", "Carlos Rodríguez", "Programada"},
            {4, "02/04/2025", "09:00", "Ana Martínez", "Manicure", "Ana Gómez", "Programada"},
            {5, "02/04/2025", "16:30", "Carlos González", "Corte Desvanecido", "Carlos Rodríguez", "Programada"}
        };
        
        CustomTable appointmentsTable = new CustomTable(columnNames, data);
        appointmentsTable.setBounds(50, 110, 1200, 300);
        panel.add(appointmentsTable);
        
        // Botones de acción
        CustomButton addButton = new CustomButton("Nueva Cita", window.getAccentColor());
        addButton.setBounds(50, 420, 120, 40);
        panel.add(addButton);
        
        CustomButton editButton = new CustomButton("Modificar", window.getAccentColor());
        editButton.setBounds(190, 420, 120, 40);
        panel.add(editButton);
        
        CustomButton cancelButton = new CustomButton("Cancelar", window.getAccentColor());
        cancelButton.setBounds(330, 420, 120, 40);
        panel.add(cancelButton);
        
        CustomButton completeButton = new CustomButton("Completar", window.getAccentColor());
        completeButton.setBounds(470, 420, 120, 40);
        panel.add(completeButton);
    }
    
    /**
     * Configura la sección de servicios
     * @param panel Panel donde se agregarán los componentes
     */
    private void setupServicesSection(Panel panel) {
        // Implementación para la sección de servicios
        Label subtitleLabel = new Label("Catálogo de Servicios", Label.CENTER);
        subtitleLabel.setFont(window.getSubtitleFont());
        subtitleLabel.setBounds(0, 70, 800, 30);
        panel.add(subtitleLabel);
        
        // Tabla de servicios (ejemplo)
        String[] columnNames = {"ID", "Nombre", "Descripción", "Precio", "Duración (min)", "Categoría"};
        Object[][] data = {
            {1, "Corte Clásico", "Corte tradicional para caballeros", "$25,000", 30, "Cortes de cabello"},
            {2, "Corte Desvanecido", "Corte con degradado", "$30,000", 45, "Cortes de cabello"},
            {3, "Corte en Capas", "Corte en capas para damas", "$45,000", 60, "Cortes de cabello"},
            {4, "Hidratación Profunda", "Tratamiento intensivo de hidratación", "$70,000", 60, "Tratamientos capilares"},
            {5, "Tinte y Mechas", "Aplicación de color y mechas", "$150,000", 150, "Tratamientos capilares"}
        };
        
        CustomTable servicesTable = new CustomTable(columnNames, data);
        servicesTable.setBounds(50, 110, 1200, 300);
        panel.add(servicesTable);
        
        // Botones de acción
        CustomButton addButton = new CustomButton("Agregar", window.getAccentColor());
        addButton.setBounds(50, 420, 120, 40);
        panel.add(addButton);
        
        CustomButton editButton = new CustomButton("Editar", window.getAccentColor());
        editButton.setBounds(190, 420, 120, 40);
        panel.add(editButton);
        
        CustomButton deleteButton = new CustomButton("Eliminar", window.getAccentColor());
        deleteButton.setBounds(330, 420, 120, 40);
        panel.add(deleteButton);
    }
    
    /**
     * Configura la sección de productos
     * @param panel Panel donde se agregarán los componentes
     */
    private void setupProductsSection(Panel panel) {
        // Implementación para la sección de productos
        Label subtitleLabel = new Label("Catálogo de Productos", Label.CENTER);
        subtitleLabel.setFont(window.getSubtitleFont());
        subtitleLabel.setBounds(0, 70, 800, 30);
        panel.add(subtitleLabel);
        
        // Tabla de productos (ejemplo)
        String[] columnNames = {"ID", "Nombre", "Descripción", "Precio", "Stock", "Categoría"};
        Object[][] data = {
            {1, "Shampoo Hidratante", "Shampoo para cabello seco", "$45,000", 20, "Shampoos y acondicionadores"},
            {2, "Acondicionador Reparador", "Acondicionador para cabello dañado", "$48,000", 18, "Shampoos y acondicionadores"},
            {3, "Aceite de Argán", "Aceite nutritivo para todo tipo de cabello", "$60,000", 15, "Aceites y tratamientos"},
            {4, "Mascarilla Capilar", "Tratamiento intensivo semanal", "$55,000", 12, "Aceites y tratamientos"},
            {5, "Secador Profesional", "Secador de alta potencia", "$200,000", 6, "Herramientas"}
        };
        
        CustomTable productsTable = new CustomTable(columnNames, data);
        productsTable.setBounds(50, 110, 1200, 300);
        panel.add(productsTable);
        
        // Botones de acción
        CustomButton addButton = new CustomButton("Agregar", window.getAccentColor());
        addButton.setBounds(50, 420, 120, 40);
        panel.add(addButton);
        
        CustomButton editButton = new CustomButton("Editar", window.getAccentColor());
        editButton.setBounds(190, 420, 120, 40);
        panel.add(editButton);
        
        CustomButton deleteButton = new CustomButton("Eliminar", window.getAccentColor());
        deleteButton.setBounds(330, 420, 120, 40);
        panel.add(deleteButton);
        
        CustomButton sellButton = new CustomButton("Vender", window.getAccentColor());
        sellButton.setBounds(470, 420, 120, 40);
        panel.add(sellButton);
    }
    
    /**
     * Configura la sección de inventario
     * @param panel Panel donde se agregarán los componentes
     */
    private void setupInventorySection(Panel panel) {
        // Implementación para la sección de inventario
        Label subtitleLabel = new Label("Control de Inventario", Label.CENTER);
        subtitleLabel.setFont(window.getSubtitleFont());
        subtitleLabel.setBounds(0, 70, 800, 30);
        panel.add(subtitleLabel);
        
        // Tabla de inventario (ejemplo)
        String[] columnNames = {"ID", "Producto", "Stock Actual", "Stock Mínimo", "Estado", "Categoría"};
        Object[][] data = {
            {1, "Shampoo Hidratante", 20, 5, "OK", "Shampoos y acondicionadores"},
            {2, "Acondicionador Reparador", 18, 5, "OK", "Shampoos y acondicionadores"},
            {3, "Aceite de Argán", 15, 3, "OK", "Aceites y tratamientos"},
            {4, "Mascarilla Capilar", 2, 3, "BAJO", "Aceites y tratamientos"},
            {5, "Secador Profesional", 1, 2, "BAJO", "Herramientas"}
        };
        
        CustomTable inventoryTable = new CustomTable(columnNames, data);
        inventoryTable.setBounds(50, 110, 1200, 300);
        panel.add(inventoryTable);
        
        // Botones de acción
        CustomButton restockButton = new CustomButton("Reponer Stock", window.getAccentColor());
        restockButton.setBounds(50, 420, 150, 40);
        panel.add(restockButton);
        
        CustomButton adjustButton = new CustomButton("Ajustar Stock", window.getAccentColor());
        adjustButton.setBounds(220, 420, 150, 40);
        panel.add(adjustButton);
        
        CustomButton reportButton = new CustomButton("Reporte", window.getAccentColor());
        reportButton.setBounds(390, 420, 120, 40);
        panel.add(reportButton);
    }
    
    /**
     * Configura la sección de reportes
     * @param panel Panel donde se agregarán los componentes
     */
    private void setupReportsSection(Panel panel) {
        // Implementación para la sección de reportes
        Label subtitleLabel = new Label("Informes y Reportes", Label.CENTER);
        subtitleLabel.setFont(window.getSubtitleFont());
        subtitleLabel.setBounds(0, 70, 800, 30);
        panel.add(subtitleLabel);
        
        // Botones para diferentes tipos de reportes
        CustomButton salesButton = new CustomButton("Ventas", window.getAccentColor());
        salesButton.setBounds(50, 150, 150, 40);
        panel.add(salesButton);
        
        CustomButton serviceButton = new CustomButton("Servicios", window.getAccentColor());
        serviceButton.setBounds(50, 210, 150, 40);
        panel.add(serviceButton);
        
        CustomButton clientButton = new CustomButton("Clientes", window.getAccentColor());
        clientButton.setBounds(50, 270, 150, 40);
        panel.add(clientButton);
        
        CustomButton inventoryButton = new CustomButton("Inventario", window.getAccentColor());
        inventoryButton.setBounds(50, 330, 150, 40);
        panel.add(inventoryButton);
        
        // Panel para mostrar el reporte seleccionado
        Panel reportPanel = new Panel();
        reportPanel.setBounds(250, 150, 500, 400);
        reportPanel.setBackground(Color.WHITE);
        panel.add(reportPanel);
    }
    
    /**
     * Muestra la pantalla principal
     */
    public void show() {
        window.show();
    }
}