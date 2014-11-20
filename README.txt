See http://neppramod.wordpress.com/2014/11/20/write-a-simple-jini-application/ for  more explanation.

1. Compile (from inside src)

$ javac *.java
$ jar cf simple_message-dl.jar HelloIntf.class

2. Host (outside src)
$ java -jar lib/tools.jar -port 8080 -dir host_this/ -verbose

3. Services (Each separate terminals)
# rmid

$ rmid

# JINI Lookup

$ java -Djava.security.policy=all_rmi_lib/jsk-all.policy -jar all_rmi_lib/start.jar all_rmi_lib/start-transient-reggie.config

4. Server/Client (from inside src)

$ java -Djava.rmi.server.codebase=http://localhost:8080/simple_message-dl.jar -Djava.security.policy=../all_rmi_lib/jsk-all.policy HelloServer

$ java -Djava.security.policy=../all_rmi_lib/jsk-all.policy HelloClient


Lookup service found
Client: From server: Server is sending hello!!
Server: Message from client :I am client, saying hi to server
