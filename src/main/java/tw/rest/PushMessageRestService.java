package tw.rest;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.LongSerializer;

import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.GET;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Response;


@Path("/pushmessage")
public class PushMessageRestService {

	private final static String producerPropsFile = "producer.properties";
	private final static Logger LOGGER = LoggerFactory.getLogger(PushMessageRestService.class);

	private static String TOPIC;
	private static InputStream inputStream;
	private static Producer<Long, String> producer;

	static {
		try {
			Properties properties = new Properties();
			try{
				inputStream = PushMessageRestService.class.getClassLoader().getResourceAsStream(producerPropsFile);
				properties.load(inputStream);
				TOPIC = properties.getProperty("TOPIC");
			}catch(IOException e){
				e.printStackTrace();
			}finally {
				inputStream.close();
			}
			properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getProperty("BOOTSTRAP_SERVERS"));
			properties.put(ProducerConfig.CLIENT_ID_CONFIG, properties.getProperty("CLIENT_ID"));
			properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());
			properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
			LOGGER.info("===========   Creating producer  ================");
			producer = new KafkaProducer<>(properties);
			LOGGER.info("===========   PRODUCER CREATED  ================");
		} catch (IOException e) {
			e.printStackTrace();
		}
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
			exception.printStackTrace();
		});
		LOGGER.info("===========   Message Sent  ================");

		LOGGER.info("===========   PushMessage Info  ================");
		LOGGER.info("Topic : {} , Sender: {} , Urgent: {} , Text: {}", pm.getTopic(), pm.getSender(), pm.getUrgent(), pm.getText() );
		LOGGER.info("===========   End PushMessage Info  ================");
		return Response.ok(pm).build();
	}
}