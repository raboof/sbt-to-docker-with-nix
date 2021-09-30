This repo contains a couple of branches with different experiments
using Nix to package an sbt project into a Docker container.

## A Nix base image and sbt-native-packager

[Here](https://github.com/raboof/sbt-to-docker-with-nix/tree/nix-base-image)
we use Nix to create a bespoke base image, and use that with
[sbt-native-packager](https://github.com/sbt/sbt-native-packager) to produce
the actual docker image (the latter 'outside' of Nix).

Selecting only the parts of the JRE we really need in the base image works
nicely here. Unfortunately, it requires quite some tweaks, both to the
[build.sbt](https://github.com/raboof/sbt-to-docker-with-nix/blob/nix-base-image/build.sbt) and the
[default.nix](https://github.com/raboof/sbt-to-docker-with-nix/blob/nix-base-image/default.nix)
to align the base image and the expectations of sbt-native-packager. Also,
some dependencies need to be added to the image just because they are
needed while *building* the final docker image, and should not be needed
at run time. It would be nicer if the 'build-time' and the 'run-time'
dependencies were separated better.

## sbt-assembly and Nix

[Here](https://github.com/raboof/sbt-to-docker-with-nix/tree/nix-image-with-assembly)
we create the final docker image directly with Nix, using a fairly straightforward
[default.nix](https://github.com/raboof/sbt-to-docker-with-nix/blob/nix-image-with-assembly/default.nix)
referring to an `assembly.jar` fat jar created regularly with `sbt assembly`. Creating the assembly requires [some tweaks](https://github.com/raboof/sbt-to-docker-with-nix/blob/nix-image-with-assembly/build.sbt) but this is fairly straightforward.

This has all the advantages of the previous approach, requires less customizations,
and avoids the problem of including 'build-time' dependencies in the base image.
Creating the assembly still happens 'outside of Nix'.

## Inside Nix:

Next up!

# References

* [gvolpe/sbt-nix.g8](https://github.com/gvolpe/sbt-nix.g8) has some more pointers on how to use sbt in Nix
