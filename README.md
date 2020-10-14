TINY WEATHER BULLETTIN
    
   Step 1
    
    Insert into resurces the file application-local.yaml and application-test.yaml 
    with your apikey of OpenWeathter->
        openweather:
            appId:
    
    
   Step2
   
    Create docker image: 
    [docker build . -t {image-name}]
    
   Step3
    
    Running docker image created
    [docker run -d -p 8080:8080 -e SPRING_PROFILES_ACTIVE=local -e logging.level.root=ERROR {image-name}]
    
   Step4
    
    Access to swagger for the full documentation about the api and try the api, the name is three-days-forecast-controller
    [http://0.0.0.0:8080/swagger-ui.html#/three-days-forecast-controller]
    
            
    
    
         