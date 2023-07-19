package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import model.DAO;
import java.awt.SystemColor;

public class Login extends JFrame {
	
	//objetos JDBC
	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;
	//objeto tela principal
	Principal principal = new Principal();

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtLogin;
	private JPasswordField txtSenha;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
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
	public Login() {
		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 503, 347);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Login");
		lblNewLabel.setBounds(19, 51, 46, 14);
		contentPane.add(lblNewLabel);
		
		txtLogin = new JTextField();
		txtLogin.setBounds(75, 48, 235, 20);
		contentPane.add(txtLogin);
		txtLogin.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Senha");
		lblNewLabel_1.setBounds(19, 107, 46, 14);
		contentPane.add(lblNewLabel_1);
		
		txtSenha = new JPasswordField();
		txtSenha.setBounds(75, 104, 235, 20);
		contentPane.add(txtSenha);
		
		JButton btnAcessar = new JButton("Acessar");
		btnAcessar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logar();
			}
		});
		btnAcessar.setBounds(218, 159, 89, 23);
		contentPane.add(btnAcessar);
	}//fim do construtor
	
	/**
	 * Método para autenticar um usuário
	 */
	private void logar() {
		//Criar uma variável/objeto para capturar a senha
		String capturaSenha = new String(txtSenha.getPassword());
		
		//validação
		if (txtLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o login");
			txtLogin.requestFocus();
		} else if (capturaSenha.length() == 0) {
			JOptionPane.showMessageDialog(null, "Preencha a senha");
			txtSenha.requestFocus();
		} else {
			//lógica principal
			String read = "select * from users where login=? and senha=md5(?)";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(read);
				pst.setString(1, txtLogin.getText());
				pst.setString(2, capturaSenha);
				rs = pst.executeQuery();
				if (rs.next()) {
					//Capturar perfil de usuario
					//System.out.println(rs.getString(5));//apoio a logica
					//tratamento do perfl do usuario
				    String perfil = rs.getString(5);
				    if(perfil.equals("admin")) {
				    	//logar -> acessar a tela principal
						principal.setVisible(true);
						principal.lblUsuario.setText(rs.getString(2));
						//habilitar os botoes
						principal.btnRelatorios.setEnabled(true);
						principal.btnUsuarios.setEnabled(true);
						//mudar a cor do rodapé
						principal.panelRodape.setBackground(Color.RED);
						
						//fechar a tela de login(esta tela)
						this.dispose();
				    	
				    } else {
				    	principal.setVisible(true);
						principal.lblUsuario.setText(rs.getString(2));
						this.dispose();
				    }
					
					
				} else {
					JOptionPane.showMessageDialog(null, "usuário e/ou senha inválido(s)");
				}
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}
}//fim do código
