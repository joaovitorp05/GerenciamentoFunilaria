package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import model.DAO;
import utils.Validador;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;

public class Usuarios extends JDialog {
	// Instanciar objetos JDBC
		DAO dao = new DAO();
		private Connection con;
		private PreparedStatement pst;
		private ResultSet rs;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtID;
	private JTextField txtNome;
	private JTextField txtLogin;
	private JButton btnAdicionar;
	private JButton btnPesquisar;
	private JButton btnExcluir;
	private JButton btnEditar;
	private JList listUsers;
	private JScrollPane scrollPane;
	private JLabel lblNewLabel;
	private JComboBox cboPerfil;
	private JCheckBox chckSenha;
	private JPasswordField txtSenha;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Usuarios dialog = new Usuarios();
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
	public Usuarios() {
		getContentPane().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// clicar no painel do JDialog
				scrollPane.setVisible(false);
				txtNome.setText(null);
			}
		});
		setIconImage(Toolkit.getDefaultToolkit().getImage(Usuarios.class.getResource("/img/users.png")));
		setTitle("Usuários");
		setModal(true);
		setBounds(100, 100, 595, 373);
		getContentPane().setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setVisible(false);
		scrollPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buscarUsuarioLista ();
			}
		});
		scrollPane.setBorder(null);
		scrollPane.setBounds(66, 60, 203, 73);
		getContentPane().add(scrollPane);
		
		listUsers = new JList();
		scrollPane.setViewportView(listUsers);
		listUsers.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buscarUsuarioLista ();
				
			}
		});
		listUsers.setBorder(null);
		listUsers.setDoubleBuffered(true);

		JLabel lblId = new JLabel("ID:");
		lblId.setBounds(10, 6, 46, 28);
		lblId.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		getContentPane().add(lblId);

		JLabel lblNome = new JLabel("Nome:");
		lblNome.setBounds(10, 37, 46, 28);
		lblNome.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		getContentPane().add(lblNome);

		JLabel lblLogin = new JLabel("Login:");
		lblLogin.setBounds(10, 88, 56, 28);
		lblLogin.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		getContentPane().add(lblLogin);

		JLabel lblSenha = new JLabel("Senha:");
		lblSenha.setBounds(10, 142, 46, 28);
		lblSenha.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		getContentPane().add(lblSenha);

		txtID = new JTextField();
		txtID.setBounds(66, 11, 100, 20);
		txtID.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		txtID.setEditable(false);
		getContentPane().add(txtID);
		txtID.setColumns(10);

		txtNome = new JTextField();
		txtNome.setBounds(66, 42, 200, 20);
		txtNome.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				// na digitação...
							}
			@Override
			public void keyReleased(KeyEvent e) {
				//soltar uma tecla
				listarUsuarios();

				
			}
		});
		getContentPane().add(txtNome);
		txtNome.setColumns(10);
		txtNome.setDocument(new Validador(30));

		txtLogin = new JTextField();
		txtLogin.setBounds(66, 93, 200, 20);
		getContentPane().add(txtLogin);
		txtLogin.setColumns(10);
		txtLogin.setDocument(new Validador(20));

		JButton btnLimpar = new JButton("");
		btnLimpar.setBounds(329, 259, 75, 52);
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			limparCampos();
			}
		});
		btnLimpar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLimpar.setBorder(null);
		btnLimpar.setContentAreaFilled(false);
		btnLimpar.setIcon(new ImageIcon(Usuarios.class.getResource("/img/eraser.png")));
		btnLimpar.setToolTipText("Limpar Campos");
		getContentPane().add(btnLimpar);

		btnPesquisar = new JButton("");
		btnPesquisar.setBounds(429, 259, 52, 52);
		btnPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscar();
			}
		});
		btnPesquisar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnPesquisar.setIcon(new ImageIcon(Usuarios.class.getResource("/img/pesquisar.png")));
		btnPesquisar.setToolTipText("Pesquisar");
		btnPesquisar.setBorder(null);
		btnPesquisar.setContentAreaFilled(false);
		getContentPane().add(btnPesquisar);

		getRootPane().setDefaultButton(btnPesquisar);
		
		btnAdicionar = new JButton("");
		btnAdicionar.setEnabled(false);
		btnAdicionar.setBounds(10, 261, 89, 50);
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionarUsuario();
			}
		});
		btnAdicionar.setIcon(new ImageIcon(Usuarios.class.getResource("/img/add.png")));
		btnAdicionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAdicionar.setContentAreaFilled(false);
		btnAdicionar.setBorder(null);
		btnAdicionar.setToolTipText("Adicionar");
		getContentPane().add(btnAdicionar);
		
		btnExcluir = new JButton("");
		btnExcluir.setEnabled(false);
		btnExcluir.setBounds(206, 251, 60, 60);
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirContato();
			}
		});
		btnExcluir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnExcluir.setContentAreaFilled(false);
		btnExcluir.setBorder(null);
		btnExcluir.setIcon(new ImageIcon(Usuarios.class.getResource("/img/delete 0.png")));
		btnExcluir.setToolTipText("Excluir");
		getContentPane().add(btnExcluir);
		
		btnEditar = new JButton("");
		btnEditar.setEnabled(false);
		btnEditar.setBounds(109, 251, 70, 60);
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(chckSenha.isSelected()) {
					editarUsuario();
					
				} else {
					editarUsuarioExcetoSenha();
				}
			}
		});
		btnEditar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEditar.setContentAreaFilled(false);
		btnEditar.setBorder(null);
		btnEditar.setIcon(new ImageIcon(Usuarios.class.getResource("/img/editar.png")));
		btnEditar.setToolTipText("Editar");
		getContentPane().add(btnEditar);
		
		lblNewLabel = new JLabel("Perfil");
		lblNewLabel.setBounds(310, 88, 46, 14);
		getContentPane().add(lblNewLabel);
		
		cboPerfil = new JComboBox();
		cboPerfil.setModel(new DefaultComboBoxModel(new String[] {"", "admin", "user"}));
		cboPerfil.setBounds(344, 84, 70, 22);
		getContentPane().add(cboPerfil);
		
		chckSenha = new JCheckBox("Alterar senha");
		chckSenha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chckSenha.isSelected()) {
				txtSenha.setText(null);
				txtSenha.requestFocus();
				txtSenha.setBackground(Color.GREEN);
			} else {
				txtSenha.setBackground(Color.WHITE);
			}
			}
		});
		chckSenha.setBounds(18, 192, 132, 23);
		getContentPane().add(chckSenha);
		
		txtSenha = new JPasswordField();
		txtSenha.setBounds(66, 147, 203, 20);
		getContentPane().add(txtSenha);

	} // Fim do Construtor
	
	private void listarUsuarios() {
		//System.out.println("teste");
		// a linha abaixo cria um objeto usando como referencia um vetor dinamico,este objeto ira temporariamente armazenar os nomes.
		DefaultListModel<String> modelo = new DefaultListModel<>();
		// setar o modelo (vetor na lista)
		listUsers.setModel(modelo);
		// Query (instrução sql)
		String readlista = "select * from users where nome like '" + txtNome.getText() + "%'" + "order by nome";
		try {
			// abrir conexão
			con = dao.conectar();
			// preparar a query (instrução sql)
			pst = con.prepareStatement(readlista);
			// executar a query e trazer o resultado para a lista
			rs = pst.executeQuery();
			// uso do while para trazer os usuarios enquanto existir
			while(rs.next()) {
				// mostrar a barra de rolagem (scrollpane)
				scrollPane.setVisible(true);
				// adicionar os usuarios no vetor -> lista
				modelo.addElement(rs.getString(2));
				// esconder a lista se nenhuma letra for digitada
				if (txtNome.getText().isEmpty()) {
					scrollPane.setVisible(false);
				}
				
			}
			//fechar a conexão
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * Limpar campos
	 */
	private void limparCampos() {
		txtID.setText(null);
		txtNome.setText(null);
		txtLogin.setText(null);
		txtSenha.setText(null);
		btnEditar.setEnabled(false);
		btnExcluir.setEnabled(false);
		btnPesquisar.setEnabled(true);
		txtSenha.setBackground(Color.WHITE);
		btnAdicionar.setEnabled(false);
		cboPerfil.setSelectedItem(null);
		scrollPane.setVisible(false);
		chckSenha.setSelected(false);
		
	}

	/**
	 * Método para buscar um contato pelo nome
	 */
	private void buscar() {
		//dica - testar o evento primeiro
		//System.out.println("teste do botão buscar");
		// Criar uma variável com a query (instrução do banco)
		String read = "select * from users where login = ?";
		//tratamento de exceções
		try {
			//abrir a conexão
			con = dao.conectar();
			//preparar a execução da query (instrução sql - CRUD Read)
			// O parâmetro 1 substitui a ? pelo conteúdo da caixa de texto
			pst = con.prepareStatement(read);
			pst.setString(1, txtLogin.getText());
			// executar a query e buscar o resultado
			rs = pst.executeQuery();
			// uso da estrutura if else para verificar se existe o contato
			// rs.next() -> se existir um contato no banco
			if (rs.next()) {
				// preencher as caixas de texto com as informações
				txtID.setText(rs.getString(1)); // 1º Campo da tabela ID
				txtNome.setText(rs.getString(2));
				txtLogin.setText(rs.getString(3)); // 3º Campo da tabela (Fone)
				txtSenha.setText(rs.getString(4)); // 4° Campo da tabela (Email) 
				cboPerfil.setSelectedItem(rs.getString(5));
				
				btnEditar.setEnabled(true);
				btnExcluir.setEnabled(true);
				btnPesquisar.setEnabled(true);
			} else {
				//se não existir um contato no banco
				JOptionPane.showMessageDialog(null, "Usuário inexistente");
				btnPesquisar.setEnabled(false);
				btnAdicionar.setEnabled(true);
			}
			// fechar conexão (IMPORTANTE)
			con.close();
		} catch (Exception e) {
			
		}
	}//fim do método buscar
	
	/**
	 *  Método para adicionar um novo contato
	 */
	
	private void adicionarUsuario() {
		// Criar uma variável/objeto para capturar a senha
		String capturaSenha = new String(txtSenha.getPassword());
		// System.out.println("teste");
		// validação de campos obrigatórios
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o nome do usuário");
			txtNome.requestFocus();
		} else if (txtLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o login do usuário");
			txtLogin.requestFocus();
		} else if (capturaSenha.length() == 0) {
			JOptionPane.showMessageDialog(null, "Preencha a senha do usuário");
			txtSenha.requestFocus();
		} else if (cboPerfil.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "Preencha o Perfil");
			cboPerfil.requestFocus();
		} else {
			// lógica principal
			// CRUD Create
			String create = "insert into users(nome,login,senha,perfil) values (?,?,?,?)";
			// tratamento de exceções
			try {
				// abrir a conexão
				con = dao.conectar();
				// preparar a execução da query(instrução sql - CRUD Create)
				pst = con.prepareStatement(create);
				pst.setString(1, txtNome.getText());
				pst.setString(2, txtLogin.getText());
				pst.setString(3, capturaSenha);
				pst.setString(4, cboPerfil.getSelectedItem().toString());
				// executa a query(instrução sql (CRUD - Create))
				pst.executeUpdate();
				// confirmar
				JOptionPane.showMessageDialog(null, "Usuário adicionado");
				// limpar os campos
				limparCampos();
				// fechar a conexão
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
			
			


			
		}
		
	}
	private void excluirContato() {
		int confirma = JOptionPane.showConfirmDialog(null, "Confirma a exclusão deste contato?","Atenção!",
				JOptionPane.YES_NO_OPTION);
		if(confirma == JOptionPane.YES_OPTION) {
			String delete = "delete from users where id=?";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(delete);
				pst.setString(1, txtID.getText());
				pst.executeUpdate();
				limparCampos();
				btnPesquisar.setEnabled(true);
				JOptionPane.showMessageDialog(null, "Contato excluído");
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}
	private void editarUsuarioExcetoSenha() {
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o nome");
			txtNome.requestFocus();
		} else if (txtLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o login");
			txtLogin.requestFocus();
		} else {
			String update2 = "update users set nome=?,login=?,perfil=? where id=?";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(update2);
				pst.setString(1, txtNome.getText());
				pst.setString(2, txtLogin.getText());
				pst.setString(3, cboPerfil.getSelectedItem().toString());
				pst.setString(4, txtID.getText());
				
				pst.executeUpdate();
				limparCampos();
				btnPesquisar.setEnabled(true);
				JOptionPane.showMessageDialog(null, "Dados do contato editados com sucesso");
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}
	private void editarUsuario() {
	    if (txtNome.getText().isEmpty()) {
	        JOptionPane.showMessageDialog(null, "Digite o nome");
	        txtNome.requestFocus();
	    } else if (txtLogin.getText().isEmpty()) {
	        JOptionPane.showMessageDialog(null, "Digite o login");
	        txtLogin.requestFocus();
	    } else {
	        String update2 = "update users set nome=?, login=?, senha=md5(?), perfil=? where id=?";
	        try {
	            con = dao.conectar();
	            pst = con.prepareStatement(update2);
	            pst.setString(1, txtNome.getText());
	            pst.setString(2, txtLogin.getText());
	            pst.setString(3, txtSenha.getText());
	            pst.setString(4, cboPerfil.getSelectedItem().toString());
	            pst.setString(5, txtID.getText());

	            pst.executeUpdate();
	            limparCampos();
	            btnPesquisar.setEnabled(true);
	            JOptionPane.showMessageDialog(null, "Dados do contato editados com sucesso");
	        } catch (Exception e) {
	            System.out.println(e);
	        }
	    }
	}

	private void buscarUsuarioLista() {
		//System.out.println("teste");
		// variavel que captura o indice da linha da lista
		int linha = listUsers.getSelectedIndex();
		if (linha>= 0) {
			// Query (instrução sql)
			// limit (0,1) -> seleciona o indice 0 e 1 usuario da lista
			String readlistaUsuario = "select * from users where nome like '" + txtNome.getText() + "%'" + "order by nome limit " + (linha) + " , 1";
			try {
				// abrir a conexão
				con = dao.conectar();
				//preparar a query para a execução
				pst = con.prepareStatement(readlistaUsuario);
				//executar e obter o resultado
				rs = pst.executeQuery();
				if (rs.next()) {
					// esconder a linha
					scrollPane.setVisible(false);
					//setar os campos
					txtID.setText(rs.getString(1)); 
					txtNome.setText(rs.getString(2));
					txtLogin.setText(rs.getString(3)); 
					txtSenha.setText(rs.getString(4));
					cboPerfil.setSelectedItem(rs.getString(5));
				} else {
					JOptionPane.showMessageDialog(null, "Usuário inexistente");
				}
				// fechar a conexão
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} else {
			// se não existir no banco um usuario da lista
			scrollPane.setVisible(false);
			
			
		}
		
	}
} //Fim do Código
