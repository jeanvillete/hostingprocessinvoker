### Para que serve a aplicação? ###
> Basicamente, o HPI (Hosting Process Invoker) tem o objetivo de executar tarefas pré-programadas em um servidor remoto.
> O HPI é divido em duas partes, um servidor que gerencia "Invokers" [mais detalhes abaixo sobre Invokers](veja.md) e outra parte que é um cliente, que comunica com o servidor fazendo perguntas de quais "Invokers" existem no contexto do servidor e ainda pode invocar a execução de um destes "Invokers", ou seja, das tarefas cadastradas no servidor.
> Para o cliente, existe a possibilidade de um cliente para PC e outro para Mobilie escrito em J2ME.


**Não conhece o formato SSD?** <a href='https://code.google.com/p/simplestructuredata/'>clique aqui</a>


### O que são Invokers para o HPI? ###
> Os Invokers são arquivos ".SSD" que contém informações para execução de uma tarefa.
> Exemplo do conteúdo de um invoker;
```
   {
      description = "this invoker is used to turn the tomcat service up"
      , executables = [
         {
           canonical_path = "C:\\Program Files\\Apache Software Foundation\\Tomcat 6.0\\bin\\startup.bat"
           , parameters = [
              { key="-Djavax.net.debug", value="all"}
              , { key="-Denvironment.name", value="-dev5"}
           ]
         }
      ]
   }
```
> É possível então indicar no descritor do invoker;
    * description: Uma breve descrição do que se trata este invoker;
    * executables: Um conjunto de "Executables". Cada executable deve apontar para um arquivo que possa ser executável no S.O. corrente (um script ou um executável de fato) e pode ter uma lista de parâmetros para este executável.

## HPI Servidor ##
### Colocando o Servidor no ar ###
> Para rodar o servidor do HPI, basta ir ao diretório "$HPI\_HOME\bin\" e executar o script de startup (e.g. startup.cmd no ambiente Windows)

### Configurações no servidor ###
> O arquivo "$HPI\_HOME\conf\hpi\_data\_settings.ssd" é utilizado para configurações do servidor, após qualquer edição, o servidor deve ser parado e iniciado novamente.
> O conteúdo padrão deste arquivo de configurações é como o seguinte;
```
   {
      version = "1.0"
      , mapped_folders = [
         { relative_server = "mapinvs" }
      ]
      , configuration_server {
         port_number = "4444"
         , session_manager = {
            keep_session_alive = "180000"
         }
      }
      , users = [
         { nickname = "admin", passphrase = "admin"}
         , { nickname = "jean", passphrase = "jbadm"}
      ]
   }
```
Pode-se então ser definido neste arquivo;
  * version: a versão do descritor
  * port\_number: o número da porta em que o servidor responderá
  * keep\_session\_alive: tempo em milisegundos em que a sessão de um usuário permanecerá ativa
  * users: usuários cadastrados para poder se cadastrar no sistema
  * mapped\_folders: um "mapped\_folder" representa um diretório onde deve-se adicionar invokers e para os invokers, pode-se adicionar como "canonical\_path" ou como "relative\_server" que no caso é um endereço relativo a "$HPI\_HOME".
```
   ...
   , mapped_folders = [
      { relative_server = "mapinvs" }
      , { canonical_path = "C:\\Program Files (x86)\\hpi_server\\another_mapinvs" }
   ]
   ...
```

### Desligando o Servidor ###
> Para desligar o servidor, vá até o diretório "$HPI\_HOME\bin\" e execute o script de shutdown (e.g. shutdown.cmd no ambiente Windows)

### Logs ###
> Todo evento no servidor passível de log será impresso no diretório "$HPI\_HOME\logs\" nos arquivos "datawatcher.log" ou "hpi\_server.log";

### Notas do servidor HPI ###
  1. Com o servidor no ar, pode-se adicionar e/ou remover "invokers" que estes serão adicionados e/ou removidos do contexto do servidor automaticamente.
  1. Por padrão, há um arquivo denominado "readme.txt" no diretório "$HPI\_HOME\mapinvs\".