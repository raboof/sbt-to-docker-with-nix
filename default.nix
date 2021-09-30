{ pkgs ? import <nixpkgs> {} }:

with pkgs;

dockerTools.buildImage {
  name = "nix-jre";
  tag = "latest";
  runAsRoot = ''
    # to make the child image find the root user
    ${dockerTools.shadowSetup}
    # so sbt-native-packager can create a homedir
    #mkdir /home
    # so sbt-native-packager can find them
    mkdir -p /usr/bin
    ln -s /sbin/env /usr/bin/env
  '';
  contents = [
    # Not so minimal:
    # python, gtk, two versions of icu4c, /lib/modules
    (jre_minimal.override {
      jdk = jdk11_headless;
      modules = [
        # For reading the logback config
        "java.xml"
        # For slf4j
        "java.naming"
        # For sun.misc.Unsafe
        "jdk.unsupported"
        # For AkkaServerlessRunner.logJvmInfo
        "java.management"
      ];
    })
    # for chmod in sbt-native-packager
    coreutils
    # for addgroup in sbt-native-packager
    busybox
    # for bash in the sbt-native-packager start script
    bash
  ];
}
