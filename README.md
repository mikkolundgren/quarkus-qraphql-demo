# quarkus-graphql Project

Simple demo of using Smallrye GraphQL api.

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Graphql url:s

- queries: http://localhost:8080/graphql/
- UI:  http://localhost:8080/q/graphql-ui
- schema: http://localhost:8080/graphql/schema.graphql


Example queries:

```
{
	allAlbums {
    artist
    title
    year
  }
}
```


```
query albumsByTitle {
    al0: albumsByTitle(title:"Elvis") {
      title
      artist
      year
    }
    al1: albumsByTitle(title: "Black") {
      title
      artist
      year
    }
  }
```

```
mutation createAlbum {
    createAlbum(album: {
        title: "foo",
        artist: "fighter"
        year: 2022
        id: 666
    }
    )
    {
        title
        artist
        id
    }
}
```

```
mutation deleteAlbum {
    deleteAlbum(id: 1044) {
        title
        year
        artist
        id
    }
}
```

## K8s stuff

Leave minikube extension commented out in pom.xml and set kube context to docker-desktop for deploying to docker-desktop cluster:
```
# switch to docker-desktop
kubectl config use docker-desktop

# build and deploy
mvn clean package -Dquarkus.kubernetes.deploy=true

# test the service
curl localhost:30585/q/health/live
```

Running in minikube cluster with minikube extension and docker driver. Uncomment the minikube etension in pom.xml.

```
# set the needed env variables for minikubes docker daemon
eval $(minikube -p minikube docker-env)

# make sure minikube is running
minikube service list

# build and deploy
mvn clean package -Dquarkus.kubernetes.deploy=true

# start minikube tunneling...
minikube service --url quarkus-graphql-demo

# test the service
curl <URL>:<PORT>/q/health/live
```

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using: 
```shell script
./mvnw package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/quarkus-graphql-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.
