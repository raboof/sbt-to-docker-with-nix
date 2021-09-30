Example project packaging an sbt project in docker with Nix

First create the base image with Nix: `nix-build` will use the
specification in [`default.nix`](./default.nix) to build the image:

```
docker load < $(nix-build)
```

Then use sbt-native-packager to create the image based on that:

```
sbt docker:publishLocal
```
