## Spring Boot + Redis Caching
- Run http://localhost:8080/swagger-ui/index.html
- Customer Service using H2 database
    * http://localhost:8080/h2-console
- Required redis (ex. Install on Amazon Linux)
```shell
sudo yum update
sudo yum install -y make gcc
mkdir redis && cd redis && wget https://download.redis.io/redis-stable.tar.gz 
tar -xzvf redis-stable.tar.gz && cd redis-stable/
make distclean  # for clean build
make
sudo make install

# start
redis-server --daemonize yes
redis-cli ping        ## output is PONG
redis-cli -v

# check process & port
ps aux | grep redis-server
```
