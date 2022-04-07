FROM maven:latest
RUN mkdir /musicapi
WORKDIR /musicapi
COPY . .
EXPOSE 8080
CMD ["mvn","spring-boot:run"]
