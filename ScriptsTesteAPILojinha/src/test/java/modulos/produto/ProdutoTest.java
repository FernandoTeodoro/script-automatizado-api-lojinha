package modulos.produto;

import dataFactory.ProdutoDataFactory;
import dataFactory.UsuarioDataFactory;
import io.github.cdimascio.dotenv.Dotenv;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@DisplayName("Testes de API Rest do modulo de produto")
public class ProdutoTest {
    Dotenv dotenv = Dotenv.load();
    private String token;

    private String user = dotenv.get("INFO_USER");
    private String password = dotenv.get("INFO_PASSWORD");

    @BeforeEach
    public void obterToken(){

        /* Conigurando os dados da API da Lojinha */
        baseURI = "http://165.227.93.41";
        basePath = "/lojinha";

        /* Obter o token do usuário */
        this.token =
            given()
                .contentType(ContentType.JSON)
                .body(UsuarioDataFactory.criarUsuario(user, password))
            .when()
                .post("/v2/login")
            .then()
                .extract()
                    .path("data.token");
    }

    @Test
    @DisplayName("Validar que o do valor do produto = 0.00 não é permitido")
    public void testValidarLimiteMIinimoProibidoValorProdutos(){

        /* Tentar inserir um produto com o valor 0.00 e validar se a mensagem de erro foi apresentada e o status code
        retornado foi 422 */
        given()
            .contentType(ContentType.JSON)
            .header("token", this.token)
            .body(ProdutoDataFactory.criarProdutoComOValor(0.00))
        .when()
            .post("/v2/produtos")
        .then()
            .assertThat()
                .body("error", equalTo("O valor do produto deve estar entre R$ 0,01 e R$ 7.000,00"))
                .statusCode(422);
    }

    @Test
    @DisplayName("Validar que o do valor do produto = 7000.01 não é permitido")
    public void testValidarLimiteMaximoProibidoValorProdutos(){

        /* Tentar inserir um produto  com o valor 7000.01 e validar se a mensagem de erro foi apresentada e o status code
        retornado foi 422 */
        given()
            .contentType(ContentType.JSON)
            .header("token", this.token)
            .body(ProdutoDataFactory.criarProdutoComOValor(7000.01))
        .when()
            .post("/v2/produtos")
        .then()
            .assertThat()
                .body("error", equalTo("O valor do produto deve estar entre R$ 0,01 e R$ 7.000,00"))
                .statusCode(422);
    }

    @Test
    @DisplayName("Buscar todos os produtos de um determinado usuário")
    public void testBuscarProdutosDoUsuario(){

        /* Busca todos os produtos cadastrados por um usuário e confirma se o codigo retornado foi 200 */
        given()
            .contentType(ContentType.JSON)
            .header("token", this.token)
        .when()
            .get("/v2/produtos")
        .then()
            .assertThat()
                .statusCode(200);
    }

    @Test
    @DisplayName("Buscar um produto usando o seu ID")
    public void testBuscarProdutoUsandoId(){

        /*Busca o ID de um produto*/
        int produtoId =
            given()
                .contentType(ContentType.JSON)
                .header("token", this.token)
            .when()
                .get("/v2/produtos")
            .then()
                .extract()
                    .path("data[0].produtoId");

        /* Busca um produto cadastrado por um usuário com um ID específico confirma se o codigo retornado foi 200 */
        given()
            .contentType(ContentType.JSON)
            .header("token", this.token)
        .when()
            .get("/v2/produtos/" + produtoId)
        .then()
            .assertThat()
                .statusCode(200);
    }

    @Test
    @DisplayName("Altera as informações de um produto")
    public void testAlterarProduto(){

        /*Busca o ID de um produto*/
        int produtoId =
            given()
            .contentType(ContentType.JSON)
            .header("token", this.token)
            .when()
                .get("/v2/produtos")
            .then()
                .extract()
                    .path("data[0].produtoId");


        /*Altera as informações do produto com id igual ao contido em produtoId */
        given()
            .contentType(ContentType.JSON)
            .header("token", this.token)
                .body(ProdutoDataFactory.alterarDadosDoProduto(1000.00))
        .when()
            .put("/v2/produtos/" + produtoId)
        .then()
            .assertThat()
                .statusCode(200);
    }

    @Test
    @DisplayName("Deleta as informações de um produto")
    public void testDeletarDadosProduto(){

        /*Busca o ID de um produto*/
        int produtoId =
            given()
                .contentType(ContentType.JSON)
                .header("token", this.token)
            .when()
                .get("/v2/produtos")
            .then()
                .extract()
                    .path("data[0].produtoId");

        /*Deleta as informações do produto com id igual ao contido em produtoId */
        given()
            .contentType(ContentType.JSON)
            .header("token", this.token)
        .when()
            .delete("/v2/produtos/" + produtoId)
        .then()
            .assertThat()
                .statusCode(204);
    }
}
