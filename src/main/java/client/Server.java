package client;

//Tutti gli import necessari
import java.io.*;
import java.net.*;

public class Server {
    public void avvia(){

        //Apertura socket server
        try(ServerSocket server = new ServerSocket(12345)){
            System.out.println("Server avviato sulla porta 12345");
            //Ciclo per tenere il sever aperto anche in chiusura della connessione
            while(true){
                //Il server rimane in ascolto per eventuali connessioni
                System.out.println("Server in ascolto....");
                Socket client = server.accept();
                System.out.println("Connessione accettata!");
                boolean connessione = true;
                //Creazione stream di input e output
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                PrintWriter out = new PrintWriter(client.getOutputStream(), true);
                while(connessione){
                    //Lettura dell'operazione inviata dal client
                    String op = in.readLine();
                    //Controllo se l'operazione è nulla, in caso contrario la eseguo
                    if(op != null){
                        String[] parti = op.split(";");
                        Double op1 = Double.parseDouble(parti[0]);
                        Double op2 = Double.parseDouble(parti[1]);
                        Double res;
                        
                        //Switch per eseguire l'operazione richiesta
                        switch (parti[2]){
                            case "+":
                                res = op1+op2;
                                out.println("Risultato= " + res);
                            break;
                            case "-":
                                res= op1-op2;
                                out.println("Risultato= " + res);
                            break;
                            case "*":
                                res= op1*op2;
                                out.println("Risultato= " + res);
                            break;
                            case "/":
                                res= op1/op2;
                                out.println("Risultato= " + res);
                            break;
                            default:
                                out.println("C'è stato un errore nell'operazione");
                            break;
                        }
                    }
                    else{
                        //Se l'operazione è nulla, chiudo la connessione
                        connessione=false;
                    }
                    
                }
            }
    
        }
        //Gestione eccezioni
        catch(IOException e){
            System.out.println("C'è stato un errore" + e.getMessage());
        }
        catch(Exception e){
            System.out.println("C'è stato un errore" + e.getMessage());
        }
    }
}
