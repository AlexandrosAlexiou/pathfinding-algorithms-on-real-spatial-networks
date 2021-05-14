# <div align="center">Top-K Queries using a Spatial Network</div>

---

### California Road Network and Points of Interest [dataset](http://www.cs.utah.edu/~lifeifei/SpatialDataset.htm)

[California Road Network's Nodes (Vertex ID, Longitude, Latitude)](http://www.cs.utah.edu/~lifeifei/research/tpq/cal.cnode)

[California Road Network's Edges \(Edge ID, Start Vertex ID, End Vertex ID, L2 Distance\)](http://www.cs.utah.edu/~lifeifei/research/tpq/cal.cedge)


### Download

---

``` commandline
curl http://www.cs.utah.edu/~lifeifei/research/tpq/cal.cnode --output cal.cnode && curl http://www.cs.utah.edu/~lifeifei/research/tpq/cal.cedge --output cal.cedge
```

### Run graph initialization

---

In project root,
``` commandline
javac -d out Graph.java
```

In out folder,
``` commandline
java Graph 0 55
```
to find Shortest path from node `0` to node `55` using A* and Dijkstra.