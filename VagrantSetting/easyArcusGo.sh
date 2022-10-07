/home/vagrant/arcus/zookeeper/bin/zkServer.sh start

echo "Waiting for 5 seconds..."
sleep 5

cd /home/vagrant/arcus
bin/memcached -E lib/default_engine.so -X lib/syslog_logger.so -X lib/ascii_scrub.so -o 60 -d -v -r -R5 -U 0 -D: -b 8192 -m 100 -p 11211 -c 1000 -t 4 -z localhost:2181
echo "Task Completed"