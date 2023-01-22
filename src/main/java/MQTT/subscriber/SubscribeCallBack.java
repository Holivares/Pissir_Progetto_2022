package MQTT.subscriber;

import MQTT.publisher.MQTTPublisher;
import Operazioni.Misura;
import OperazioniDao.GestioneMisura;
import OperazioniDao.GestioneSensore;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;


import java.time.LocalDateTime;

public class SubscribeCallBack implements MqttCallback {

    private MQTTPublisher publisher;

    GestioneMisura misuraDao = new GestioneMisura();
    GestioneSensore sensoreDao = new GestioneSensore();

    public SubscribeCallBack(MQTTPublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public void connectionLost(Throwable cause) {
        // what happens when the connection is lost. We could reconnect here, for example.
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        manageMessage(topic, message);
    }

    private void manageMessage(String topic, MqttMessage message) throws Exception{
        System.out.println("Message arrived for the topic '" + topic + "': " + message.toString());

        int first = topic.indexOf('/', 1); // /aziende/ <-
        int second = topic.indexOf('/', first + 1); // /aziende/AziendaAgricolaId/ <-
        int third = topic.indexOf('/', second + 1); // /aziende/AziendaAgricolaId/sensori/ <-

        String tipo = topic.substring(third + 1); // /aziende/AziendaAgricolaId/sensori/ ??? <-

        String misurazione = message.toString();
        String now = LocalDateTime.now().toString();
        int aziendaAgricolaId= getIdaziendaAgricola(topic);
        int sensoreId = sensoreDao.getSensoreOfAziende(aziendaAgricolaId, tipo).getId();
        Misura misura = new Misura(tipo, misurazione, now, sensoreId, aziendaAgricolaId);

        misuraDao.addMisura(misura);
        publisher.publishMeasurement(misura);
    }

    private int getIdaziendaAgricola(String topic){
        int i = topic.indexOf("/", 1);
        int idAziendaAgricola = Integer.valueOf(topic.substring(i+1, i+2));
        return idAziendaAgricola;
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        // called when delivery for a message has been completed, and all acknowledgments have been received
        // no-op, here
    }
}