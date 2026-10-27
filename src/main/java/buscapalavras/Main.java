package buscapalavras;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;
import javax.swing.SwingUtilities;

public class Main {

	private static final String ARQ = "indice.txt";

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		MapaDispersao<ListaEncadeada<String>> indice;

		File arquivoIndice = new File(ARQ);

		if (arquivoIndice.exists()) {
			indice = carregar();

			System.out.println("Indice carregado do disco.");
		} else {
			indice = new MapaDispersao<>(101);

			System.out.print("Informe o diretorio: ");
			String dir = sc.nextLine();

			new Indexador(indice).indexarDiretorio(new File(dir));

			salvar(indice);

			System.out.println("Indice criado e salvo.");
		}

		SwingUtilities.invokeLater(() -> new TelaPesquisa(indice));

		sc.close();
	}


	static void salvar(MapaDispersao<ListaEncadeada<String>> ind) {

		try (PrintWriter pw = new PrintWriter(new FileWriter(ARQ))) {
			ListaEncadeada<NoMapa<ListaEncadeada<String>>>[] tabela = ind.getTabela();

			for (ListaEncadeada<NoMapa<ListaEncadeada<String>>> lista : tabela) {
				if (lista == null) {
					continue;
				}

				NoLista<NoMapa<ListaEncadeada<String>>> p = lista.getPrimeiro();

				while (p != null) {
					NoMapa<ListaEncadeada<String>> no = p.getInfo();

					pw.print(no.getChave() + "|");

					ListaEncadeada<String> docs = no.getValor();

					NoLista<String> d = docs.getPrimeiro();

					while (d != null) {
						pw.print(d.getInfo());

						if (d.getProximo() != null) {
							pw.print(";");
						}

						d = d.getProximo();
					}

					pw.println();

					p = p.getProximo();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static MapaDispersao<ListaEncadeada<String>> carregar() {

		MapaDispersao<ListaEncadeada<String>> indice = new MapaDispersao<>(101);

		File arquivo = new File(ARQ);

		if (!arquivo.exists()) {
			return indice;
		}

		try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
			String linha;

			while ((linha = br.readLine()) != null) {
				String[] partes = linha.split("\\|");

				if (partes.length != 2) {
					continue;
				}

				String palavra = partes[0];

				ListaEncadeada<String> docs = new ListaEncadeada<>();

				String[] documentos = partes[1].split(";");

				for (String doc : documentos) {
					if (!doc.trim().isEmpty()) {
						docs.inserir(doc);
					}
				}

				indice.inserir(palavra, docs);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		return indice;
	}
}