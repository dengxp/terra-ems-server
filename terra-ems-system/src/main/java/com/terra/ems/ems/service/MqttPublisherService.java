package com.terra.ems.ems.service;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@Slf4j
@Service
public class MqttPublisherService {

    @Value("${mqtt.broker.url:tcp://127.0.0.1:1883}")
    private String brokerUrl;

    @Value("${mqtt.client.id:terra-server-publisher}")
    private String clientId;

    @Value("${mqtt.username:}")
    private String username;

    @Value("${mqtt.password:}")
    private String password;

    private MqttClient mqttClient;

    @PostConstruct
    public void init() {
        try {
            mqttClient = new MqttClient(brokerUrl, clientId, new MemoryPersistence());
            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(true);
            options.setAutomaticReconnect(true);
            options.setConnectionTimeout(10);
            if (!username.isEmpty()) {
                options.setUserName(username);
            }
            if (!password.isEmpty()) {
                options.setPassword(password.toCharArray());
            }
            mqttClient.connect(options);
            log.info("MQTT Publisher connected to {}", brokerUrl);
        } catch (Exception e) {
            log.error("Failed to connect MQTT Publisher to {}: {}", brokerUrl, e.getMessage());
        }
    }

    @PreDestroy
    public void destroy() {
        try {
            if (mqttClient != null && mqttClient.isConnected()) {
                mqttClient.disconnect();
                mqttClient.close();
            }
        } catch (Exception e) {
            log.error("Error disconnecting MQTT Publisher", e);
        }
    }

    public void publish(String topic, String payload) {
        if (mqttClient == null || !mqttClient.isConnected()) {
            log.warn("MQTT client not connected. Skipping message to topic: {}", topic);
            return;
        }
        try {
            MqttMessage message = new MqttMessage(payload.getBytes());
            message.setQos(1);
            mqttClient.publish(topic, message);
            log.debug("Published MQTT message to topic {}: {}", topic, payload);
        } catch (Exception e) {
            log.error("Failed to publish MQTT message to {}: {}", topic, e.getMessage());
        }
    }
    
    /**
     * 发送 Gateway 重载配置指令
     * @param gatewayCode 网关编码
     */
    public void notifyGatewayReload(String gatewayCode) {
        String topic = "ems/cmd/" + gatewayCode + "/reload";
        String payload = "{\"action\": \"reload\", \"type\": \"modbus\"}";
        publish(topic, payload);
        log.info("Successfully dispatched config reload command to Gateway: {}", gatewayCode);
    }
}
