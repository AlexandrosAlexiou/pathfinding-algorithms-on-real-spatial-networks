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

### Compile

---

In project root,
``` commandline
make
```

### Run

---

- Shortest Path algorithms,
    ``` commandline
    java Graph 0 55
    ```
    to find Shortest path from node `1` to node `10` using Dijkstra and A*.
    
    Output,
    ```
    Dijkstra:Shortest path length =  55
    Shortest path distance =  1.0524429999999998
    Shortest path =  [1, 0, 6, 5, 7, 265, 264, 263, 262, 261, 260, 259, 258, 297, 434, 433, 432, 431, 430, 429, 428, 427, 426, 425, 424, 423, 422, 421, 420, 419, 418, 417, 416, 415, 414, 413, 412, 411, 410, 409, 408, 407, 354, 353, 347, 348, 349, 350, 351, 352, 39, 38, 37, 9, 10]
    number of visited nodes = 288
    
    Astar:
    Shortest path length =  55
    Shortest path distance =  1.0524429999999996
    Shortest path =  [1, 0, 6, 5, 7, 265, 264, 263, 262, 261, 260, 259, 258, 297, 434, 433, 432, 431, 430, 429, 428, 427, 426, 425, 424, 423, 422, 421, 420, 419, 418, 417, 416, 415, 414, 413, 412, 411, 410, 409, 408, 407, 354, 353, 347, 348, 349, 350, 351, 352, 39, 38, 37, 9, 10]
    number of visited nodes = 102
    ```


- Best meeting node,
    ``` commandline
    java NRA 1 6 10
    ```
    to find best meeting node for nodes `1`, `6`, `10` using the Top-K NRA algorithm.
  
  Output,
    ```
    best meeting point: 417
    Shortest path distance = 0.526863
    paths: 
    [0.5255799999999999, [1, 0, 6, 5, 7, 265, 264, 263, 262, 261, 260, 259, 258, 297, 434, 433, 432, 431, 430, 429, 428, 427, 426, 425, 424, 423, 422, 421, 420, 419, 418, 417]],
    [0.5176029999999999, [6, 5, 7, 265, 264, 263, 262, 261, 260, 259, 258, 297, 434, 433, 432, 431, 430, 429, 428, 427, 426,425, 424, 423, 422, 421, 420, 419, 418, 417]],
    [0.526863, [10, 9, 37, 38, 39, 352, 351, 350, 349, 348, 347, 353, 354, 407, 408, 409, 410, 411, 412, 413, 414, 415, 416, 417]]
    ```
  