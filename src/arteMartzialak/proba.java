package arteMartzialak;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class proba extends JFrame {

    public proba() {
        setTitle("Abrir Archivo");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JButton abrirButton = new JButton("Abrir Archivo");
        abrirButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Crear un JFileChooser
                JFileChooser fileChooser = new JFileChooser();
                
                // Limitar la selección a archivos con extensión .txt
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de Texto", "txt");
                fileChooser.setFileFilter(filter);
                
                // Mostrar el diálogo de selección de archivo
                int result = fileChooser.showOpenDialog(proba.this);
                
                // Si el usuario selecciona un archivo, obtener la ruta
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    String filePath = selectedFile.getAbsolutePath();
                    System.out.println("Archivo seleccionado: " + filePath);
                }
            }
        });

        getContentPane().add(abrirButton);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new proba().setVisible(true);
            }
        });
    }
}
