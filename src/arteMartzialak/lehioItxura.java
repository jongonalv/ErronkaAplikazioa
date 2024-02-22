package arteMartzialak;

import javax.imageio.stream.ImageOutputStreamImpl;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.*;
import java.awt.peer.ButtonPeer;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Enumeration;

public class lehioItxura {

	private static JFrame frame = new JFrame("Tolosako Arte Martzial Taldeko Fitxategi Kudeaketa");

	// LEHIO NAGUSIAK erabiliko dituen elementu ezberdinak atributoetan ezarrita
	private static JMenuBar 			menu 				= new JMenuBar();
	private static JMenu 				artxMenu 			= new JMenu("Artxiboa");
	private static JMenu 				laguntzaMenu 		= new JMenu("Laguntza");
	private static JMenuItem 			berriaItem 			= new JMenuItem("Berria");
	private static JMenuItem 			irekiItem 			= new JMenuItem("Ireki");
	private static JMenuItem 			gordeItem 			= new JMenuItem("Gorde");
	private static JMenuItem 			gordehonelaItem 	= new JMenuItem("Gorde honela...");
	private static JMenuItem 			ateraItem 			= new JMenuItem("Irten");
	private static JLabel 				tituloaLabel 		= new JLabel("Fitxategi kudeaketa aplikazioa");
	private static JLabel 				deskLabel 			= new JLabel("Sakatu goran 'Artxiboa' aukera ezberdinak ikusteko");
	private static JPanel 				panel				= new JPanel(new GridBagLayout());
	private static GridBagConstraints 	gbcTituloa 			= new GridBagConstraints();
	private static GridBagConstraints 	gbcDesk 			= new GridBagConstraints();

	// artxiboBerria aukerako elementuak
	private static JPanel 				panel1 				= new JPanel();
	private static JPanel 				panelBorroka 		= new JPanel();
	private static JPanel 				panelLehiaketa 		= new JPanel();
	private static JPanel 				panelTaldea 		= new JPanel();
	private static JPanel 				panelErabiltzailea 	= new JPanel();
	private static JPanel 				mainPanel			= new JPanel(new BorderLayout());
	private static JPanel 				panelBotoiak 		= new JPanel();
	private static JButton 				btnSortu 			= new JButton("Sortu fitxeroa");
	private static JButton 				btnGehituLerroa		= new JButton("Gehitu Lerroa fitxategiari");
	private static JButton 				btnGarbitu			= new JButton("Garbitu");
	private static JButton 				btnEditatu			= new JButton("Editatu (kontuz formatoarekin)");
	private static JTextArea 			txtOutput 			= new JTextArea();
	private static CardLayout 			cardLayout 			= new CardLayout();
	private static JPanel 				centerPanel 		= new JPanel(cardLayout);
	private static JLabel 				label1 				= new JLabel("Aukeratu fitxategi mota:");
	private static JComboBox<String> 	fitxCombo 			= new JComboBox<>(
			new String[] { "Borrokalaria", "Lehiaketa", "Taldea", "Erabiltzailea" });

	// Borrokalariaren formularioaren elementuak
	private static JLabel 				formIzena 			= new JLabel("Borrokalariaren izena:");
	private static JTextField 			txtIzena 			= new JTextField();
	private static JLabel 				formPisua 			= new JLabel("Borrokalariaren pisua (kg):");
	private static JTextField 			txtPisua 			= new JTextField();
	private static JLabel 				formData 			= new JLabel("Jaiotze data (UUUU-HH-EE):");
	private static JTextField 			txtData 			= new JTextField();
	private static JLabel 				formSexua 			= new JLabel("Aukeratu borrokalariaren sexua");
	private static JRadioButton 		mutilaRad 			= new JRadioButton("Gizonezkoa");
	private static JRadioButton 		neskaRad 			= new JRadioButton("Emakumezkoa");
	private static JRadioButton 		ezRad 				= new JRadioButton("Nahiago dut ez esan");
	private static ButtonGroup 			buttonGroup 		= new ButtonGroup();

