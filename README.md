# ubs-api

# Download do projeto
Faça o download via git clone ou através do github.

Dentro do projeto no mesmo diretório do pom.xml execute o seguinte comando:
mvn clean package

# Executando a API
Após terminar o build execute o próximo comando:
java -jar ubs-api-0.0.1-SNAPSHOT.jar "DIRETÓRIO_DOS_ARQUIVOS_JSON"

Quando a api finalizar a carga de dados irá aparecer o log: "Fim da carga de dados!"

# Calcular dados

Para calcular os dados utilize a seguinte url:
http://localhost:8080/estoque?produto=?&lojas=?

Informe o código do produto e quantidade de lojas na URL.
