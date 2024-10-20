package springai.example.ollamahf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringAiOllamaHuggingFaceApplication {

	private final Logger logger = LoggerFactory.getLogger(SpringAiOllamaHuggingFaceApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringAiOllamaHuggingFaceApplication.class, args);
	}

	@Bean
	public CommandLineRunner run(ChatClient.Builder builder, EmbeddingModel embeddingModel) {
		
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

}
