mvn clean package
podman build -f src/main/docker/Dockerfile.jvm -t quarkus/train-ceq-app-jvm --platform linux/arm64/v8 . 
podman tag quarkus/train-ceq-app-jvm:latest quay.io/mouachan/train-ceq-app-arm64:latest    
podman push quay.io/mouachan/train-ceq-app-arm64:latest 