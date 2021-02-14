set -ex
content_address="$(sha256sum eclipse-formatter.xml | awk '//{print $1}')"

curl \
    -F "content=@eclipse-formatter.xml" http://localhost:8080/api/v1/objects/upload/${content_address}