package modulos.componente;

import dataFactory.UsuarioDataFactory;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;

@DisplayName("Testes de API Rest do modulo de componentes")
public class ComponenteTest {
    private String token;

    @BeforeEach
    public void obterToken(){

        /*Configurando a API da Lojinha*/
        baseURI = "http://165.227.93.41";
        basePath = "/lojinha";

        /*Obtendo o token do usuário*/
        this.token =
            given()
                .contentType(ContentType.JSON)
                .body(UsuarioDataFactory.criarUsuario("FernandoTeodoro", "4n4k1u$m0$"))
            .when()
                .post("/v2/login")
            .then()
                .extract()
                    .path("data.token");
    }

    @Test
    @DisplayName("Buscar dados dos componentes de um produto")
    public void testBuscarDadosComponente(){

        /*Obtendo o id do primeiro produto da lista*/
        int produtoId =
            given()
                .contentType(ContentType.JSON)
                .header("token", this.token)
            .when()
                .get("/v2/produtos")
            .then()
                .extract()
                    .path("data[0].produtoId");

        /*Busca os dados dos componentes de um determinado produto, udando o id do produto como parametro de busca*/
        given()
            .contentType(ContentType.JSON)
            .header("token", this.token)
        .when()
            .get("/v2/produtos/" + produtoId + "/componentes")
        .then()
            .assertThat()
                .statusCode(200);
    }
}

