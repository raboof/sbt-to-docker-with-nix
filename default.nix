{ pkgs ? import <nixpkgs> {} }:

with pkgs;

dockerTools.buildLayeredImage {
  name = "my-akkaserverless-project";
  tag = "latest";
  contents = [
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
  ];
  config = {
    Cmd = [];
    # Needs to be '/' because otherwise the JVM gets confused about java.home being empty
    # and looking for the modules in `lib/modules`
    WorkingDir = "/";
    Entrypoint = [ "java" "-jar" ./assembly.jar ];
  };
}
