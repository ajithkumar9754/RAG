package com.ajith.ai.rag;

import java.io.File;
import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingClient;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;

@SpringBootApplication
public class RagApplication {

	public static void main(String[] args) {
		SpringApplication.run(RagApplication.class, args);
	}

	
	 @Value("${app.vectorstore.path:c:/tmp/vectorstore.json}")
	    private String vectorStorePath;

	    @Value("${app.resource}")
	    private Resource pdfResource;

	    @Bean
	    SimpleVectorStore storeVectorStore(EmbeddingClient embeddingClient) {
	    	System.out.println("Initialize the vector store");
	        SimpleVectorStore simpleVectorStore = new SimpleVectorStore(embeddingClient);
	        File vectorStoreFile = new File(vectorStorePath);
	        if (vectorStoreFile.exists()) { 
	            simpleVectorStore.load(vectorStoreFile);
	        } else { 
	            TikaDocumentReader documentReader = new TikaDocumentReader(pdfResource);
	            List<Document> documents = documentReader.get();
	            TextSplitter textSplitter = new TokenTextSplitter();
	            List<Document> splitDocuments = textSplitter.apply(documents);
	            simpleVectorStore.add(splitDocuments);
	            simpleVectorStore.save(vectorStoreFile);
	        }
	        return simpleVectorStore;
	    }
	    
}
