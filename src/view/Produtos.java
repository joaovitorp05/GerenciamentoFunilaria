package view;

import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import model.DAO;
import java.awt.Toolkit;

public class Produtos extends JDialog {
	
	private static final long serialVersionUID = 1L;
	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;
	
	//instanciar objeto para o fluxo de bytes
	private FileInputStream fis;
	
	//variável global para armazenar o tamanho da imagem(bytes)
	private int tamanho;
	
	
	
	
	
	private JLabel lblFoto;
	private JTextField txtId;
	private JTextField txtcodBarras;
	private JTextField txtDescricao;
	private JTextField txtEstoque;
	private JTextField txtEstoquemin;
	private JTextField txtValor;
	private JTextField txtLocalarmazenagem;
	private JButton btnLimpar;
	private JButton btnEditar;
	private JButton btnAdicionar;
	private JButton btnExcluir;
	private JComboBox cboUnidade;
	private JScrollPane scrollPane;
	private JList listarBarras;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Produtos dialog = new Produtos();
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
	public Produtos() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Produtos.class.getResource("/img/produtos.png")));
		getContentPane().setBackground(SystemColor.scrollbar);
		setTitle("Produtos");
		setBounds(100, 100, 536, 427);
		getContentPane().setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setVisible(false);
		scrollPane.setBounds(112, 73, 130, 40);
		getContentPane().add(scrollPane);
		
		listarBarras = new JList();
		listarBarras.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				 buscarProdutolista();
			}
		});
		scrollPane.setViewportView(listarBarras);
		
		lblFoto = new JLabel("");
		lblFoto.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		lblFoto.setIcon(new ImageIcon(Produtos.class.getResource("/img/photo128.png")));
		lblFoto.setBounds(345, 11, 130, 128);
		getContentPane().add(lblFoto);
		
		JButton btnCarregar = new JButton("Carregar Foto");
		btnCarregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				carregarFoto ();
			}
		});
		btnCarregar.setForeground(SystemColor.textHighlight);
		btnCarregar.setBounds(342, 150, 141, 23);
		getContentPane().add(btnCarregar);
		
		JLabel lblNewLabel = new JLabel("ID do produto");
		lblNewLabel.setBounds(10, 21, 75, 14);
		getContentPane().add(lblNewLabel);
		
		txtId = new JTextField();
		txtId.setEditable(false);
		txtId.setBounds(90, 18, 56, 20);
		getContentPane().add(txtId);
		txtId.setColumns(10);
		
		txtcodBarras = new JTextField();
		txtcodBarras.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				listarProdutos();
			}
			
		
		
				
			
		});
		txtcodBarras.setBounds(112, 55, 133, 20);
		getContentPane().add(txtcodBarras);
		txtcodBarras.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Código de barras");
		lblNewLabel_1.setBounds(10, 58, 106, 14);
		getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Descrição");
		lblNewLabel_2.setBounds(10, 99, 75, 14);
		getContentPane().add(lblNewLabel_2);
		
		txtDescricao = new JTextField();
		txtDescricao.setBounds(78, 96, 257, 20);
		getContentPane().add(txtDescricao);
		txtDescricao.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("Estoque");
		lblNewLabel_3.setBounds(10, 135, 46, 14);
		getContentPane().add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Estoque Minimo");
		lblNewLabel_4.setBounds(156, 135, 86, 14);
		getContentPane().add(lblNewLabel_4);
		
		txtEstoque = new JTextField();
		txtEstoque.setBounds(60, 132, 86, 20);
		getContentPane().add(txtEstoque);
		txtEstoque.setColumns(10);
		
		txtEstoquemin = new JTextField();
		txtEstoquemin.setBounds(239, 132, 86, 20);
		getContentPane().add(txtEstoquemin);
		txtEstoquemin.setColumns(10);
		
		txtValor = new JTextField();
		txtValor.setBounds(60, 173, 86, 20);
		getContentPane().add(txtValor);
		txtValor.setColumns(10);
		
		JLabel lblNewLabel_5 = new JLabel("Valor");
		lblNewLabel_5.setBounds(14, 176, 46, 14);
		getContentPane().add(lblNewLabel_5);
		
		cboUnidade = new JComboBox();
		cboUnidade.setModel(new DefaultComboBoxModel(new String[] {"UN", "CX", "PC", "KG", "M"}));
		cboUnidade.setBounds(203, 172, 56, 22);
		getContentPane().add(cboUnidade);
		
		txtLocalarmazenagem = new JTextField();
		txtLocalarmazenagem.setBounds(158, 218, 218, 20);
		getContentPane().add(txtLocalarmazenagem);
		txtLocalarmazenagem.setColumns(10);
		
		JLabel lblNewLabel_6 = new JLabel("Local de armazenagem");
		lblNewLabel_6.setBounds(10, 221, 136, 14);
		getContentPane().add(lblNewLabel_6);
		
		btnLimpar = new JButton("");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparCampos();
			}
		});
		btnLimpar.setToolTipText("Limpar Campos");
		btnLimpar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLimpar.setContentAreaFilled(false);
		btnLimpar.setBorder(null);
		btnLimpar.setIcon(new ImageIcon(Produtos.class.getResource("/img/eraser.png")));
		btnLimpar.setBounds(41, 275, 89, 60);
		getContentPane().add(btnLimpar);
		
		btnEditar = new JButton("");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editarProduto();
			}
		});
		btnEditar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEditar.setContentAreaFilled(false);
		btnEditar.setBorder(null);
		btnEditar.setToolTipText("Editar Produto");
		btnEditar.setIcon(new ImageIcon(Produtos.class.getResource("/img/editservice.png")));
		btnEditar.setBounds(156, 275, 89, 60);
		getContentPane().add(btnEditar);
		
		btnAdicionar = new JButton("");
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionar();
			}
		});
		btnAdicionar.setToolTipText("Adicionar Produto");
		btnAdicionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAdicionar.setContentAreaFilled(false);
		btnAdicionar.setBorder(null);
		btnAdicionar.setIcon(new ImageIcon(Produtos.class.getResource("/img/addproduto.png")));
		btnAdicionar.setBounds(268, 275, 89, 60);
		getContentPane().add(btnAdicionar);
		
		btnExcluir = new JButton("");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluir();
			}
		});
		btnExcluir.setToolTipText("Excluir Produto");
		btnExcluir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnExcluir.setContentAreaFilled(false);
		btnExcluir.setBorder(null);
		btnExcluir.setIcon(new ImageIcon(Produtos.class.getResource("/img/delete 0.png")));
		btnExcluir.setBounds(377, 275, 89, 60);
		getContentPane().add(btnExcluir);

	}
	private void carregarFoto() {
		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle("Selecionar Arquivo");
		jfc.setFileFilter(new FileNameExtensionFilter("Arquivo de imagens(*.PNG,*.JPG,*.JPEG)","png","jpg","jpeg"));
		int resultado = jfc.showOpenDialog(this); 
		if (resultado == JFileChooser.APPROVE_OPTION) {
			try {
				fis = new FileInputStream(jfc.getSelectedFile());
				tamanho = (int) jfc.getSelectedFile().length();
				Image foto = ImageIO.read(jfc.getSelectedFile()).getScaledInstance(lblFoto.getWidth(), lblFoto.getHeight(), Image.SCALE_SMOOTH);
				lblFoto.setIcon(new ImageIcon(foto));
				lblFoto.updateUI();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		
		
	}
	private void excluir() {
		//System.out.println("teste do botão excluir");
		//validação de exclusão
		int confirma = JOptionPane.showConfirmDialog(null, "Confirma a exclusão deste produto?","Atenção!",
				JOptionPane.YES_NO_OPTION);
		if(confirma == JOptionPane.YES_OPTION) {
			//CRUD - Delete
			String delete = "delete from produtos where codigoproduto=?";
			//tratamento de exceções
			try {
				//abrir conexão
				con = dao.conectar();
				//preparar a query (instrução sql)
				pst = con.prepareStatement(delete);
				//substituir a ? pelo id do contato
				pst.setString(1, txtId.getText());
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
	private void adicionar() {

		
		 if (txtcodBarras.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Código de Barras do produto");
			txtcodBarras.requestFocus();
		} else if (txtDescricao.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a Descrição do produto");
			txtDescricao.requestFocus();
		} else if (txtEstoque.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Estoque do produto");
			txtEstoque.requestFocus();
		} else if (txtEstoquemin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a Estoque Mínimo do produto ");
			txtEstoquemin.requestFocus();
		} else if (txtValor.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Valor do produto");
			txtValor.requestFocus();
		} else if(cboUnidade.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "Preencha a Unidade de medida do produto");
			cboUnidade.requestFocus();
		} else if (txtLocalarmazenagem.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Local de armazenagem do produto");
			txtLocalarmazenagem.requestFocus();
		} else {
			String create = "insert into produtos(codigodebarras, descricao, foto, estoque, estoquemin, valor, unidadedemedida, localdearmazenagem) values(?,?,?,?,?,?,?,?)";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(create);
				pst.setString(1, txtcodBarras.getText());
				pst.setString(2, txtDescricao.getText());
				pst.setBlob(3, fis, tamanho);
				pst.setString(4, txtEstoque.getText());
				pst.setString(5, txtEstoquemin.getText());
				pst.setString(6, txtValor.getText());
				pst.setString(7, cboUnidade.getSelectedItem().toString());
				pst.setString(8, txtLocalarmazenagem.getText());
				
				int confirma = pst.executeUpdate();
				if (confirma == 1) {
					JOptionPane.showMessageDialog(null, "Produto adicionado");
				} else {
					JOptionPane.showMessageDialog(null, "Produto inexistente");
				}
				limparCampos();
			
				//fechar a conexão
				con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
			
		}
	}
	private void editarProduto() {
		if (txtcodBarras.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o código de barras do produto");
			txtcodBarras.requestFocus();
		} else {
			String update = "update produtos set codigodebarras=?, descricao=?, foto=?, estoque=?, estoquemin=?, valor=?, unidadedemedida=?, localdearmazenagem=? WHERE codigoproduto=? ";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(update);
				pst.setString(1, txtcodBarras.getText());
				pst.setString(2, txtDescricao.getText());
				pst.setBlob(3, fis, tamanho);
				pst.setString(4, txtEstoque.getText());
				pst.setString(5, txtEstoquemin.getText());
				pst.setString(6, txtValor.getText());
				pst.setString(7, cboUnidade.getSelectedItem().toString());
				pst.setString(8, txtLocalarmazenagem.getText());
				pst.setString(9, txtId.getText());
				
				pst.executeUpdate();
				limparCampos();
				
				
				JOptionPane.showMessageDialog(null, "Dados do produto editados com sucesso");
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		
		}
		private void limparCampos() {
			txtId.setText(null);
			txtcodBarras.setText(null);
			txtDescricao.setText(null);
			txtEstoque.setText(null);
			txtEstoquemin.setText(null);
			txtValor.setText(null);
			cboUnidade.setSelectedItem(null);
			lblFoto.setIcon(new ImageIcon(Produtos.class.getResource("/img/photo128.png")));
			txtLocalarmazenagem.setText(null);
			txtcodBarras.requestFocus();
			
	}
	private void buscarProdutolista() {
		int linha = listarBarras.getSelectedIndex();
		if (linha >= 0) {
			String readlistaUsuario = "select * from produtos where codigodebarras like '" + txtcodBarras.getText()

			+ "%'" + "order by codigodebarras limit " + (linha) + " , 1";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(readlistaUsuario);
				rs = pst.executeQuery();
				if (rs.next()) {
					scrollPane.setVisible(false);
					txtId.setText(rs.getString(1));
					txtcodBarras.setText(rs.getString(2));
					txtDescricao.setText(rs.getString(3));
					Blob blob = (Blob) rs.getBlob(4);
					byte[] img = blob.getBytes(1, (int) blob.length());
					BufferedImage imagem = null;
					txtEstoquemin.setText(rs.getString(5));
					txtEstoque.setText(rs.getString(6));
					txtValor.setText(rs.getString(7));
					cboUnidade.setSelectedItem(rs.getString(8));
					txtLocalarmazenagem.setText(rs.getString(9));
					
					try {
						imagem = ImageIO.read(new ByteArrayInputStream(img));
					} catch (Exception e) {
					    System.out.println(e);
					}
					ImageIcon icone = new ImageIcon(imagem);
					Icon foto = new ImageIcon(icone.getImage().getScaledInstance(lblFoto.getWidth(),
					lblFoto.getHeight(), Image.SCALE_SMOOTH));
					lblFoto.setIcon(foto);
					
				} else {
					JOptionPane.showMessageDialog(null, "Produtos inexistente");
				}
				con.close();
			} catch (Exception e) {
				
			}
		} else {
			scrollPane.setVisible(false);
		}
	}
	private void listarProdutos() {
		//System.out.println("teste");
		// a linha abaixo cria um objeto usando como referencia um vetor dinamico,este objeto ira temporariamente armazenar os nomes.
		DefaultListModel<String> modelo = new DefaultListModel<>();
		// setar o modelo (vetor na lista)
		listarBarras.setModel(modelo);
		// Query (instrução sql)
		String readlista = "select * from produtos where codigodebarras like '" + txtcodBarras.getText() + "%'" + "order by codigodebarras";
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
				if (txtcodBarras.getText().isEmpty()) {
					scrollPane.setVisible(false);
				}
				
			}
			//fechar a conexão
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
