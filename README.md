# Getting Started
RAG PoC


Import the code base

Configure the below props in application.properties


Set the OPENAPI key 


Set PDF file to be read 

run the app 

mvn spring-boot:run

Start chatting

curl -i -X POST -H "Content-Type: application/json" -d "{\"question\":\"What is the Terminal for my flight \"}" http://localhost:8080/chat

Sample response

{"answer":"The terminal at London Heathrow Airport for your flight is Terminal 2."}

