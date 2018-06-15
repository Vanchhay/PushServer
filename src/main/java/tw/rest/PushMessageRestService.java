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

	private final static String TOPIC = "kafka_topic";
	private final static String BOOTSTRAP_SERVERS = "localhost:9092";

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
		runProducer(pm);
		return Response.ok(pm).build();
	}

	private static Producer<Long, String> createProducer() {
		Properties props = new Properties();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
		props.put(ProducerConfig.CLIENT_ID_CONFIG, "client.id");
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		return new KafkaProducer<>(props);
	}

	static void runProducer(PushMessage pm) {
		final Producer<Long, String> producer = createProducer();
		try {
			final ProducerRecord<Long, String> record = new ProducerRecord<>(TOPIC, pm.sender);
			producer.send(record, (metadata, exception) -> {
				if (metadata != null) {
					System.out.printf("sent record(key=%s value=%s) " +
									"meta(partition=%d, offset=%d) \n",
							record.key(), record.value(), metadata.partition(),
							metadata.offset());
				} else {
					exception.printStackTrace();
				}
			});
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			producer.flush();
			producer.close();
		}
	}
}