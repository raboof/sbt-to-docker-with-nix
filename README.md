Example project to create a Docker image with Nix

First create a fat jar 'outside' of nix:

```
sbt clean assembly
cp target/scala-*/*-assembly-*.jar assembly.jar
```

Then build and load the docker image:

```
docker load < $(nix-build build.nix)
```
