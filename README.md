# RepasoParcial2AREP

Archivos y comandos usados en solución parcial

## Generales

1. Dependencias Spark

* Spark
* Spark api
* Spark simple binding

## Implementación sin Docker

1. Instalación Java 17

```
sudo yum install java-17-amazon-corretto-devel
```

2. Descomprimir archivos

```
unzip {nombre-archivo}
```

3. Ejecutar clase java en linux

```
java -cp "target/classes:target/dependency/*" org.example.nameClase
```

## Implementación en Docker


1. DockerFile

```
FROM openjdk:8

WORKDIR /usrapp/bin

ENV PORT 6000

COPY /target/classes /usrapp/bin/classes
COPY /target/dependency /usrapp/bin/dependency

CMD ["java","-cp","./classes:./dependency/*","org.example.nameClase"]
```

2. Docker Compose

```
version: '2'

services:
  log1:
    build:
      context: .
      dockerfile: Dockerfile
    container_name: log1
    ports:
      - "34000:6000"
      
  db:
    image: mongo:3.6.1
    container_name: db
    volumes:
      - mongodb:/data/db
      - mongodb_config:/data/configdb
    ports:
      - 27017:27017
    command: mongod

volumes:
  mongodb:
  mongodb_config:
```

3. Construir y Crear los servicios en Docker

```
docker-compose build 
docker-compose up -d 
```

4. Subir imagenes a docker hube

```
docker tag {nombre-imagen-en-docker-local} {usernameDockerHub}/{nombre-repoDockerHub}
docker login
docker push {usernameDockerHub}/{nombre-repoDockerHub}:latest
```

5. Instalación Docker en linux

```
sudo yum update -y
sudo yum install docker
sudo usermod -a -G docker ec2-user
sudo service docker start
```

6. Correr imagenes de dockerhub en AWS

```
docker run -d -p {puerto}:6000 --name {nombre-aws} {usernameDockerHub}/{nombre-repoDockerHub}
```

7. Verificar contenedores corriendo

```
docker ps
```


