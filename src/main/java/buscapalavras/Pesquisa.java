package buscapalavras;

public class Pesquisa {

    public static ListaEncadeada<String> pesquisar(MapaDispersao<ListaEncadeada<String>> indice,String consulta) {

        ListaEncadeada<String> resultado = new ListaEncadeada<>();

        if (consulta == null || consulta.trim().isEmpty()) {
            return resultado;
        }

        String[] palavras = consulta.toLowerCase().split("\\s+");

        ListaEncadeada<String> base = indice.buscar(palavras[0]);

        if (base == null || base.estaVazia()) {
            return resultado;
        }

        NoLista<String> atual = base.getPrimeiro();

        while (atual != null) {
            String documento = atual.getInfo();

            boolean contemTodas = true;

            for (int i = 1; i < palavras.length; i++) {
                ListaEncadeada<String> docs = indice.buscar(palavras[i]);

                if (docs == null || !docs.pertence(documento)) {
                    contemTodas = false;
                    break;
                }
            }

            if (contemTodas) {
                resultado.inserir(documento);
            }

            atual = atual.getProximo();
        }

        return resultado;
    }
}