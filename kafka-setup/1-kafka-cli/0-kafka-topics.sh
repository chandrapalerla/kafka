# Replace "kafka-topics.bat" 
# by "kafka-topics" or "kafka-topics.bat" based on your system # (or bin/kafka-topics.bat or bin\windows\kafka-topics.bat if you didn't setup PATH / Environment variables)

#Start Zookeeper
zookeeper-server-start.bat .\config\zookeeper.properties

#Start Kafka Server
kafka-server-start.bat .\config\server.properties


kafka-topics.bat 

kafka-topics.bat --bootstrap-server localhost:9092 --list 

kafka-topics.bat --bootstrap-server localhost:9092 --topic first_topic --create

kafka-topics.bat --bootstrap-server localhost:9092 --topic first_topic --create --partitions 3

kafka-topics.bat --bootstrap-server localhost:9092 --topic first_topic --create --partitions 3 --replication-factor 2

# Create a topic (working)
kafka-topics.bat --bootstrap-server localhost:9092 --topic first_topic --create --partitions 3 --replication-factor 1

# List topics
kafka-topics.bat --bootstrap-server localhost:9092 --list 

# Describe a topic
kafka-topics.bat --bootstrap-server localhost:9092 --topic first_topic --describe

# Delete a topic 
kafka-topics.bat --bootstrap-server localhost:9092 --topic first_topic --delete
# (only works if delete.topic.enable=true)