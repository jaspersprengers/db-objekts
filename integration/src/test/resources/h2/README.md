# Running an H2 server on the command line

```bash
 java -cp h2*.jar org.h2.tools.Server 

 ```

Now you can connect to the server using the url `jdbc:h2:tcp://localhost:9092/~/test`
No authorization required.
