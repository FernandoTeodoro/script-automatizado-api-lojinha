package dataFactory;

import pojo.ComponentePojo;
import pojo.ProdutoPojo;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDataFactory {
    public static ProdutoPojo criarProdutoComOValor(double valor){

        ProdutoPojo produto = new ProdutoPojo();
        produto.setProdutoNome("PlayStation 4");
        produto.setProdutoValor(valor);

        List<String> cores = new ArrayList<>();
        cores.add("Preto");
        cores.add("Branco");

        produto.setProdutoCores(cores);
        produto.setProdutoUrlMock("");

        List<ComponentePojo> componentes = new ArrayList<>();
        ComponentePojo componente = new ComponentePojo();
        componente.setNomeComponente("Controle");
        componente.setQtdComponente("2");
        componentes.add(componente);

        ComponentePojo componente2 = new ComponentePojo();
        componente2.setNomeComponente("Jogo: Tomb Raider - Legend");
        componente2.setQtdComponente("1");
        componentes.add(componente2);

        produto.setComponente(componentes);

        return produto;
    }

    public static ProdutoPojo alterarDadosDoProduto(double valor){

        ProdutoPojo produto = new ProdutoPojo();
        produto.setProdutoNome("MegaDriver");
        produto.setProdutoValor(valor);

        List<String> cores = new ArrayList<>();
        cores.add("Azul");
        cores.add("Verde");

        produto.setProdutoCores(cores);
        produto.setProdutoUrlMock("");

        List<ComponentePojo> componentes = new ArrayList<>();
        ComponentePojo componente = new ComponentePojo();
        componente.setNomeComponente("Controle");
        componente.setQtdComponente("1");
        componentes.add(componente);

        ComponentePojo componente2 = new ComponentePojo();
        componente2.setNomeComponente("Jogo: Sonic");
        componente2.setQtdComponente("1");
        componentes.add(componente2);

        produto.setComponente(componentes);

        return produto;
    }
}
