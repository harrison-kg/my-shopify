# my-shopify

Springboot appplication for a basic customer-product service

application runs on port 8084

* Deployment to docker
Open the application directory, navigate to the DockerFile
------------------------------------------------------
# build image

docker build -f Dockerfile -t myapp/my-shopify-app
____________________________________________________
Run docker container

docker run -p 8084:8084 myapp/my-shopify-app . 
____________________________________________________


# prometheus is also added for metrics
pull prometheus on docker using
------------------------------------------------------------
docker pull prom/prometheus 

run prometheus pointing at the prometheus.yml file inside the resource foulder
docker run -p 9090:9090 –v /c:/user/myshop/src/main/resources/prometheus.yml prom/prometheus
endpoint http://localhost:9090/

# H2 in memory database 
--------------------------------------------------------------------
 H2 console available at '/h2-ui'. Database available at 'jdbc:h2:mem:shopify'
 <host>:port/h2-ui

