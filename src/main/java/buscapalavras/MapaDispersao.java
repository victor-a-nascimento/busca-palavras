package buscapalavras;

public class MapaDispersao<T> {

	private ListaEncadeada<NoMapa<T>>[] tabela;
	private int quantidadeElementos;

	public MapaDispersao(int tamanho) {

		tabela = new ListaEncadeada[tamanho];
		quantidadeElementos = 0;
	}

	private int hash(String chave) {

		int h = 0;

		for (int i = 0; i < chave.length(); i++) {
			h = 31 * h + chave.charAt(i);
		}

		return Math.abs(h) % tabela.length;
	}

	private double fatorCarga() {
		return (double) quantidadeElementos / tabela.length;
	}

	private void redimensionar() {

		ListaEncadeada<NoMapa<T>>[] tabelaAntiga = tabela;

		tabela = new ListaEncadeada[(tabelaAntiga.length * 2) + 1];

		int elementosAntigos = quantidadeElementos;
		quantidadeElementos = 0;

		for (ListaEncadeada<NoMapa<T>> lista : tabelaAntiga) {
			if (lista == null) {
				continue;
			}

			NoLista<NoMapa<T>> p = lista.getPrimeiro();

			while (p != null) {
				NoMapa<T> no = p.getInfo();

				inserirRehash(no.getChave(),no.getValor());

				p = p.getProximo();
			}
		}

		quantidadeElementos = elementosAntigos;
	}

	private void inserirRehash(String chave, T valor) {

		int pos = hash(chave);

		if (tabela[pos] == null) {
			tabela[pos] = new ListaEncadeada<>();
		}

		tabela[pos].inserir(new NoMapa<>(chave, valor));
	}

	public void inserir(String chave, T valor) {

		if (fatorCarga() >= 0.75) {
			redimensionar();
		}

		int pos = hash(chave);

		if (tabela[pos] == null) {
			tabela[pos] = new ListaEncadeada<>();
		}

		NoMapa<T> novo = new NoMapa<>(chave, valor);

		if (!tabela[pos].pertence(novo)) {
			tabela[pos].inserir(novo);
			quantidadeElementos++;
		}
	}

	public T buscar(String chave) {

		int pos = hash(chave);

		if (tabela[pos] == null) {
			return null;
		}

		NoLista<NoMapa<T>> p = tabela[pos].getPrimeiro();

		while (p != null) {

			NoMapa<T> no = p.getInfo();

			if (no.getChave().equals(chave)) {
				return no.getValor();
			}

			p = p.getProximo();
		}

		return null;
	}

	public boolean contemChave(String chave) {
		return buscar(chave) != null;
	}

	public ListaEncadeada<NoMapa<T>>[] getTabela() {
		return tabela;
	}

	public int getQuantidadeElementos() {
		return quantidadeElementos;
	}

	public double getFatorCarga() {
		return fatorCarga();
	}

	public int getTamanhoTabela() {
		return tabela.length;
	}
}