	// Lehiaketa formularioaren elementuak
	private static JLabel 				formDataHasi 		= new JLabel("Lehiaketaren data hasiera (UUUU-HH-EE):");
	private static JTextField 			txtDataHasi 		= new JTextField();
	private static JLabel 				formDataBukatu 		= new JLabel("Lehiaketaren data bukaera (UUUU-HH-EE):");
	private static JTextField 			txtDataBukatu 		= new JTextField();
	private static JLabel 				formLIzena 			= new JLabel("Izena");
	private static JTextField 			txtLizena 			= new JTextField();
	private static JLabel 				formKokapena 		= new JLabel("Kokapena");
	private static JTextField 			txtKokapena 		= new JTextField();
	private static JLabel 				formMartzial 		= new JLabel("Aukeratu lehiaketaren arte martziala");
	private static JComboBox<String> 	lehiaketaCombo 		= new JComboBox<>(
			new String[] { "1- Jiu-jitsu", "2- Aikido", "3- Judo", "4- Kendo" });
	
	// Taldea formularioaren elementuak
	private static JLabel 				formTalde 			= new JLabel("Taldearen izena:");
	private static JTextField 			txtTalde 			= new JTextField();
	private static JLabel 				formTaldeKokapena 	= new JLabel("Taldearen kokapena / herria:");
	private static JTextField 			txtTaldeKokapena 	= new JTextField();
	private static JLabel 				formKontaktua 		= new JLabel("Taldearen kontaktua:");
	private static JTextField 			txtKontaktua 		= new JTextField();
	private static JLabel 				formBazkide 		= new JLabel("Bazkide kopurua:");
	private static JTextField 			txtBazkide 			= new JTextField();
	
