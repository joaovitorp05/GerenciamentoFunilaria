package view;

import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import model.DAO;
import utils.Validador;
import javax.swing.border.BevelBorder;

public class Servicos extends JDialog {
	
	
	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtOS;
	private JTextField txtData;
	private JTextField txtModelo;
	private JTextField txtPeca;
	private JTextField txtValor;
	private JTextField txtID;
	private JTextField txtCliente;
	private JScrollPane painel1;
	private JList listarUsuarios;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Servicos dialog = new Servicos();
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
	public Servicos() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Servicos.class.getResource("/img/REPORT 128.png")));
		setTitle("Ordem de Serviço");
		setModal(true);
		setBounds(100, 100, 540, 416);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("OS");
		lblNewLabel.setBounds(20, 78, 32, 14);
		getContentPane().add(lblNewLabel);
		
		txtOS = new JTextField();
		txtOS.setEditable(false);
		txtOS.setBounds(74, 75, 86, 20);
		getContentPane().add(txtOS);
		txtOS.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Data");
		lblNewLabel_1.setBounds(20, 112, 39, 14);
		getContentPane().add(lblNewLabel_1);
		
		txtData = new JTextField();
		txtData.setEditable(false);
		txtData.setBounds(74, 109, 125, 20);
		getContentPane().add(txtData);
		txtData.setColumns(10);
		
		
		JLabel lblNewLabel_3 = new JLabel("Modelo Carro");
		lblNewLabel_3.setBounds(10, 167, 86, 14);
		getContentPane().add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Peça");
		lblNewLabel_4.setBounds(32, 198, 46, 14);
		getContentPane().add(lblNewLabel_4);
		
		txtModelo = new JTextField();
		txtModelo.setBounds(88, 164, 346, 20);
		getContentPane().add(txtModelo);
		txtModelo.setColumns(10);
		txtModelo.setDocument(new Validador(30));
		
		txtPeca = new JTextField();
		txtPeca.setBounds(88, 195, 346, 20);
		getContentPane().add(txtPeca);
		txtPeca.setColumns(10);
		txtPeca.setDocument(new Validador(50));
		
		txtValor = new JTextField();
		txtValor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				//digitação...
				//definir caracteres permitidos
				String caracteres = "0123456789.";
				//se for digitado algum caractere não permitido
				if (!caracteres.contains(e.getKeyChar()+ "")) {
					//ignorar
					e.consume();
				}
				
			}
			
		});
		txtValor.setBounds(88, 226, 111, 20);
		getContentPane().add(txtValor);
		txtValor.setColumns(10);
		txtValor.setDocument(new Validador(10));
		
		JLabel lblNewLabel_5 = new JLabel("Valor");
		lblNewLabel_5.setBounds(32, 229, 46, 14);
		getContentPane().add(lblNewLabel_5);
		
		JButton btnAdicionar = new JButton("");
		btnAdicionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAdicionar.setContentAreaFilled(false);
		btnAdicionar.setBorder(null);
		btnAdicionar.setIcon(new ImageIcon(Servicos.class.getResource("/img/addservice.png")));
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionar();
			}
		});
		btnAdicionar.setBounds(10, 310, 70, 45);
		getContentPane().add(btnAdicionar);
		
		JButton btnEditar = new JButton("");
		btnEditar.setIcon(new ImageIcon(Servicos.class.getResource("/img/editservice.png")));
		btnEditar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEditar.setContentAreaFilled(false);
		btnEditar.setBorder(null);
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editar();
			}
		});
		btnEditar.setBounds(74, 305, 75, 50);
		getContentPane().add(btnEditar);
		
		JButton btnExcluir = new JButton("");
		btnExcluir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnExcluir.setContentAreaFilled(false);
		btnExcluir.setBorder(null);
		btnExcluir.setIcon(new ImageIcon(Servicos.class.getResource("/img/delete 0.png")));
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluir();
			}
		});
		btnExcluir.setBounds(159, 300, 75, 55);
		getContentPane().add(btnExcluir);
		
		JButton btnBuscar = new JButton("");
		btnBuscar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnBuscar.setContentAreaFilled(false);
		btnBuscar.setBorder(null);
		btnBuscar.setIcon(new ImageIcon(Servicos.class.getResource("/img/pesquisar.png")));
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscar();
			}
		});
		btnBuscar.setBounds(158, 60, 80, 35);
		getContentPane().add(btnBuscar);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Cliente", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(248, 11, 218, 115);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		painel1 = new JScrollPane();
		painel1.setVisible(false);
		painel1.setBorder(null);
		painel1.setBounds(11, 43, 177, 32);
		panel.add(painel1);
		
		listarUsuarios = new JList();
		listarUsuarios.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buscarUsuarioLista();
			}
		});
		painel1.setViewportView(listarUsuarios);
		
		txtCliente = new JTextField();
		txtCliente.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtCliente.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				listarUsuarios ();
			}
		});
		txtCliente.setBounds(11, 24, 177, 20);
		panel.add(txtCliente);
		txtCliente.setColumns(10);
		
		txtID = new JTextField();
		txtID.setEditable(false);
		txtID.setBounds(91, 84, 75, 20);
		panel.add(txtID);
		txtID.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("ID");
		lblNewLabel_2.setBounds(33, 87, 46, 14);
		panel.add(lblNewLabel_2);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparCampos();
			}
		});
		btnNewButton.setContentAreaFilled(false);
		btnNewButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton.setBorder(null);
		btnNewButton.setIcon(new ImageIcon(Servicos.class.getResource("/img/eraser.png")));
		btnNewButton.setBounds(244, 305, 80, 50);
		getContentPane().add(btnNewButton);
		
		JButton btnOS = new JButton("OS");
		btnOS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				imprimirOS();
			}
		});
		btnOS.setBounds(360, 322, 89, 23);
		getContentPane().add(btnOS);

	}
	private void buscar() {
		//captura do numero da OS (sem usar a caixa de texto)
		String numOS = JOptionPane.showInputDialog("Número da OS");
		
		String read = "select * from servicos where os = ?";
		try {
			//ABRIR A CONEXÃO
			con = dao.conectar();
			//preparar a query
			pst = con.prepareStatement(read);
			//substituir a ? (parametro) pelo número da OS
			pst.setString(1, numOS);
			rs = pst.executeQuery();
			if (rs.next()) {
				txtOS.setText(rs.getString(1)); 
				txtData.setText(rs.getString(2));
				txtModelo.setText(rs.getString(3));
				txtPeca.setText(rs.getString(4));
				txtValor.setText(rs.getString(5));
				txtID.setText(rs.getString(6));
			
		} else {
			JOptionPane.showMessageDialog(null, "Serviço inexistente");
			
		}
		// fechar a conexão
		con.close();
	} catch (Exception e) {
		System.out.println(e);
		}
	}
	
	
	private void adicionar() {
		 if (txtModelo.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Modelo do serviço");
			txtModelo.requestFocus();
		} else if (txtPeca.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a Peça");
			txtPeca.requestFocus();
		} else if (txtValor.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Valor do serviço");
			txtValor.requestFocus();
		} else {
			String create = "insert into servicos(equipamento,defeito,valor,idcli) values (?,?,?,?)";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(create);
				pst.setString(1, txtModelo.getText());
				pst.setString(2, txtPeca.getText());
				pst.setString(3, txtValor.getText());
				pst.setString(4, txtID.getText());
				pst.executeUpdate();
				limparCampos();
				JOptionPane.showMessageDialog(null, "Serviço adicionado");
				//fechar a conexão
				con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
			
		}
	}
	private void excluir() {
		//System.out.println("teste do botão excluir");
		//validação de exclusão
		int confirma = JOptionPane.showConfirmDialog(null, "Confirma a exclusão deste serviço?","Atenção!",
				JOptionPane.YES_NO_OPTION);
		if(confirma == JOptionPane.YES_OPTION) {
			//CRUD - Delete
			String delete = "delete from servicos where os=?";
			//tratamento de exceções
			try {
				//abrir conexão
				con = dao.conectar();
				//preparar a query (instrução sql)
				pst = con.prepareStatement(delete);
				//substituir a ? pelo id do contato
				pst.setString(1, txtOS.getText());
				//executar a query
				pst.executeUpdate();
				//exibir uma mensagem ao usúario
				limparCampos();
				JOptionPane.showMessageDialog(null, "Serviço excluído");
				//fechar a conexão
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}
	
	private void editar() {
		//System.out.println("teste do botão editar");
		//validação dos campos obrigatórios
		if (txtOS.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite a OS do serviço");
			txtOS.requestFocus();
		} else if (txtModelo.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o modelo");
			txtModelo.requestFocus();
		} else {
			//lógica principal
			//CRUD - update
			String update = "update servicos set os=?,dataOS=?,equipamento=?,defeito=?,valor=? where os=?";
			//tratamento de exceções
			try {
				//abrir a conexão
				con = dao.conectar();
				//preparar a query (instrução sql)
				pst = con.prepareStatement(update);
				pst.setString(1, txtOS.getText());
				pst.setString(2, txtData.getText());
				pst.setString(3, txtModelo.getText());
				pst.setString(4, txtPeca.getText());
				pst.setString(5, txtValor.getText());
				pst.setString(6, txtOS.getText());
				//executar a query
				pst.executeUpdate();
				limparCampos();
				//confirmar para o usúario
				JOptionPane.showMessageDialog(null, "Dados do serviço editados com sucesso");
			} catch (Exception e) {
				System.out.println(e);
			}
				
			}
		}
	private void limparCampos() {
		txtID.setText(null);
		txtOS.setText(null);
		txtData.setText(null);
		txtCliente.setText(null);
		txtModelo.setText(null);
		txtPeca.setText(null);
		txtValor.setText(null);
		
	}
	private void buscarUsuarioLista() {
		//System.out.println("teste");
				// variavel que captura o indice da linha da lista
				int linha = listarUsuarios.getSelectedIndex();
				if (linha>= 0) {
					// Query (instrução sql)
					// limit (0,1) -> seleciona o indice 0 e 1 usuario da lista
					String readlistaUsuario = "select * from clientes where nome like '" + txtCliente.getText() + "%'" + "order by nome limit " + (linha) + " , 1";
					try {
						// abrir a conexão
						con = dao.conectar();
						//preparar a query para a execução
						pst = con.prepareStatement(readlistaUsuario);
						//executar e obter o resultado
						rs = pst.executeQuery();
						if (rs.next()) {
							// esconder a linha
							painel1.setVisible(false);
							//setar os campos
							txtID.setText(rs.getString(1));
							
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
					painel1.setVisible(false);
				}
	}
	private void listarUsuarios() {
		// a linha abaixo cria um objeto usando como referencia um vetor dinamico,este objeto ira temporariamente armazenar os nomes.
		DefaultListModel<String> modelo = new DefaultListModel<>();
		// setar o modelo (vetor na lista)
		listarUsuarios.setModel(modelo);
		// Query (instrução sql)
		String readlista = "select * from clientes where nome like '" + txtCliente.getText() + "%'" + "order by nome";
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
							painel1.setVisible(true);
							// adicionar os usuarios no vetor -> lista
							modelo.addElement(rs.getString(2));
							// esconder a lista se nenhuma letra for digitada
							if (txtCliente.getText().isEmpty()) {
								painel1.setVisible(false);
							}
						}
						//fechar a conexão
						con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	/**
	 * Impressão da OS
	 */
	private void imprimirOS() {
		// instanciar objeto para usar os métodos da biblioteca
		Document document = new Document();
		// documento pdf
		try {
			// criar um documento em branco (pdf) de nome clientes.pdf
			PdfWriter.getInstance(document, new FileOutputStream("os.pdf"));
			// abrir o documento (formatar e inserir o conteúdo)
			document.open();
			String readOS = "select * from servicos where os = ?";
			// conexão com o banco
			try {
				// abrir a conexão
				con = dao.conectar();
				// preparar a execução da query (instrução sql)
				pst = con.prepareStatement(readOS);
				pst.setString(1, txtOS.getText());
				// executar a query
				rs = pst.executeQuery();
				// se existir a OS
				if (rs.next()) {					
					//document.add(new Paragraph("OS: " + rs.getString(1)));
					Paragraph os = new Paragraph ("OS: " + rs.getString(1));
					os.setAlignment(Element.ALIGN_RIGHT);
					document.add(os);
						
					Paragraph modelo = new Paragraph ("Modelo: " + rs.getString(3));
					modelo.setAlignment(Element.ALIGN_LEFT);
					document.add(modelo);
					
					Paragraph peca = new Paragraph ("Peça: " + rs.getString(4));
					peca.setAlignment(Element.ALIGN_LEFT);
					document.add(peca);
					
					Paragraph valor = new Paragraph ("Valor: " + rs.getString(5));
					valor.setAlignment(Element.ALIGN_LEFT);
					document.add(valor);
					
					Paragraph data = new Paragraph ("Data: " + rs.getString(2));
					data.setAlignment(Element.ALIGN_LEFT);
					document.add(data);
					
					//imprimir imagens
					Image imagem = Image.getInstance(Servicos.class.getResource("/img/plantabmw.jpg"));
					//resolução de imagem
					imagem.scaleToFit(500,350);
					imagem.setAbsolutePosition(10, 330);
					document.add(imagem);					
				}
				// fechar a conexão com o banco
				con.close();
				} catch (Exception e) {
					System.out.println(e);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		// fechar o documento (pronto para "impressão" (exibir o pdf))
		document.close();
		// Abrir o desktop do sistema operacional e usar o leitor padrão
		// de pdf para exibir o documento
		try {
			Desktop.getDesktop().open(new File("os.pdf"));
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
