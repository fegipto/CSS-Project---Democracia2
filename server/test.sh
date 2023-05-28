docker build -t java-docker --target test .
docker run -it --rm --name springboot-test java-docker
