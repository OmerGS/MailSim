package mail;

public class MailClient {
    private String user;
    private MailServer server;
    
    public MailClient(MailServer server, String user){
        if(server != null){
            this.server = server;
        } else {
            System.out.println("MailClient : Le serveur est nulle !");
        }

        if(user != null){
            this.user = user;
        } else {
            System.out.println("MailClient : Le nom est nulle !");
        }
    }

    public String getUser(){
        return(this.user);
    }

    public MailItem getNextMailItem(){
        return(server.getNextMailItem(this.user));
    }

    public void printNextMailItem(){
        MailItem mail = getNextMailItem();
        if(mail == null){
            System.out.println("Pas de mail");
        } else {
            mail.print();
        }
    }

    public void sendMailItem(String to, String message){
        MailItem mail;
        if(to != null && message != null){
            mail = new MailItem(this.user, to, message);
            server.post(mail);
        } else {
            System.out.println("Erreur dans le destinateur ou le message !");
        }
    }
}
