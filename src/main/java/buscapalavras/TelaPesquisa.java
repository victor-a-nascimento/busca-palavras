package buscapalavras;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class TelaPesquisa extends JFrame {

	private JTextField txtPesquisa;
	private JTextArea txtResultado;
	private JButton btnPesquisar;

	private MapaDispersao<ListaEncadeada<String>> indice;

	public TelaPesquisa(MapaDispersao<ListaEncadeada<String>> indice) {

		this.indice = indice;

		setTitle("Pesquisa de Palavras");
		setSize(700, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		setLayout(new BorderLayout());

		JPanel painelTopo = new JPanel(new BorderLayout());

		txtPesquisa = new JTextField();
		btnPesquisar = new JButton("Pesquisar");

		painelTopo.add(txtPesquisa, BorderLayout.CENTER);
		painelTopo.add(btnPesquisar, BorderLayout.EAST);

		txtResultado = new JTextArea();
		txtResultado.setEditable(false);

		JScrollPane scroll = new JScrollPane(txtResultado);

		add(painelTopo, BorderLayout.NORTH);
		add(scroll, BorderLayout.CENTER);

		btnPesquisar.addActionListener(e -> pesquisar());

		txtPesquisa.addActionListener(e -> pesquisar());

		setVisible(true);
	}

	private void pesquisar() {

	    String consulta = txtPesquisa.getText().toLowerCase().replaceAll("[^\\p{L}0-9]", " ").trim();

	    txtResultado.setText("");

	    if (consulta.isEmpty()) {
	        txtResultado.setText("Digite uma palavra.");
	        return;
	    }

	    ListaEncadeada<String> resultado = Pesquisa.pesquisar(indice, consulta);

	    if (resultado.estaVazia()) {
	        txtResultado.setText("Nenhum documento encontrado.");
	        return;
	    }

	    NoLista<String> p = resultado.getPrimeiro();

	    while (p != null) {
	        txtResultado.append(p.getInfo() + "\n");
	        p = p.getProximo();
	    }
	}
}