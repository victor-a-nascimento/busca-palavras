package buscapalavras;

public class ListaEncadeada<T> {

    private NoLista<T> primeiro;

    public void inserir(T valor) {

        NoLista<T> novo = new NoLista<>();
        novo.setInfo(valor);

        if (primeiro == null) {
            primeiro = novo;

        } else {
            NoLista<T> p = primeiro;

            while (p.getProximo() != null) {
                p = p.getProximo();
            }

            p.setProximo(novo);
        }
    }

    public boolean pertence(T valor) {

        NoLista<T> p = primeiro;

        while (p != null) {
            if (p.getInfo().equals(valor)) {
                return true;
            }

            p = p.getProximo();
        }

        return false;
    }

    public int tamanho() {

        int cont = 0;

        NoLista<T> p = primeiro;

        while (p != null) {
            cont++;
            p = p.getProximo();
        }

        return cont;
    }

    public T get(int indice) {

        if (indice < 0) {
            return null;
        }

        int pos = 0;

        NoLista<T> p = primeiro;

        while (p != null) {
            if (pos == indice) {
                return p.getInfo();
            }

            pos++;
            p = p.getProximo();
        }

        return null;
    }

    public boolean estaVazia() {
        return primeiro == null;
    }

    public NoLista<T> getPrimeiro() {
        return primeiro;
    }
}