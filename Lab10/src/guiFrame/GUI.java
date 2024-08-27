package guiFrame;

import repairWork.Bill;

import javax.swing.*;

import bufferPack.Connector;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class GUI extends JFrame {
	
	public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GUI().setVisible(true);
            }
        });
	}

	private static final long serialVersionUID = -8438842795663356530L;

	private JPanel mainPanel;

	private final int WIDTH = 700;
	private final int HEIGHT = 620;

	public GUI() throws HeadlessException {
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}

		JFrame frame = this;

		DefaultListModel<Bill> listModel = new DefaultListModel<>();

		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu(" File ");
		JMenuItem loadItem = new JMenuItem(" Open ");
		JMenuItem saveItem = new JMenuItem(" Save ");

		fileMenu.add(loadItem);
		fileMenu.add(saveItem);

		JMenu sortMenu = new JMenu(" Sort ");
		JMenuItem ascendingOrder = new JMenuItem(" Ascending sort ");
		JMenuItem descendingOrder = new JMenuItem(" Descending sort ");
		sortMenu.add(ascendingOrder);
		sortMenu.add(descendingOrder);

		JMenu clearMenu = new JMenu(" Clear ");
		JMenuItem deleteData = new JMenuItem(" Delete data ");
		clearMenu.add(deleteData);

		menuBar.add(fileMenu);
		menuBar.add(sortMenu);
		menuBar.add(clearMenu);
		setJMenuBar(menuBar);


		mainPanel = new JPanel();
		setResizable(false);
		mainPanel.setLayout(new BorderLayout());
		setContentPane(mainPanel);

		JPanel listPanel = new JPanel();
		listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));

		JList<Bill> listView = new JList<Bill>(listModel);
		listPanel.setLayout(new BorderLayout());
		listPanel.add(listView);

		JScrollPane scrollBar = new JScrollPane(listView);

		listPanel.add(scrollBar, BorderLayout.NORTH);
		mainPanel.add(listPanel, BorderLayout.NORTH);


		JPanel addPanel = new JPanel();
		addPanel.setLayout(new BoxLayout(addPanel, BoxLayout.Y_AXIS));

		JTextField houseNumText = new JTextField();
		JLabel houseNumLabel = new JLabel(" House number: ");
		addPanel.add(houseNumLabel);
		addPanel.add(houseNumText);

		JTextField roomNumText = new JTextField();
		JLabel roomNumLabel = new JLabel(" Room number: ");
		addPanel.add(roomNumLabel);
		addPanel.add(roomNumText);

		JTextField addressText = new JTextField();
		JLabel addressLabel = new JLabel(" Address:");
		addPanel.add(addressLabel);
		addPanel.add(addressText);

		JTextField nameText = new JTextField();
		JLabel nameLabel = new JLabel(" Name: ");
		addPanel.add(nameLabel);
		addPanel.add(nameText);

		JTextField dateText = new JTextField();
		JLabel dateLabel = new JLabel(" Date (dd.mm.yyyy): ");
		addPanel.add(dateLabel);
		addPanel.add(dateText);

		JTextField sumText = new JTextField();
		JLabel sumLabel = new JLabel(" Sum: ");
		addPanel.add(sumLabel);
		addPanel.add(sumText);

		JTextField penyaText = new JTextField();
		JLabel penyaLabel = new JLabel(" Penya:");
		addPanel.add(penyaLabel);
		addPanel.add(penyaText);

		JTextField overdueText = new JTextField();
		JLabel overdueLabel = new JLabel(" Overdue:");
		addPanel.add(overdueLabel);
		addPanel.add(overdueText);

		JButton enter = new JButton(" Enter ");
		addPanel.add(enter);

		mainPanel.add(addPanel, BorderLayout.SOUTH);

		setLocationRelativeTo(null);
		setVisible(true);



		enter.addActionListener(new AbstractAction() {

			private static final long serialVersionUID = 5951921358099901592L;

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int houseNum = 0;
					try{
						houseNum = Integer.parseInt(houseNumText.getText());
					}
					catch(Exception excp){
						houseNumText.setText("INCORRECT HOUSE NUMBER");
					}
					int roomNum = 0;
					try{
						roomNum = Integer.parseInt(roomNumText.getText());
					}
					catch(Exception excp){
						roomNumText.setText("INCORRECT ROOM NUMBER");
					}
					String address = addressText.getText();
					if (address.isEmpty())
						addressText.setText("INCORRECT ADDRESS");
					String name = nameText.getText();
					if (name.isEmpty())
						nameText.setText("INCORRECT NAME");

					SimpleDateFormat sdf = new SimpleDateFormat("dd.mm.yyyy");
					Date date = sdf.parse("01.01.2000");;
					try{
						date = sdf.parse(dateText.getText());
					}
					catch(Exception excp){
						dateText.setText("INCORRECT DATE");
					}

					int sum = 0;
					try{
						sum = Integer.parseInt(sumText.getText());
					}
					catch(Exception ex){
						sumText.setText("INCORRECT SUM");
					}

					int penya = 0;
					try{
						penya = Integer.parseInt(penyaText.getText());
					} catch(Exception excp){
						penyaText.setText("INCORRECT PENYA");
					}

					int overdue = -1;
					try{
						overdue = Integer.parseInt(overdueText.getText());
					} catch(Exception excp){
						overdueText.setText("INCORRECT OVERDUE");
					}

					Bill bill = null;
					if (houseNum != 0 && roomNum != 0 
							&& !addressText.getText().equals("INCORRECT ADDRESS") 
							&& !nameText.getText().equals("INCORRECT NAME")
							&& !dateText.getText().equals("INCORRECT DATE") 
							&& sum != 0 && penya != 0 && overdue >= 0) {
						try {
						bill = new Bill(houseNum, roomNum, address, name,
								date, sum, penya, overdue);
						}catch(IOException e1) {
							bill = null;
						}
					}
					
					if (bill != null && bill.validBillDate() 
							&& !dateText.getText().equals("INCORRECT DATE")
							&& bill.validPenya() && 
							overdue >= 0 && bill.validNumber(roomNum) && 
							bill.validNumber(houseNum) && bill.validNumber(sum))
						listModel.addElement(bill);
					
					if (bill != null && !bill.validBillDate())
						dateText.setText("INCORRECT DATE");
					if (bill != null && !bill.validPenya())
						penyaText.setText("INCORRECT PENYA");
					if (overdue < 0)
						overdueText.setText("INCORRECT OVERDUE");
					if (bill != null && !bill.validNumber(roomNum))
						roomNumText.setText("INCORRECT ROOM NUMBER");
					if (bill != null && !bill.validNumber(houseNum))
						houseNumText.setText("INCORRECT HOUSE NUMBER");
					if (bill != null && !bill.validNumber(sum))
						sumText.setText("INCORRECT SUM");


				} catch (ParseException e1) {
					e1.printStackTrace();
				}
			}
		});


		deleteData.addActionListener(new AbstractAction() {

			private static final long serialVersionUID = -7082711991094843133L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				listModel.clear();				
			}

		});


		loadItem.addActionListener(new AbstractAction() {

			private static final long serialVersionUID = -467948162958651683L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				int result = fileChooser.showOpenDialog(frame);

				if(result != JFileChooser.APPROVE_OPTION)
					return;

				File file= fileChooser.getSelectedFile();
				Connector fileDat = new Connector(file);
				try {
					ArrayList<Bill> bills = fileDat.read();
					for(Bill bill : bills)
						listModel.addElement(bill);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});


		saveItem.addActionListener(new AbstractAction() {

			private static final long serialVersionUID = -2260914577980925526L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				int result = fileChooser.showSaveDialog(frame);

				if(result != JFileChooser.APPROVE_OPTION)
					return;

				File file= fileChooser.getSelectedFile();
				Connector fileDat = new Connector(file);
				try {
					fileDat.write(listModel);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		ascendingOrder.addActionListener(new AbstractAction() {

			private static final long serialVersionUID = -1224601496650403411L;

			@Override
			public void actionPerformed(ActionEvent e) {
				ArrayList<Bill> data = new ArrayList<>();
				for(int i = 0; i < listModel.size(); ++i)
					data.add(listModel.get(i));
				Collections.sort(data,((a, b) ->{return a.getHouseNumber()-b.getHouseNumber(); }));
				listModel.clear();
				for(Bill bill:data)
					listModel.addElement(bill);
			}
		});

		descendingOrder.addActionListener(new AbstractAction() {

			private static final long serialVersionUID = 4055861752169560897L;

			@Override
			public void actionPerformed(ActionEvent e) {
				ArrayList<Bill> data = new ArrayList<>();
				for(int i = 0; i < listModel.size(); ++i)
					data.add(listModel.get(i));
				Collections.sort(data,((a, b) ->{return b.getHouseNumber()-a.getHouseNumber(); }));
				listModel.clear();
				for(Bill bill:data)
					listModel.addElement(bill);
			}
		});
	}
}