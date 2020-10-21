// camel-k: trait=quarkus.enabled=true dependency=mvn:org.apache.commons:commons-dbcp2:2.7.0 dependency=mvn:org.postgresql:postgresql:9.4.1212 
// trait=quarkus.enabled=true dependency=mvn:org.apache.commons:commons-dbcp2:2.7.0 dependency=mvn:org.postgresql:postgresql:9.4.1212 

//kamel run DataIntegrationOutboxRoute.java  --dependency=mvn:org.apache.commons:commons-dbcp2:2.7.0 --dependency=mvn:org.postgresql:postgresql:9.4.1212 --name credit-card-generator --dev

// used upstream camel-k for faster build time..also Camel 3 which allows for updating the registry
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.camel.BindToRegistry;
import java.time.Instant;


public class DataIntegrationOutboxRoute extends RouteBuilder {

    @BindToRegistry("myDataSource")
    public BasicDataSource basicDataSource() {
        BasicDataSource ds = new BasicDataSource();
        ds.setUsername("sa");
        ds.setDriverClassName("org.postgresql.Driver");
        ds.setPassword("sa");
        ds.setUrl("jdbc:postgresql://postgresql:5432/creditcard");
        return ds;    
    }
    @Override
    public void configure() throws Exception {
        // from("timer:java?period=1000")
        // .setHeader("example")
        //   .constant("Java")
        // .setBody()
        //   .simple("Hello World! Camel K route written in ${header.example}.")
        // .to("log:info");


    from("timer:jhucsse?repeatCount=10000")
     .toD("https://gitlab.com/gbengataylor/fraud-detection-tutorial/-/raw/master/data/creditcardv.csv?httpMethod=GET")
     .split().tokenize("\n", 1, true)
     .process(exchange -> {
        Instant timestamp = Instant.now();
        Instant current_date_copy=Instant.ofEpochMilli(timestamp.toEpochMilli());
        // terrible code everywhere
        String aggregate_id = "creditcard."+current_date_copy.toString();
        String input = (String) exchange.getIn().getBody();
       // System.out.println("Input to be persisted : " + input);
       // transaction
        String insertQuery = "INSERT INTO cct values ( " + input+");";
        // outbox event
        String outBoxInsert = "INSERT INTO outbox_events (aggregate_id, aggregate_type, event_type, payload_string, payload) values ( \'"+ aggregate_id +"\', \'creditcard\',  \'transaction\', \'" + input + "\' , \' { \"strData\" : \" " + input + " \"} \'  );";
       // System.out.println("Insert Query is : " + insertQuery);
       //System.out.println("Outbox Query is : " + outBoxInsert);
        exchange.getIn().setBody(insertQuery+outBoxInsert);
         })
      .log("${body}")
      .to("jdbc:myDataSource");
    }
}