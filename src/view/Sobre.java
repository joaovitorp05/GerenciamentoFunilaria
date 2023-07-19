package view;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Cursor;

public class Sobre extends JDialog {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Sobre dialog = new Sobre();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	public Sobre() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Sobre.class.getResource("/img/about.png")));
		setTitle("Sobre");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		
		JButton btnLogo = new JButton("");
		btnLogo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLogo.setContentAreaFilled(false);
		btnLogo.setIcon(new ImageIcon(Sobre.class.getResource("/img/mit-icon.png")));
		btnLogo.setToolTipText("Licença MIT");
		btnLogo.setBorderPainted(false);
		btnLogo.setBorder(null);
		btnLogo.setBounds(284, 111, 150, 150);
		getContentPane().add(btnLogo);
		
		JLabel lblNewLabel = new JLabel("Autor: João Vitor ");
		lblNewLabel.setFont(new Font("Times New Roman", Font.ITALIC, 16));
		lblNewLabel.setBounds(108, 196, 135, 30);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Sobre a Licença MIT");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblNewLabel_1.setBounds(295, 98, 168, 17);
		getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Sistema para Gestão de Funilaria");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_2.setBounds(74, 11, 217, 43);
		getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setIcon(new ImageIcon(Sobre.class.getResource("/img/spray-gun (2).png")));
		lblNewLabel_3.setBounds(108, 56, 130, 130);
		getContentPane().add(lblNewLabel_3);

	}
}
