# CodeAI

> An experimental project leveraging the power of AI to help developers write better code.
> The project provides a REST server feeding an embedded Neo4j database with codebase data and a REST CLI to interact with the server.

## Requirements

- Java 21

## Run

CodeAI is a plain Java application that can be run from the command line.
The following command will start the application:

**Start the REST server**
```shell
java -cp codeai.jar io.morin.codeai.restserver.RestServer <codebase path>
```

**Start the REST CLI**
```shell
java -cp codeai.jar io.morin.codeai.restcli.RestCli
```

## Configuration

Each key in the table below corresponds to an environment variable or a Java system property.

For instance, to set the `codeai.langchain4j.integration` property, you can use the following command line:

```shell
java -Dcodeai.langchain4j.integration=MISTRAL -jar codeai-0.0.1-SNAPSHOT.jar
```

Or you can set the `codeai.langchain4j.integration` environment variable:

```shell
codeai_langchain4j_integration=MISTRAL java -jar codeai-0.0.1-SNAPSHOT.jar
```

| Key                                            | Default                        |
|------------------------------------------------|--------------------------------|
| `codeai.langchain4j.integration`               | `MISTRAL` or `OLLAMA`          |
| `codeai.langchain4j.dimension`                 | `384`                          |
| `codeai.langchain4j.max_memory_messages`       | `20`                           |
| `codeai.langchain4j.max_segment_size_in_chars` | `500`                          |
| `codeai.langchain4j.max_overlap_size_in_chars` | `0`                            |
| `codeai.mistral.model_name`                    | `mistral-medium`               |
| `codeai.mistral.api_key`                       |                                |
| `codeai.neo4j.host`                            | `localhost`                    |
| `codeai.neo4j.port`                            | `7687`                         |
| `codeai.ollama.model_name`                     | `mistral`                      |
| `codeai.ollama.base_url`                       | `http://localhost:11434`       |
| `codeai.restserver.host`                       | `localhost`                    |
| `codeai.restserver.port`                       | `9090`                         |
| `codeai.rest_cli.prompt_url`                   | `http://localhost:9090/prompt` |

## Build

**Build the uber jar**
```shell
./mvnw verify
```

## Maintenance

**Dependencies upgrade**
```shell
./mvnw versions:display-dependency-updates
```

**Release**
```shell
./mvnw --batch-mode release:clean \
&& ./mvnw --batch-mode release:prepare \
  -DreleaseVersion=X.Y.Z \
  -DdevelopmentVersion=Y.X.Z-SNAPSHOT
```
