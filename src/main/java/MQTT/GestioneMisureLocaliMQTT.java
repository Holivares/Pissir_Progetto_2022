package MQTT;

import MQTT.publisher.MQTTPublisher;
import MQTT.subscriber.MQTTSubscriber;

public class GestioneMisureLocaliMQTT {

    // init the client
    private MQTTPublisher publisher;
    private MQTTSubscriber subscriber;

    public GestioneMisureLocaliMQTT() {
        publisher = new MQTTPublisher();
        subscriber = new MQTTSubscriber(publisher);
    }

    /**
     * The method to start the subscriber. It listen to all the homestation-related topics.
     */
    public void MQTTInit() {
        subscriber.start();
        publisher.start();
    }
}