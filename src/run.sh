javac -cp ".:./External/genclass.jar" ./HybridServerSide/ArrivalLounge/ArrivalLoungeServer.java
javac -cp ".:./External/genclass.jar" ./HybridServerSide/ArrivalTerminalExit/ArrivalTerminalExitServer.java
javac -cp ".:./External/genclass.jar" ./HybridServerSide/ArrivalTerminalTransferQuay/ArrivalTerminalTransferQuayServer.java
javac -cp ".:./External/genclass.jar" ./HybridServerSide/BaggageCollectionPoint/BaggageCollectionPointServer.java
javac -cp ".:./External/genclass.jar" ./HybridServerSide/BaggageReclaimOffice/BaggageReclaimOfficeServer.java
javac -cp ".:./External/genclass.jar" ./HybridServerSide/DepartureTerminalEntrance/DepartureTerminalEntranceServer.java
javac -cp ".:./External/genclass.jar" ./HybridServerSide/DepartureTerminalTransferQuay/DepartureTerminalTransferQuayServer.java
javac -cp ".:./External/genclass.jar" ./HybridServerSide/TemporaryStorageArea/TemporaryStorageAreaServer.java
javac -cp ".:./External/genclass.jar" ./HybridServerSide/Repository/RepositoryServer.java

java -cp ".:./External/genclass.jar" HybridServerSide/ArrivalLounge/ArrivalLoungeServer &
java -cp ".:./External/genclass.jar" HybridServerSide/ArrivalTerminalExit/ArrivalTerminalExitServer &
java -cp ".:./External/genclass.jar" HybridServerSide/ArrivalTerminalTransferQuay/ArrivalTerminalTransferQuayServer &
java -cp ".:./External/genclass.jar" HybridServerSide/BaggageCollectionPoint/BaggageCollectionPointServer &
java -cp ".:./External/genclass.jar" HybridServerSide/BaggageReclaimOffice/BaggageReclaimOfficeServer &
java -cp ".:./External/genclass.jar" HybridServerSide/DepartureTerminalEntrance/DepartureTerminalEntranceServer &
java -cp ".:./External/genclass.jar" HybridServerSide/DepartureTerminalTransferQuay/DepartureTerminalTransferQuayServer &
java -cp ".:./External/genclass.jar" HybridServerSide/TemporaryStorageArea/TemporaryStorageAreaServer &
java -cp ".:./External/genclass.jar" HybridServerSide/Repository/RepositoryServer &

javac -cp ".:./External/genclass.jar" ./ClientSide/Main/AirportRhapsodyMain.java
java -cp ".:./External/genclass.jar" ClientSide/Main/AirportRhapsodyMain

wait
