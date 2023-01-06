haproxy
==================


## Install on Mac
```shell
brew install haproxy
brew info haproxy
```

### 설정
/opt/homebrew/etc/haproxy.cfg
```shell
global
    log 127.0.0.1   local0
    log 127.0.0.1   local1 debug
    #log loghost    local0 info
    maxconn 4096
    #chroot /usr/share/haproxy
    #daemon
    #debug
    #quiet

  defaults
    log     global
    mode    http
    option  httplog
    option  dontlognull
    retries 3
    option redispatch
    maxconn 2000
    timeout connect      5000
    timeout client      50000
    timeout server      50000

  frontend localnodes
    bind *:8000
    mode http
    default_backend nodes

  backend nodes
    mode http
    balance roundrobin
    option forwardfor
    http-request set-header X-Forwarded-Port %[dst_port]
    http-request add-header X-Forwarded-Proto https if { ssl_fc }
    option httpchk HEAD / HTTP/1.1\r\nHost:localhost
    server server1 192.168.1.135:3000 check  
```
### 설정 검증
```shell
/opt/homebrew/opt/haproxy/bin/haproxy -f /opt/homebrew/etc/haproxy.cfg -c
```

### 실행
```shell
/opt/homebrew/opt/haproxy/bin/haproxy -f /opt/homebrew/etc/haproxy.cfg
```