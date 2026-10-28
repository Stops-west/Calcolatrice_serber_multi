package client;

// Tutti gli import necessari
import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public void avvia(){

        // Apertura socket server
        try(ServerSocket server = new ServerSocket(12345)){
            System.out.println("Server avviato sulla porta 12345");
            ExecutorService executor = Executors.newCachedThreadPool();
            
            // Ciclo per tenere il server aperto anche in chiusura della connessione
            while(true){
                // Accettazione connessione client
                Socket client = server.accept();
                System.out.println("Connessione accettata da " + client.getInetAddress());
                
                // Creazione di un nuovo thread per gestire la connessione con il client
                executor.execute(new ClientHandler(client));
                
            }
            
        } 
        
        // Gestione eccezioni
        catch (IOException e) {
            System.err.println("Errore nell'apertura della socket del server: " + e.getMessage());
        }
        catch (Exception e) {
            System.err.println("Errore generico: " + e.getMessage());
        }
    }
}