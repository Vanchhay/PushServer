package tw.rest;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.GET;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Response;


@Path("/pushmessage")
public class PushMessageRestService {

	private final static String TOPIC = "kafkaTopic";
	private final static String BOOTSTRAP_SERVERS = "localhost:9092";
	private final Properties DEFAULT_PROPS = setDefaultProps(BOOTSTRAP_SERVERS);

	private Producer<Long, String> producer = setProducer();

	public Producer<Long, String> setProducer(){
		return new KafkaProducer<Long, String>(DEFAULT_PROPS);
	}

	public Properties setDefaultProps(String bootstrapServer, String clientID) {
		Properties props = new Properties();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
		props.put(ProducerConfig.CLIENT_ID_CONFIG, clientID);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		return props;
	}

	public Properties setDefaultProps(String bootstrapServer) {
		Properties props = new Properties();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		return props;
	}

	@GET
	@Produces("application/json")
	@Consumes("application/json")
	public PushMessage getPushMessageDetail() {
		PushMessage pushMessage = new PushMessage();
		return pushMessage;
	}

	@POST
	@Path("post")
	@Produces("application/json")
	@Consumes("application/json")
	public Response addPushMessage(PushMessage pm) {
		// Add PushMessage to Producer
		producer.send(new ProducerRecord<>(TOPIC, pm.topic),(metadata, exception)->{
			System.out.println("Metadata => "+metadata.topic());
		});
		return Response.ok().build();
	}

	/**
	 * Stream passing PushMessage object to Kafka
	 **/
//	boolean runProducer(PushMessage pm) {
//		try {
//			producer.send(new ProducerRecord<>(TOPIC, pm.text));
//		}catch(Exception e){
//			e.printStackTrace();
//			return false;
//		}finally {
//			producer.flush();
//			producer.close();
//		}
//		return true;
//	}
}