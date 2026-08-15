```text
                 ParkingLot
          +----------------------+
          | capacity             |
          | v_arr : Vehicle*[]   |
          +----------------------+
          | park(v)              |
          | unpark(v)            |
          +----------+-----------+
                     |
                     | stores
                     v

                 Vehicle (abstract)
          +----------------------+
          | duration             |
          | isParked             |
          | number_plate         |
          +----------------------+
          | cost()               |
          +----+-----------+-----+
               |           |
               |           |
               v           v

             Car         Bike
        +-----------+ +-----------+
        | cost()    | | cost()    |
        | d * 0.5   | | d * 0.1   |
        +-----------+ +-----------+
```

### Memory trick

* **ParkingLot --> stores vehicles** in array and Vehicle -> Car/Bike
* park() / unpark() in the ParkingLot Class
* cost() in the Vehicle Class
