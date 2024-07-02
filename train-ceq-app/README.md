# Demo Stop The Crazy Train - Lego Train Camel post processing app

![lego](https://www.lego.com/cdn/cs/set/assets/blt95604d8cc65e26c4/CITYtrain_Hero-XL-Desktop.png?fit=crop&format=webply&quality=80&width=1600&height=1000&dpr=1)

# Train-CEQ-App

Train-CEQ-App is a module in a larger system that is responsible for post-processing data. It takes the raw predictions made by the Intelligent-Train module, processes them, and transforms them into actionable insights.

## How it works

Train-CEQ-App receives prediction data from the Intelligent-Train module via MQTT. It processes this data, performing post-processing operations such as filtering, aggregation, and interpretation to transform the raw predictions into actionable insights. These insights are used to make decisions to control the train service. These decisions are then sent to the Train-Controller module.

Train-CEQ-App receives prediction data from the Intelligent-Train module via MQTT. It processes this data, performing post-processing operations such as filtering, aggregation, and interpretation to transform the raw predictions into actionable insights. These insights, along with the original images, are packaged as a CloudEvent and sent to the Train-Monitoring-App module via Kafka. The Train-Monitoring-App module uses this information for monitoring and visualization purposes. Decisions are sent to the Train-Controller module through MQTT.



## Prerequisites

- **MQTT Broker**: Train-CEQ-App uses MQTT for messaging. You need to have an MQTT broker running and accessible to Train-CEQ-App. The MQTT broker's URL should be specified in the `mqtt.broker.url` property in the `application.properties` file.

## Related Modules

Train-CEQ-App is part of a larger system that includes the following modules:

- **Capture-App**: This module captures video and sends images to Intelligent-Train.
- **Intelligent-Train**: This module uses machine learning algorithms to make decisions based on the data received from the Capture-App.
- **Train-Monitoring-App**: This module receives the CloudEvent from Train-CEQ-App via Kafka and uses this information for monitoring and visualization purposes.
- **Train-Controller**: This module receives decisions from Train-CEQ-App and controls the operation of the train accordingly through bluetooth.

## Prerequisites

- **MQTT Broker**: Train-CEQ-App uses MQTT for messaging. You need to have an MQTT broker running and accessible to Train-CEQ-App. The MQTT broker's URL should be specified in the `mqtt.broker.url` property in the `application.properties` file.
- **Kafka**: Train-CEQ-App uses Kafka to send data to the Train-Monitoring-App module. You need to have a Kafka cluster running and accessible to Train-CEQ-App. The Kafka cluster's URL should be specified in the `kafka.bootstrap.servers` property in the `application.properties` file.

## Dependencies

Train-CEQ-App has the following dependencies:

- **Quarkus**: A Kubernetes-native Java stack tailored for GraalVM and OpenJDK HotSpot.
- **Apache Kafka Client**: A client library for Apache Kafka.
- **Eclipse Paho MQTT Client**: A Java client library for MQTT.
- **CloudEvents SDK**: A Java SDK for CloudEvents.


## How to run

1. Clone the repository: `git clone https://github.com/Demo-AI-Edge-Crazy-Train/train-ceq-app.git`
2. Navigate to the project directory: `cd train-ceq-app`
3. Update the `application.properties` file with the appropriate values.
4. Run the application: `./mvnw compile quarkus:dev`

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.