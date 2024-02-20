package arteMartzialak;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.BorderLayout;
import java.awt.CardLayout;
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
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

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
	private static JTextArea 			txtOutput 			= new JTextArea();
	private static CardLayout 			cardLayout 			= new CardLayout();
	private static JPanel 				centerPanel 		= new JPanel(cardLayout);
	private static JLabel 				label1 				= new JLabel("Aukeratu fitxategi mota:");
	private static JComboBox<String> 	fitxCombo 			= new JComboBox<>(
			new String[] { "Borrokalaria", "Lehiaketa", "Taldea", "Erabiltzailea" });

	// Borrokalariaren formularioaren elementuak
	private static JLabel 				formIzena 			= new JLabel("Borrokalariaren izena:");
	private static JTextField 			txtIzena 			= new JTextField();
	private static JLabel 				formPisua 			= new JLabel("Borrokalariaren pisua:");
	private static JTextField 			txtPisua 			= new JTextField();
	private static JLabel 				formData 			= new JLabel("Jaiotze data (DD/HH/UUUU):");
	private static JTextField 			txtData 			= new JTextField();
	private static JLabel 				formSexua 			= new JLabel("Aukeratu borrokalariaren sexua");
	private static JRadioButton 		mutilaRad 			= new JRadioButton("Gizonezkoa");
	private static JRadioButton 		neskaRad 			= new JRadioButton("Emakumezkoa");
	private static JRadioButton 		ezRad 				= new JRadioButton("Nahiago dut ez esan");
	private static ButtonGroup 			buttonGroup 		= new ButtonGroup();

	// Lehiaketa formularioaren elementuak
	private static JLabel 				formDataHasi 		= new JLabel("Lehiaketaren data hasiera (DD/HH/UUUU):");
	private static JTextField 			txtDataHasi 		= new JTextField();
	private static JLabel 				formDataBukatu 		= new JLabel("Lehiaketaren data bukaera (DD/HH/UUUU):");
	private static JTextField 			txtDataBukatu 		= new JTextField();
	private static JLabel 				formLIzena 			= new JLabel("Izena");
	private static JTextField 			txtLizena 			= new JTextField();
	private static JLabel 				formKokapena 		= new JLabel("Kokapena");
	private static JTextField 			txtKokapena 		= new JTextField();
	private static JLabel 				formMartzial 		= new JLabel("Aukeratu lehiaketaren arte martziala");
	private static JComboBox<String> 	lehiaketaCombo 		= new JComboBox<>(
			new String[] { "1- Jiu-jitsu", "2- Aikido", "3- Judo", "4- Kendo" });
	
	// Taldea formularioaren elementuak

	// Erabiltzaile formularioaren elementuak
	private static void datuakFitxategiaBihurtu() {
		String datuak = txtOutput.getText();
		String kokapena = "C:/Users/1ag3.jonmgonz/Desktop/erronkaKobaz/datuak.txt";
		
		try {
			FileWriter wr = new FileWriter(kokapena);
			wr.write(datuak);
			wr.close();
			JOptionPane.showMessageDialog(null, "Datuak gorde dira " + kokapena + " kokapenean, datu basean sartzeko prest.");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Errorea testua gordetzerakoan: " + e.getMessage(), "Errorea", JOptionPane.ERROR_MESSAGE);
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

	// Berria aukera sakatzerakoan azalduko diren elementuak
	public static void artxiboBerriaElementuak() {

		// Generoa aukeratzeko radioButton-ari, gehitu
		buttonGroup.add(mutilaRad);
		buttonGroup.add(neskaRad);
		buttonGroup.add(ezRad);

		// Panel ezberdinei border inbisibleak gehitu
		panel1.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Aukeratu bat"),
				new EmptyBorder(10, 10, 10, 10)));
		panelBotoiak.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Output"),
				new EmptyBorder(10, 10, 10, 10)));
		panelBorroka.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Input"),
				new EmptyBorder(10, 10, 10, 10)));
		panelLehiaketa.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Input"),
				new EmptyBorder(10, 10, 10, 10)));

		// Botoiak panelaren elementuak gehitu
		panelBotoiak.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 10));
		panelBotoiak.add(btnGehituLerroa);
		panelBotoiak.add(btnGarbitu);
		panelBotoiak.add(btnSortu);
		txtOutput.setPreferredSize(new Dimension(600, 150));
		txtOutput.setEditable(false);
		panelBotoiak.add(txtOutput);

		// Aukeraketa ezberdinak egiteko panelaren elementu ezberdinak gehitu
		panel1.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel1.add(label1);
		panel1.add(fitxCombo);

		// Borrokalarien datuak sartzeko panelaren elementuak
		panelBorroka.setLayout(new GridLayout(11, 1));
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

		// Erabiltzailea panelaren elementuak
		panelErabiltzailea.setLayout(new BoxLayout(panelErabiltzailea, BoxLayout.Y_AXIS));

		centerPanel.add(panelBorroka, "Borrokalaria");
		centerPanel.add(panelLehiaketa, "Lehiaketa");

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
		
		btnGehituLerroa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fitxCombo.getSelectedIndex() == 1) {
					lehiaketaDatuakGehitu();
				}				
			}
		});
		
		btnGarbitu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtOutput.setText("");
			}
		});
		
		// Sortutako datuen textua fitxategia bihutzerko botoiaren bidez
		btnSortu.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent e) {
				datuakFitxategiaBihurtu();				
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

		ateraItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		berriaItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				artxiboBerriaElementuak();
			}
		});

		frame.setVisible(true);
	}
}
