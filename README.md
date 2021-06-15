Hi,

I have used spring boot to to implement the backend with:
    - h2 in-memory database
    - spring data jpa
    - MapStruct for the mapping between dto and model
    - Mockito for testing
    - Wiremock for mocking the currency client  
    - lombok
    
The frontend is pretty much basic as I didn't have any previous knowledge with react and I had to follow some tutorials to implement it.
Integration with fixer.io is only by paid subscription, so I had to disaple and it and limit the currency for euros only but I added some integration
testing with wiremock to test the client.

Please go to env folder and run "docker-compose -f docker-compose.yml up -d"
    -frontend on http://localhost:3000
    -backend on http://localhost:8080
    
for swagger documention please open http://localhost:8080/swagger-ui.html
