package buscapalavras;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Indexador {

    private MapaDispersao<ListaEncadeada<String>> indice;

    public Indexador(MapaDispersao<ListaEncadeada<String>> indice) {
        this.indice = indice;
    }

    public void indexarDiretorio(File dir) {

        if (dir == null || !dir.exists() || !dir.isDirectory()) {
            return;
        }

        File[] arquivos = dir.listFiles();

        if (arquivos == null) {
            return;
        }

        for (File arquivo : arquivos) {
            if (arquivo.isDirectory()) {
                indexarDiretorio(arquivo);

            } else if (arquivo.getName().toLowerCase().endsWith(".txt")) {
                indexarArquivo(arquivo);
            }
        }
    }

    private void indexarArquivo(File file) {

        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String linha = sc.nextLine().toLowerCase();

                linha = linha.replaceAll("[^\\p{L}0-9]", " ");

                String[] palavras = linha.split("\\s+");

                for (String p : palavras) {
                    if (!palavraValida(p)) {
                        continue;
                    }

                    ListaEncadeada<String> docs = indice.buscar(p);

                    if (docs == null) {
                        docs = new ListaEncadeada<>();
                        indice.inserir(p, docs);
                    }

                    if (!docs.pertence(file.getName())) {
                        docs.inserir(file.getName());
                    }
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("Erro ao ler arquivo: " + file.getAbsolutePath());
        }
    }

    private boolean palavraValida(String palavra) {

        if (palavra == null || palavra.isEmpty()) {
            return false;
        }

        if (palavra.length() < 3) {
            return false;
        }

        if (palavra.matches("[0-9]+")) {
            return false;
        }

        return true;
    }
}