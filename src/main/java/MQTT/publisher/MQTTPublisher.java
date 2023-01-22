package MQTT.publisher;

import Operazioni.Attuatore;
import Operazioni.AziendaAgricola;
import Operazioni.Misura;
import OperazioniDao.GestioneAttuatore;
import OperazioniDao.GestioneAziendaAgricola;
import OperazioniDao.GestioneProgrammaIrrig;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;


import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Sample publisher for MQTT. It uses the Eclipse Paho library and Mosquitto as a broker.
 * Mosquitto is expected to be installed and launched locally
 * (public test servers are available, however).
 *
 * It connects to the Mosquitto broker, set up a Last Will and Testament for the connection,
 * and publish a sample temperature value (i.e., a string equal to "20 C") on a specific topic.
 *
 * @author <a href="mailto:luigi.derussis@uniupo.it">Luigi De Russis</a>
 * @version 1.1 (21/05/2019)
 */
public class MQTTPublisher {
    GestioneAttuatore attuatoreDao = new GestioneAttuatore();
    GestioneAziendaAgricola aziendaDao = new GestioneAziendaAgricola();
    GestioneProgrammaIrrig programmaDao = new GestioneProgrammaIrrig();
    // init the client
    private MqttClient client;
    MemoryPersistence persistence = new MemoryPersistence();
    private final int HOT_TEMPERATURE_THRESHOLD = 20;
    private final int COLD_TEMPERATURE_THRESHOLD = 15;
    private final int ARTIFICIAL_BRIGHTNESS_THRESHOLD = 30;

    /**
     * Constructor. It generates a client id and instantiate the MQTT client.
     */
    public MQTTPublisher() {

        // the broker URL
        String brokerURL = "tcp://localhost:1883";

        // A randomly generated client identifier based on the user's login
        // name and the system time
        String clientId = MqttClient.generateClientId();

        try {
            // create a new MQTT client
            client = new MqttClient(brokerURL, clientId, persistence);

        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    /**
     * The method to start the publisher. Currently, it sets a Last Will and Testament
     * message, open a non persistent connection, and publish a temperature value
     */
    public void start() {
        try {
            MqttConnectOptions options = new MqttConnectOptions();
            // persistent, durable connection
            options.setCleanSession(false);
            options.setUserName("esp32");
            options.setPassword(new char[]{'2', '0', '1', '0', '2', '0', '1', '7', 't', 'o', 'm'});

            // connect the publisher to the broker
            client.connect(options);

        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    /**
     * @throws MqttException
     */
    public void publishMeasurement(Misura misura) throws MqttException {
        String topicRaw = "/aziende/" + misura.getAziendaAgricolaId() + "/attuatori/" + misura.getTipo();
        AziendaAgricola azienda = aziendaDao.getAziendaAgricola(misura.getAziendaAgricolaId());

        MqttTopic topic = client.getTopic(topicRaw);
        Attuatore attuatore = attuatoreDao.getAttuatoreOfLocal(misura.getAziendaAgricolaId(), misura.getTipo());

        String message = "";

        if(attuatore.getManual().equals("manuale")){
            if(attuatore.getDescrizione().equals("rinfrescante.") && misura.getTipo().equals("temperatura")){
                try {
                    int needTemperature = Integer.valueOf(attuatore.getStato());
                    if(needTemperature < Integer.valueOf(misura.getMisurazioni())) message = "irrigazione";
                    else if(needTemperature > Integer.valueOf(misura.getMisurazioni())) message = "riscaldamento";
                } catch(Exception e ){
                    message = "off";
                }
            }
        } else { // Automatico

            if (misura.getMisurazioni().equals("temperatura")) {
                if(azienda.getTipo().equals("serra") && !programmaDao.agguinto(misura.getAziendaAgricolaId(), Date.valueOf(LocalDate.now()).toString(), LocalDateTime.now().getHour())) {
                    attuatoreDao.updateStato("off", attuatore.getId());
                    message = "off";
                } else if (Integer.valueOf(misura.getMisurazioni()) > HOT_TEMPERATURE_THRESHOLD){
                    attuatoreDao.updateStato("aria fredda.", attuatore.getId());
                    message = "irrigazione";
                }
                else if (Integer.valueOf(misura.getMisurazioni()) < COLD_TEMPERATURE_THRESHOLD) {
                    attuatoreDao.updateStato("aria calda.", attuatore.getId());
                    message = "riscaldamento";
                }
                else {
                    attuatoreDao.updateStato("off", attuatore.getId());
                    message = "off";
                }
            } else if (misura.getTipo().equals("luminosita")) {
                if (Integer.valueOf(misura.getMisurazioni()) > ARTIFICIAL_BRIGHTNESS_THRESHOLD){
                    attuatoreDao.updateStato("off", attuatore.getId());
                    message = "off";
                }
                else{
                    attuatoreDao.updateStato("on", attuatore.getId());
                    message = "on";
                }
            }
        }
        // publish the message on the given topic
        topic.publish(new MqttMessage(message.getBytes()));

        // debug
        System.out.println("Published message on topic '" + topic.getName() + "': " + message);
    }

}
