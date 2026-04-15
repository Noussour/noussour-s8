#!/bin/bash

set -e

echo "🧠 Initializing MongoDB Sharded Cluster..."

# -------------------------
# INIT CONFIG REPLICA SET
# -------------------------
docker exec config1 mongosh --port 27019 --eval '
try {
  rs.status()
} catch (e) {
  rs.initiate({
    _id: "cfgRS",
    configsvr: true,
    members: [
      { _id: 0, host: "config1:27019" },
      { _id: 1, host: "config2:27019" }
    ]
  })
}
'

echo "⏳ Waiting for config RS PRIMARY..."

until docker exec config1 mongosh --port 27019 --eval 'rs.status().myState' | grep -q "1"; do
  sleep 2
done

# -------------------------
# INIT SHARD 1
# -------------------------
docker exec shard1 mongosh --port 27018 --eval '
try {
  rs.status()
} catch (e) {
  rs.initiate({
    _id: "shard1RS",
    members: [
      { _id: 0, host: "shard1:27018" }
    ]
  })
}
'

# -------------------------
# INIT SHARD 2
# -------------------------
docker exec shard2 mongosh --port 27019 --eval '
try {
  rs.status()
} catch (e) {
  rs.initiate({
    _id: "shard2RS",
    members: [
      { _id: 0, host: "shard2:27019" }
    ]
  })
}
'

echo "⏳ Waiting for shards to elect PRIMARY..."
sleep 10

# -------------------------
# ADD SHARDS TO CLUSTER
# -------------------------
docker exec mongos mongosh --eval '
sh.addShard("shard1RS/shard1:27018");
sh.addShard("shard2RS/shard2:27019");
'

echo "⏳ Enabling test database sharding..."

docker exec mongos mongosh --eval '
sh.enableSharding("testdb");
sh.shardCollection("testdb.users", { _id: "hashed" });
'

echo "✅ Cluster fully initialized!"
