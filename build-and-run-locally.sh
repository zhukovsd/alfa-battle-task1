#!/bin/sh -e

docker image build -t zhukovsd/alfabattle:task1 .
docker container run -p 8080:8080 zhukovsd/alfabattle:task1