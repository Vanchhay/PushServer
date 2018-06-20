package tw.rest;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.GET;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;


@Path("/pushmessage")
public class PushMessageRestService extends Application {

	private final static String TOPIC = "kafkaTopic";
	private final static String BOOTSTRAP_SERVERS = "localhost:9092";
	private final static Logger LOGGER = LoggerFactory.getLogger(PushMessageRestService.class);
	private Properties props = setProps();
	private Producer<Long, String> producer = new KafkaProducer<>(props);

	Properties setProps(){
		LOGGER.info("===========   SetProps Execution  ================");
		Properties properties = new Properties();
		properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
		properties.put(ProducerConfig.CLIENT_ID_CONFIG, "TRADE WORK");
		properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());
		properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		LOGGER.info("===========   Creating Producer  ================");
		return properties;
	}

	@GET
	@Produces("application/json")
	@Consumes("application/json")
	public PushMessage getPushMessageDetail() {
		PushMessage pushMessage = new PushMessage();
		return pushMessage;
	}

	@POST
	@Path("/post")
	@Produces("application/json")
	@Consumes("application/json")
	public Response addPushMessage(PushMessage pm) {
		// Add PushMessage to Producer
		LOGGER.info("===========   Sending message to consumer  ================");
		producer.send(new ProducerRecord<>(TOPIC, pm.topic),(metadata, exception)->{
			System.out.println("Metadata => "+metadata.topic());
		});
		LOGGER.info("===========   Message Sent  ================");

		LOGGER.info("===========   Producer Info  ================");
		LOGGER.info("Server and Port : {}", props.getProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG));
		LOGGER.info("Client ID : {}", props.getProperty(ProducerConfig.CLIENT_ID_CONFIG));
		LOGGER.info("===========   End Producer Info  ================");

		LOGGER.info("===========   PushMessage Info  ================");
		LOGGER.info("Topic : {} , Sender: {} , Urgent: {} , Text: {}", pm.topic, pm.sender, pm.urgent, pm.text );
		LOGGER.info("===========   End PushMessage Info  ================");
		return Response.ok().build();
	}
}