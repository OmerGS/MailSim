package mail;
import java.util.ArrayList;
import java.util.Iterator;


public class AntiSpam {
    private ArrayList <String> filtreSpam;
    
    public AntiSpam(ArrayList filtreSpam){
        if(filtreSpam == null){
            System.out.println("Liste de mots filtré est vide !");
        } else {
            this.filtreSpam = filtreSpam;
        }
    }

    public void add(String mot){
        if(mot != null){
            this.filtreSpam.add(mot);
        } else {
            System.out.println("Le mot est nulle");
        }
    }

    public boolean scan(String message){
        boolean spam = false;
        if(message != null){
            Iterator <String> it = this.filtreSpam.iterator();

            while ((it.hasNext()) && (!spam)) {
                if(message.indexOf(it.next()) >= 0){
                    spam = true;
                }
            }
        } else {
            System.out.println("Message nulle !");
        }

        return(spam);
    }
}
