{ pkgs ? import <nixpkgs> {} }:

with pkgs;

dockerTools.buildImage {
  name = "nix-jre";
  tag = "latest";
  contents = [
    # Not so minimal:
    # python, gtk, two versions of icu4c, /lib/modules
    (jre_minimal.override {
      jdk = jdk11_headless;
    })
  ];
}
