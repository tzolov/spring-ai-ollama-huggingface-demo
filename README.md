# Spring AI Ollama Hugging Face Integration

This project demonstrates the integration of Spring AI with Ollama, leveraging Hugging Face GGUF models for both chat completion and embedding tasks.

## Overview

Ollama now supports all [GGUF](https://github.com/ggerganov/ggml/blob/master/docs/gguf.md) models from Hugging Face, allowing access to over 45,000 community-created models through [Spring AI's Ollama](https://docs.spring.io/spring-ai/reference/api/chat/ollama-chat.html) integration, runnable locally.

## Prerequisites

- Java 17 or higher
- Maven
- [Ollama](https://ollama.com/download) installed on your system

## Setup

1. Add the Spring AI BOM and the Spring AI Ollama boot starter to your project's `pom.xml`:

```xml
<dependency>
   <groupId>org.springframework.ai</groupId>
   <artifactId>spring-ai-ollama-spring-boot-starter</artifactId>
</dependency>
```

2. Configure `application.properties`:

```properties
spring.application.name=ollama-huggingface-gguf
spring.main.web-application-type=NONE

# Ollama chat model
spring.ai.ollama.chat.options.model=hf.co/bartowski/gemma-2-2b-it-GGUF

# Ollama embedding model
spring.ai.ollama.embedding.options.model=hf.co/nomic-ai/nomic-embed-text-v1-GGUF

# Ollama auto-pull model configuration
spring.ai.ollama.init.pull-model-strategy=always
spring.ai.ollama.init.timeout=15m
spring.ai.ollama.init.max-retries=2
```

### Notes
- `spring.ai.ollama.chat.options.model` and `spring.ai.ollama.embedding.options.model` are used to set the Hugging Face GGUF model names following the naming convention: `hf.co/{username}/{repository}`
- The `pull-model-strategy=always` enables automatic model pulling. For production, it's recommended to pre-download the models to avoid delays.


## Usage

The main application class demonstrates how to use both chat completion and embedding:

```java
    @Bean
    public CommandLineRunner run(ChatClient.Builder builder,       EmbeddingModel embeddingModel) {
        var chatClient = builder.build();

        return args -> {
            // Chat
            var response = chatClient                    
                    .prompt("Tell me a joke")
                    .call()
                    .chatResponse();

            logger.info("Question answer: " + response);

            // Embedding
            var embedding = embeddingModel.embed("The World is Big and Salvation Lurks Around the Corner");
            logger.info("Embedding dimensions: " + embedding.length);
        };
    }
```

## References

- [Spring AI Ollama Chat](https://docs.spring.io/spring-ai/reference/api/chat/ollama-chat.html)
- [Spring AI Ollama Embedding](https://docs.spring.io/spring-ai/reference/api/embeddings/ollama-embeddings.html)


