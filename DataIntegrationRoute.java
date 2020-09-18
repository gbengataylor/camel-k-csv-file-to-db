// camel-k: trait=quarkus.enabled=true dependency=mvn:org.apache.commons:commons-dbcp2:2.7.0 dependency=mvn:org.postgresql:postgresql:9.4.1212 
// trait=quarkus.enabled=true dependency=mvn:org.apache.commons:commons-dbcp2:2.7.0 dependency=mvn:org.postgresql:postgresql:9.4.1212 

//kamel run DataIntegrationRoute.java  --dependency=mvn:org.apache.commons:commons-dbcp2:2.7.0 --dependency=mvn:org.postgresql:postgresql:9.4.1212 --name credit-card-generator --dev

// used upstream camel-k for faster build time..also Camel 3 which allows for updating the registry
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.camel.BindToRegistry;


public class DataIntegrationRoute extends RouteBuilder {

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
        String input = (String) exchange.getIn().getBody();
       // System.out.println("Input to be persisted : " + input);
        String insertQuery = "INSERT INTO credit_card_transactions values ( " + input+")";
       // System.out.println("Insert Query is : " + insertQuery);
        exchange.getIn().setBody(insertQuery);
         })
      .log("${body}")
       .to("jdbc:myDataSource");

    }
}