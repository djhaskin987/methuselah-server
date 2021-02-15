set -ex


content_address="$(sha256sum eclipse-formatter.xml | awk '//{print $1}')"
curl -v --head http://localhost:8080/api/v1/objects/abc
curl -v --head 
    http://localhost:8080/api/v1/objects/${content_address}


curl -v -X PUT \
    -F "content=@eclipse-formatter.xml" http://localhost:8080/api/v1/objects/${content_address}

