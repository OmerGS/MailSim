package mail;
import java.util.ArrayList;
import java.util.Iterator;

/**
* Server of our mail services
* @author O.Gunes 
*/
public class MailServer {
    /**
     * ArrayList which contain mails.
     */
    private ArrayList<MailItem> items;

    private AntiSpam antiSpam;

    private ArrayList<MailItem> indesirable;

    /**
    * Constructor which initialize Items arraylist
    */
    public MailServer(ArrayList<String>a){
        if(a != null){
            this.antiSpam = new AntiSpam(a);
            this.items = new ArrayList<MailItem>();
            this.indesirable = new ArrayList<MailItem>();
        }
        else{
            System.out.println("Mettre des parametre valide");
            this.antiSpam = new AntiSpam(new ArrayList<String>());
            this.items = new ArrayList<MailItem>();
        }
        
    }    

    /**
     * Allow to post mail at somebody
     * 
     * @param item - The mail
     */
    public void post(MailItem item){
        if(item != null){
            if(this.antiSpam.scan(item.getMessage())){
                String s = "[SPAM] " + item.getMessage();
                MailItem it = new MailItem(item.getFrom(), item.getTo(), s);
                this.indesirable.add(it);
            }
            else{
                this.items.add(item);
            }
        }
        else{
            System.out.println("Mettre des parametre des valide");
        }
    }

    /**
     * @param who - The persons who wants know how mail he has
     * @return - Number of unread mail
     */
    public int howManyMailItems(String who){
        int compteur = 0;
        if(who == null){
            System.out.println("MailServer_howManyMailItems : Valeur Nulle");
        } else {
            for (MailItem item : items) {
                if(((item.getTo())).equals(who)){
                    compteur++;
                }
            }
        }
        return(compteur);
    }

    /**
    * A method which return you an unread mail
    *
    * @param who - E-mail who had received mail
    * @return - The mail
    */
    public MailItem getNextMailItem(String who){
        MailItem prochainMail = null;

        if(who == null){
            System.out.println("MailServer_getNextMailItem : Valeur Nulle");
        } else {
            if(howManyMailItems(who) > 0){
                boolean found = false;
                Iterator<MailItem> it = this.items.iterator();
                while(it.hasNext() || !found){
                    MailItem nextItem = it.next();
                    if(nextItem.getTo().equals(who)){
                        it.remove();
                        found = true;
                        prochainMail = nextItem;
                    }
                }
            }
        }
        return(prochainMail);
    }


    /*/
    public int howManyMailSpam(String who){
        int compteur = 0;
        for (MailItem item : indesirable) {
            if(((item.getTo())).equals(who)){
                compteur++;
                item.print();
            }
        }
        return(compteur);
    }
    */
}
