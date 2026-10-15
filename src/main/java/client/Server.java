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
                System.out.println("Server in ascolto....");
                Socket client = server.accept();
                boolean connessione = true;
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                PrintWriter out = new PrintWriter(client.getOutputStream(), true);
                while(connessione){
                    String op = in.readLine();
                    if(op != null){
                        String[] parti = op.split(";");
                        Double op1 = Double.parseDouble(parti[0]);
                        Double op2 = Double.parseDouble(parti[1]);
                        Double res;
    
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
                        connessione=false;
                    }
                    
                }
            }
    
        }
        catch(IOException e){
            System.out.println("C'è stato un errore" + e.getMessage());
        }
    }
}