	// Formularioen izenak fitxategia bihurtu
	private static void datuakFitxategiaBihurtu() {
		
		String fileName = JOptionPane.showInputDialog("Sartu fitxategiaren izena:");
		fileName += ".txt";
		
        if (fileName != null && !fileName.isEmpty()) {
            String filePath = JOptionPane.showInputDialog("Fitxategiaren kokapena ezarri (helbide osoa.)");
            
            if (filePath != null && !filePath.isEmpty()) {
            	
                // Konprobatu ea direktorioa egokia den
                if (!filePath.endsWith(File.separator))
                    filePath += File.separator;
                
                // txt luzapena konprobatu
                if (!fileName.endsWith(".txt"))
                    fileName += ".txt";
                
                // Fitxategia sortu.
                File file = new File(filePath + fileName);
                try {
                    if (file.createNewFile()) {
                    	FileWriter writer = new FileWriter(file);
                        writer.write(txtOutput.getText());
                        writer.close();
                        JOptionPane.showMessageDialog(null, "Fitxategia ondo sortu da!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Fitxategia existitzen da.", "Errorea", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Errorea fitxategia sortzerakoan. Berriro saiatu", "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        }
	}

	// Botoia sakatzerakoan txtArean gorde lehiaketaren datuak
	private static void lehiaketaDatuakGehitu() {
		
		String 	dataHasiera = txtDataHasi.getText();
		String 	dataBukaera = txtDataBukatu.getText();
		int		martzialID	= lehiaketaCombo.getSelectedIndex() + 1;
		String ID = String.valueOf(martzialID);
		
		StringBuilder outputBuilder = new StringBuilder();
		outputBuilder.setLength(0);
		
		String izena = txtLizena.getText();
		String Kokapena = txtKokapena.getText();

		if (izena == null || izena.isEmpty() || Kokapena == null || Kokapena.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Testu kutxak ezin dute hutsik egon (izena edo kokapena)", "Errorea",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		outputBuilder.append(izena).append("	").append(Kokapena).append("	");
		
		try {
			LocalDate data 		= LocalDate.parse(dataHasiera);
			LocalDate data2 	= LocalDate.parse(dataBukaera);
		} catch (DateTimeParseException e) {
			JOptionPane.showMessageDialog(null, "Data formatu desegokian dago (UUUU-HH-EE)", "Errorea",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		outputBuilder.append(dataBukaera).append("	").append(dataHasiera).append("	").append(ID).append(",");	
		txtOutput.append(outputBuilder.toString() + "\n");
	}
	
	// Borrokalariaren datuak output testu kutxara gehitzeko metodoa
	private static void borrokalariaDatuakGehitu() {
		
		// Atributoen datuak lortu testu kutxa ezberdinetatik
		String izena 	= txtIzena.getText();
		String pisua 	= txtPisua.getText();
		String dataJ	= txtData.getText();
		String sexua	= lortuSexua(buttonGroup);
		
		StringBuilder outputBuilder = new StringBuilder();
		outputBuilder.setLength(0);
		
		// Izenaren eta pisuaren testu kutxak hutsik dauden konprobatu
		if (izena == null || izena.isEmpty() || pisua == null || pisua.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Testu kutxak ezin dute hutsik egon (izena edo pisua)", "Errorea",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		// Pisua zenbakiak diren konprobatu
		if (!pisua.matches("\\d+")) {
		    JOptionPane.showMessageDialog(null, "Pisua zenbakiz osatu behar da", "Errorea",
		            JOptionPane.ERROR_MESSAGE);
		    return;
		}
		
		outputBuilder.append(izena).append("	").append(pisua).append("	");
		
		// Dataren formato egokia erabiltzeko errore kontrola
		try {
			LocalDate data 		= LocalDate.parse(dataJ);
		} catch (DateTimeParseException e) {
			JOptionPane.showMessageDialog(null, "Data formatu desegokian dago (UUUU-HH-EE)", "Errorea",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		outputBuilder.append(dataJ).append("	").append(sexua).append(",");	
		txtOutput.append(outputBuilder.toString() + "\n");
	}
	
	// 3 Radio button arabera, aukeratutako radioButton-a lortu eta sexua atera
	private static String lortuSexua(ButtonGroup buttonGroup) {
	    Enumeration<AbstractButton> botoiak = buttonGroup.getElements();
	    while (botoiak.hasMoreElements()) {
	        AbstractButton button = botoiak.nextElement();
	        if (button.isSelected()) {
	            return button.getText();
	        }
	    }
	    return null;
	}
	
	private static void taldeaDatuakLortu() {
		
		String izena 		= txtTalde.getText();
		String kokapena 	= txtTaldeKokapena.getText();
		String kontaktua 	= txtKontaktua.getText();
		String bazkideKop 	= txtBazkide.getText();
		
		StringBuilder outputBuilder = new StringBuilder();
		outputBuilder.setLength(0);
		
		// Testu kutxak hutsik dauden konprobatu
		if (izena == null || izena.isEmpty() || kokapena == null || kokapena.isEmpty() || kontaktua == null || kontaktua.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Testu kutxak ezin dute hutsik egon (izena, kokapena edo kontaktua)", "Errorea",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		outputBuilder.append(izena).append("	").append(kokapena).append("	").append(kontaktua).append("	");
		
		// Bazkide kopurua zenbakiak diren konprobatu
		if (!bazkideKop.matches("\\d+")) {
		    JOptionPane.showMessageDialog(null, "Bazkide kopurua zenbakiz osatu behar da", "Errorea",
		            JOptionPane.ERROR_MESSAGE);
		    return;
		}
		
		outputBuilder.append(bazkideKop).append(",");
		txtOutput.append(outputBuilder.toString() + "\n");
	}

	// Berria aukera sakatzerakoan azalduko diren elementuak
	public static void artxiboBerriaElementuak() {

		// Generoa aukeratzeko radioButton-ari, gehitu
		buttonGroup.add(mutilaRad);
		buttonGroup.add(neskaRad);
		buttonGroup.add(ezRad);

		// Panel ezberdinei border inbisibleak gehitu
		panel1.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Aukeratu bat"),
                BorderFactory.createCompoundBorder(
                        new MatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY),
                        new EmptyBorder(10, 10, 10, 10)
                )
        ));
		panelBotoiak.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Output"),
                BorderFactory.createCompoundBorder(
                        new MatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY),
                        new EmptyBorder(10, 10, 10, 10)
                )
        ));
		panelBorroka.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Input"),
                BorderFactory.createCompoundBorder(
                        new MatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY),
                        new EmptyBorder(10, 10, 10, 10)
                )
        ));
		panelLehiaketa.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Input"),
                BorderFactory.createCompoundBorder(
                        new MatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY),
                        new EmptyBorder(10, 10, 10, 10)
                )
        ));
		panelTaldea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Input"),
                BorderFactory.createCompoundBorder(
                        new MatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY),
                        new EmptyBorder(10, 10, 10, 10)
                )
        ));

		// Botoiak panelaren elementuak gehitu
		panelBotoiak.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 30));
		btnGehituLerroa.setBackground(Color.BLUE);
		btnGehituLerroa.setForeground(Color.WHITE);
		btnGehituLerroa.setFont(new Font("Arial", Font.BOLD, 16));
		btnGarbitu.setBackground(Color.BLUE);
		btnGarbitu.setForeground(Color.WHITE);
		btnGarbitu.setFont(new Font("Arial", Font.BOLD, 16));
		btnEditatu.setBackground(Color.BLUE);
		btnEditatu.setForeground(Color.WHITE);
		btnEditatu.setFont(new Font("Arial", Font.BOLD, 16));
		btnSortu.setBackground(Color.BLUE);
		btnSortu.setForeground(Color.WHITE);
		btnSortu.setFont(new Font("Arial", Font.BOLD, 16));
		panelBotoiak.add(btnGehituLerroa);
		panelBotoiak.add(btnGarbitu);
		panelBotoiak.add(btnEditatu);
		panelBotoiak.add(btnSortu);
		txtOutput.setPreferredSize(new Dimension(800, 200));
		txtOutput.setFont(new Font("Arial", Font.PLAIN, 14));
		txtOutput.setBackground(Color.LIGHT_GRAY);
		txtOutput.setForeground(Color.BLACK);
		txtOutput.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		
		// Crea un JScrollPane para el JTextArea
		JScrollPane scrollPane = new JScrollPane(txtOutput);
		scrollPane.setPreferredSize(new Dimension(600, 150));
		
		panelBotoiak.add(txtOutput);

		// Aukeraketa ezberdinak egiteko panelaren elementu ezberdinak gehitu
		panel1.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel1.add(label1);
		panel1.add(fitxCombo);

		// Borrokalarien datuak sartzeko panelaren elementuak
		panelBorroka.setLayout(new GridLayout(9, 1));
		panelBorroka.add(formIzena);
		panelBorroka.add(txtIzena);
		panelBorroka.add(formPisua);
		panelBorroka.add(txtPisua);
		panelBorroka.add(formData);
		panelBorroka.add(txtData);
		panelBorroka.add(formSexua);
		panelBorroka.add(mutilaRad);
		panelBorroka.add(neskaRad);
		panelBorroka.add(ezRad);

		// Lehiaketaren datuak sartzeko panelaren elementuak
		panelLehiaketa.setLayout(new GridLayout(9, 1));
		panelLehiaketa.add(formDataHasi);
		panelLehiaketa.add(txtDataHasi);
		panelLehiaketa.add(formDataBukatu);
		panelLehiaketa.add(txtDataBukatu);
		panelLehiaketa.add(formLIzena);
		panelLehiaketa.add(txtLizena);
		panelLehiaketa.add(formKokapena);
		panelLehiaketa.add(txtKokapena);
		panelLehiaketa.add(formMartzial);
		panelLehiaketa.add(lehiaketaCombo);
		
		// Taldearen datuak sartzeko panelaren elementuak
		panelTaldea.setLayout(new GridLayout(7, 1));
		panelTaldea.add(formTalde);
		panelTaldea.add(txtTalde);
		panelTaldea.add(formTaldeKokapena);
		panelTaldea.add(txtTaldeKokapena);
		panelTaldea.add(formKontaktua);
		panelTaldea.add(txtKontaktua);
		panelTaldea.add(formBazkide);
		panelTaldea.add(txtBazkide);
		
		// Erabiltzailea panelaren elementuak
		panelErabiltzailea.setLayout(new BoxLayout(panelErabiltzailea, BoxLayout.Y_AXIS));

		centerPanel.add(panelBorroka, "Borrokalaria");
		centerPanel.add(panelLehiaketa, "Lehiaketa");
		centerPanel.add(panelTaldea, "Taldea");

		mainPanel.add(panel1, BorderLayout.WEST);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		mainPanel.add(panelBotoiak, BorderLayout.SOUTH);

		// Aukeraketaren arabera combo-box-aren bidez, panel bat edo beste agertzeko listener-a
		fitxCombo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedPanel = (String) fitxCombo.getSelectedItem();
				cardLayout.show(centerPanel, selectedPanel);
				txtOutput.setText("");
			}
		});
		
		// Output-ari lerroak gehitu erabiltzaileak ikusteko (comboBox aukeraketa arabera)
		btnGehituLerroa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fitxCombo.getSelectedIndex() == 0) {
					borrokalariaDatuakGehitu();
				}
				if (fitxCombo.getSelectedIndex() == 1) {
					lehiaketaDatuakGehitu();
				}
				if (fitxCombo.getSelectedIndex() == 2) {
					taldeaDatuakLortu();
				}
			}
		});
		
		// Output-en dauden lerroak ezabatzeko
		btnGarbitu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtOutput.setText("");
			}
		});
		
		// Output zatia editatzeko
		btnEditatu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!txtOutput.getText().isEmpty()) {
					txtOutput.setEditable(true);
					txtOutput.setBorder(BorderFactory.createEtchedBorder(Color.black, null));
				}	else	{
					JOptionPane.showMessageDialog(null,
			                "Editatzeko, lerro bat gehitu behar duzu.",
			                "Kontuz!",
			                JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		
		// Editatu ondoren, bukatzeko
		txtOutput.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseExited(MouseEvent e) {
		        txtOutput.setEditable(false);
		        txtOutput.setBorder(BorderFactory.createEtchedBorder());
		    }
		});
		
		// Sortutako datuen textua fitxategia bihutzerko botoiaren bidez
		btnSortu.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent e) {
				if (!txtOutput.getText().isEmpty()) {
				datuakFitxategiaBihurtu();				
				}	else	{
					JOptionPane.showMessageDialog(null,
			                "Output-a hutsik dago. Mesedez, lerro bat sartu fitxategia sortu baino lehen.",
			                "Kontuz!",
			                JOptionPane.WARNING_MESSAGE);
				}
			}
		});

		frame.getContentPane().removeAll();
		frame.getContentPane().add(mainPanel);
		frame.revalidate();
		frame.repaint();
	}

	// Lehioaren elementu ezberdinak jartzeko metodoa
	public static void jarriElementuak() {

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1200, 600);

		// Artxibo aukeraren elementu ezberdinak jarri
		artxMenu.add(berriaItem);
		artxMenu.add(irekiItem);
		artxMenu.add(gordeItem);
		artxMenu.add(gordehonelaItem);
		artxMenu.addSeparator();
		artxMenu.add(ateraItem);

		// Menu-aren elementu ezberdinak jarri
		menu.add(artxMenu);
		menu.add(laguntzaMenu);
		frame.setJMenuBar(menu);

		// Testu kutxei itxura
		tituloaLabel.setFont(new Font("Arial", Font.BOLD, 22));
		deskLabel.setFont(new Font("Arial", Font.PLAIN, 12));

		// Testu kutxen kokapena jarri
		gbcTituloa.gridx = 0;
		gbcTituloa.gridy = 0;
		gbcTituloa.anchor = GridBagConstraints.CENTER;

		gbcDesk.gridx = 0;
		gbcDesk.gridy = 1;
		gbcDesk.anchor = GridBagConstraints.CENTER;

		// Gehitu testu kutxak eta kokapenak
		panel.add(tituloaLabel, gbcTituloa);
		panel.add(deskLabel, gbcDesk);
		frame.add(panel, BorderLayout.CENTER);

		// Programatik ateratzeko
		ateraItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		// Artxibo berri bat sortzeko aukera
		berriaItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				artxiboBerriaElementuak();
			}
		});
		
		// Artxibo txt bat irekitzeko, ordenagailuko kokapen ezberdin ikusiz
		irekiItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();

				FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de Texto", "txt");
				fileChooser.setFileFilter(filter);

				int result = fileChooser.showOpenDialog(null);

				 if (result == JFileChooser.APPROVE_OPTION) {
	                    File selectedFile = fileChooser.getSelectedFile();
	                    artxiboBerriaElementuak();
	                    try {
	                        FileReader reader = new FileReader(selectedFile);
	                        BufferedReader bufferedReader = new BufferedReader(reader);
	                        txtOutput.setText("");
	                        String line;
	                        while ((line = bufferedReader.readLine()) != null) {
	                        	txtOutput.append(line + "\n");
	                        }
	                        bufferedReader.close();
	                    } catch (IOException ex) {
	                        ex.printStackTrace();
	                        JOptionPane.showMessageDialog(null,
	                                "Errorea fitxategia irakurtzerakoan", "Errorea", JOptionPane.ERROR_MESSAGE);
	                    }
	                }
			}
		});

		frame.setVisible(true);
	}
}