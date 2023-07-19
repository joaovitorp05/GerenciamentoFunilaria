package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.DAO;
import java.awt.SystemColor;

public class Principal extends JFrame {
	// Instanciar objetos JDBC
	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblStatusz;
	private JLabel lblData;
	//esta label será alterada pela tela de Login (public)
	public JLabel lblUsuario;
	public JButton btnRelatorios;
	public JButton btnUsuarios;
	public JPanel panelRodape;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal frame = new Principal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Principal() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Principal.class.getResource("/img/paint-spray.png")));
		setTitle("Sistema da Funilaria");
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				//evento ativar janela 
				status();
				setarData();
				
			}
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 707, 479);
		contentPane = new JPanel();
		contentPane.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		btnUsuarios = new JButton("");
		btnUsuarios.setEnabled(false);
		btnUsuarios.setBorder(null);
		btnUsuarios.setContentAreaFilled(false);
		btnUsuarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// abrir tela de usuários
				Usuarios usuarios = new Usuarios();
				usuarios.setVisible(true);
			}
		});
		btnUsuarios.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUsuarios.setIcon(new ImageIcon(Principal.class.getResource("/img/users.png")));
		btnUsuarios.setToolTipText("Usuários");
		btnUsuarios.setBounds(10, 37, 141, 137);
		contentPane.add(btnUsuarios);

		JButton btnSobre = new JButton("");
		btnSobre.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSobre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Sobre sobre = new Sobre();
				sobre.setVisible(true);
			}
		});
		btnSobre.setContentAreaFilled(false);
		btnSobre.setIcon(new ImageIcon(Principal.class.getResource("/img/about.png")));
		btnSobre.setToolTipText("Sobre");
		btnSobre.setActionCommand("Sobre");
		btnSobre.setBorder(null);
		btnSobre.setBounds(639, 0, 52, 52);
		contentPane.add(btnSobre);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 128, 255));
		panel.setBounds(622, 377, -623, 52);
		contentPane.add(panel);
								
		panelRodape = new JPanel();
		panelRodape.setBackground(new Color(0, 0, 0));
		panelRodape.setBounds(0, 377, 691, 52);
		contentPane.add(panelRodape);
		panelRodape.setLayout(null);
		
		lblData = new JLabel("");
		lblData.setBounds(384, 11, 297, 28);
		panelRodape.add(lblData);
		lblData.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblData.setForeground(new Color(255, 255, 255));
		
		lblStatusz = new JLabel("");
		lblStatusz.setBounds(0, 0, 48, 48);
		panelRodape.add(lblStatusz);
		lblStatusz.setIcon(new ImageIcon(Principal.class.getResource("/img/dboff.png")));
		
		JLabel lblNewLabel_1 = new JLabel("Usuário:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1.setForeground(SystemColor.text);
		lblNewLabel_1.setBackground(Color.WHITE);
		lblNewLabel_1.setBounds(80, 25, 62, 14);
		panelRodape.add(lblNewLabel_1);
		
		lblUsuario = new JLabel("");
		lblUsuario.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblUsuario.setForeground(SystemColor.text);
		lblUsuario.setBounds(134, 25, 222, 14);
		panelRodape.add(lblUsuario);
		
		btnRelatorios = new JButton("");
		btnRelatorios.setEnabled(false);
		btnRelatorios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Relatorios relatorios = new Relatorios();
				relatorios.setVisible(true);
				
			}
		});
		btnRelatorios.setToolTipText("Relatórios");
		btnRelatorios.setBorder(null);
		btnRelatorios.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnRelatorios.setContentAreaFilled(false);
		btnRelatorios.setIcon(new ImageIcon(Principal.class.getResource("/img/REPORT 128.png")));
		btnRelatorios.setBounds(10, 196, 128, 128);
		contentPane.add(btnRelatorios);
		
		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Servicos servicos = new Servicos();
				servicos.setVisible(true);
				
			}
		});
		btnNewButton_1.setToolTipText("Serviços");
		btnNewButton_1.setContentAreaFilled(false);
		btnNewButton_1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton_1.setBorder(null);
		btnNewButton_1.setIcon(new ImageIcon(Principal.class.getResource("/img/SERVIÇOS.png")));
		btnNewButton_1.setBounds(178, 196, 128, 128);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("");
		btnNewButton_2.setToolTipText("Clientes");
		btnNewButton_2.setBorder(null);
		btnNewButton_2.setContentAreaFilled(false);
		btnNewButton_2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton_2.setIcon(new ImageIcon(Principal.class.getResource("/img/USERS 128.png")));
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Clientes clientes = new Clientes();
				clientes.setVisible(true);
				
			}
		});
		btnNewButton_2.setBounds(178, 37, 128, 128);
		contentPane.add(btnNewButton_2);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(Principal.class.getResource("/img/logo tela inicial.png")));
		lblNewLabel.setBounds(563, 249, 128, 128);
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton_3 = new JButton("");
		btnNewButton_3.setToolTipText("Produtos");
		btnNewButton_3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton_3.setContentAreaFilled(false);
		btnNewButton_3.setBorder(null);
		btnNewButton_3.setIcon(new ImageIcon(Principal.class.getResource("/img/produtos.png")));
		btnNewButton_3.setBounds(340, 206, 128, 128);
		contentPane.add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton("");
		btnNewButton_4.setToolTipText("Fornecedores");
		btnNewButton_4.setContentAreaFilled(false);
		btnNewButton_4.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton_4.setBorder(null);
		btnNewButton_4.setIcon(new ImageIcon(Principal.class.getResource("/img/fornecedores1.png")));
		btnNewButton_4.setBounds(340, 37, 140, 140);
		contentPane.add(btnNewButton_4);

	} // Fim do Construtor

	private void status() {
		try {
			//abrir conexão
			con = dao.conectar();
			if (con == null) {
				//System.out.println("Erro de Conexão");
				lblStatusz.setIcon(new ImageIcon(Principal.class.getResource("/Img/dboff.png")));
			} else {
				//System.out.println("Banco Conectado");
				lblStatusz.setIcon(new ImageIcon(Principal.class.getResource("/Img/dbon.png")));
			}
			//NUNCA esquecer de fechar a conexão
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	
	} // Fim do Código
	
	/*
	 *  Método resposavel por setar a data no rodapé
	 */
	private void setarData() {
		Date data = new Date();
		//criar obejeto para formatar a data
		DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
		// alterar o texto da label pela data atual formatada
		lblData.setText(formatador.format(data));
	}
}
