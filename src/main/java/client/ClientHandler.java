package client;
import java.io.*;
import java.net.*;

public class ClientHandler implements Runnable {
    private final Socket client;
    
    public ClientHandler(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        // 1. DICHIARIAMO LE VARIABILI FUORI DAL TRY
        // In questo modo saranno visibili in tutto il metodo, compresi i catch e il finally
        BufferedReader in = null;
        PrintWriter out = null;

        try {
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(client.getOutputStream(), true);
            boolean connessione = true;
            
            while(connessione){
                String op = in.readLine();
                
                if(op != null){
                    // Gestisce l'errore di formattazione senza interrompere la connessione con il client
                    try {
                        String[] parti = op.split("\\s+");
                        Double op1 = Double.parseDouble(parti[0]);
                        Double op2 = Double.parseDouble(parti[2]);
                        Double res;
                        
                        switch (parti[1]){
                            case "+":
                                res = op1 + op2;
                                out.println("Risultato= " + res);
                                break;
                            case "-":
                                res = op1 - op2;
                                out.println("Risultato= " + res);
                                break;
                            case "*":
                                res = op1 * op2;
                                out.println("Risultato= " + res);
                                break;
                            case "/":
                                if (op2 == 0) {
                                    out.println("Errore: Divisione per zero impossibile!");
                                } else {
                                    res = op1 / op2;
                                    out.println("Risultato= " + res);
                                }
                                break;
                            default:
                                out.println("C'è stato un errore nell'operazione: operatore sconosciuto");
                                break;
                        }
                    } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                        
                        out.println("Errore di sintassi nei dati inviati.");
                    }
                }
                else {
                    connessione = false;
                }
            }
        }
        catch(IOException e){
            System.out.println("C'è stato un errore di I/O: " + e.getMessage());
        }
        catch(Exception e){
            System.out.println("C'è stato un errore generico: " + e.getMessage());
        }
        finally {
            // Questo blocco pulisce la memoria e chiude la connessione quando il client si disconnette
            try {
                if (in != null) in.close();
                if (out != null) out.close();
                if (client != null && !client.isClosed()) {
                    client.close();
                    System.out.println("Connessione chiusa con successo.");
                }
            } catch (IOException e) {
                System.out.println("Errore nella chiusura delle risorse: " + e.getMessage());
            }
        }
    }
}
