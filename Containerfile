# podman build -t twasdiag .
# podman run --rm -p 9080:9080 -p 9443:9443 -t twasdiag
FROM icr.io/appcafe/open-liberty:full-java8-ibmjava-ubi

COPY --chown=default:root twasdiag-ear/src/main/liberty/config/server.xml /config/server.xml
COPY --chown=default:root twasdiag-ear/target/twasdiag.ear /config/dropins

RUN configure.sh
