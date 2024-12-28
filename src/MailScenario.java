import java.util.ArrayList;
import java.util.Scanner;

import mail.*;

public class MailScenario {

    final String RESET = "\033[0m";  // Réinitialiser
    final String RED = "\033[0;31m";  // Rouge
    final String GREEN = "\033[0;32m";  // Vert
    final String YELLOW = "\033[0;33m";  // Jaune
    final String BLUE = "\033[0;34m";  // Bleu
    final String CYAN = "\033[0;36m";  // Cyan
    final String WHITE = "\033[0;37m";  // Blanc

    private String nomUtilisateurCourant;

    private static ArrayList<MailClient> LesClients = new ArrayList<>();

    ArrayList<String> motsSpam = new ArrayList<>();

    MailServer server = new MailServer(motsSpam);

    public static void main(String[] args) {
        MailScenario mailScenario = new MailScenario();

        mailScenario.start();
    }

    public void start() {
        motsSpam.add("argent");
        motsSpam.add("riche");
        motsSpam.add("concours");
        motsSpam.add("gagner");

        MailClient omer = new MailClient(server, "omer");

        LesClients.add(omer);

        connectionOuInscription();
    }

    public void connectionOuInscription() {
        System.out.print("\033[H\033[2J");
        System.out.println(CYAN + "Bienvenue sur Mailer !" + RESET);
        System.out.println("Appuyez sur 'A' pour vous connecter");
        System.out.println("Appuyez sur 'B' pour vous inscrire");

        Scanner userPromptScan = new Scanner(System.in);
        String userPrompt = userPromptScan.next();

        if (userPrompt.equals("A")) {
            connection();
        } else if (userPrompt.equals("B")) {
            inscription();
        }
    }

    public void connection() {
        System.out.print("\033[H\033[2J");
        System.out.println(CYAN + "*** CONNECTION SUR MAILER ***" + RESET);
        System.out.println("Saisissez 'echap' pour revenir en arrière !");
        String nameOfUser;
        do {
            System.out.println("Entrez votre identifiant : ");

            Scanner nameOfUserScan = new Scanner(System.in);
            nameOfUser = nameOfUserScan.next();

            if (getClientByName(nameOfUser)) {
                this.nomUtilisateurCourant = nameOfUser;
                System.out.print("\033[H\033[2J");
                System.out.println(GREEN + "Connecté avec succès" + RESET);
                menuPrincipal();
            } else if (nameOfUser.equals("echap")) {
                connectionOuInscription();
            } else {
                System.out.println(RED + "Erreur identifiant inconnu !" + RESET);
            }

        } while (!getClientByName(nameOfUser));
    }

    private boolean getClientByName(String nom) {
        for (MailClient client : LesClients) {
            if (client.getUser().equals(nom)) {
                return true;
            }
        }
        return false; // Retourne false si aucun client n'est trouvé avec cet identifiant
    }


    public void inscription() {
        System.out.print("\033[H\033[2J");
        System.out.println(CYAN + "*** INSCRIPTION SUR MAILER ***" + RESET);
        String nameOfUser;
        boolean sorti = false;

        do {
            System.out.println("Entrez un identifiant (ne l'oubliez pas) : ");

            Scanner nameOfUserScan = new Scanner(System.in);
            nameOfUser = nameOfUserScan.next();

            if (getClientByName(nameOfUser)) {
                System.out.println(RED + "Erreur cet identifiant existe déjà" + RESET);
            } else {
                System.out.println(GREEN + "Inscription réalisée avec succès !" + RESET);
                MailClient client = new MailClient(server, nameOfUser);
                LesClients.add(client);
                System.out.print("\033[H\033[2J");
                sorti = true;
                connection();
            }
        } while (getClientByName(nameOfUser) && !sorti);
    }

    public void menuPrincipal() {
        System.out.print("\033[H\033[2J");
        int choix;
        System.out.println(CYAN + nomUtilisateurCourant + ", bienvenue sur votre compte Mailer !" + RESET);

        do {
            System.out.print("\033[H\033[2J");
            int nbMailNonLu = server.howManyMailItems(nomUtilisateurCourant);
            System.out.println(YELLOW + "Vous avez " + nbMailNonLu + " mail non lu." + RESET);

            System.out.println("Appuyez sur '1' pour envoyer des mails");
            System.out.println("Appuyez sur '2' pour consulter vos mails");
            System.out.println("Appuyez sur '3' pour consulter vos mails indésirables");
            System.out.println("Appuyez sur '4' pour aller dans les paramètres");
            System.out.println("Appuyez sur '5' pour vous déconnecter");

            Scanner inChoix = new Scanner(System.in);
            choix = inChoix.nextInt();

            switch (choix) {
                case 1:
                    mailSend();
                    break;
                case 2:
                    boiteDeReception();
                    break;
                case 3:
                    spamMail();
                    break;
                case 4:
                    parameters();
                    break;
                case 5:
                    logout();
                    break;
                default:
                    System.out.println(RED + "Choix inconnu" + RESET);
                    break;
            }
        } while (choix < 5);
    }

    public void mailSend() {
        System.out.print("\033[H\033[2J");
        System.out.println(CYAN + "*** ENVOI DE MAIL ***" + RESET);
        MailItem mail;

        System.out.println("À qui voulez-vous envoyer le mail ? : ");
        Scanner inTo = new Scanner(System.in);
        String to = inTo.next();

        System.out.println("Destinataire : " + to);

        System.out.println("Que voulez-vous envoyer ? : ");
        Scanner inMessage = new Scanner(System.in);
        String message = inMessage.next();

        mail = new MailItem(nomUtilisateurCourant, to, message);

        server.post(mail);

        mail.print();
    }

    public void boiteDeReception() {
        System.out.print("\033[H\033[2J");
        System.out.println(CYAN + "*** BOÎTE DE RÉCEPTION ***" + RESET);
        int nbMailNonLu = server.howManyMailItems(nomUtilisateurCourant);
        System.out.println("Vous avez " + nbMailNonLu + " mail non lu.");

        MailItem mail = server.getNextMailItem(nomUtilisateurCourant);

        if (mail != null) {
            mail.print();
        } else {
            System.out.println(YELLOW + "Pas de nouveau mail !" + RESET);
        }

        System.out.println("Appuyez sur une touche pour sortir.");
        Scanner inSortie = new Scanner(System.in);
        String sortie = inSortie.next();
    }

    public void spamMail() {
        System.out.print("\033[H\033[2J");
        System.out.println(CYAN + "*** COURRIER INDÉSIRABLES ***" + RESET);

        //System.out.println(server.howManyMailSpam(nomUtilisateurCourant));

        System.out.println("Appuyez sur une touche pour sortir.");
        Scanner inSortie = new Scanner(System.in);
        String sortie = inSortie.next();
    }

    public void parameters() {
        System.out.print("\033[H\033[2J");
        System.out.println(CYAN + "*** PARAMÈTRES ***" + RESET);

        for (String mot : motsSpam) {
            System.out.println(mot);
        }

        System.out.println("\nAppuyez sur F pour ajouter des mots\nAppuyez sur H pour supprimer des mots");
        System.out.println("Appuyez sur une autre touche pour quitter");

        Scanner inSortie = new Scanner(System.in);
        String sortie = inSortie.next();

        if (sortie.equals("F")) {
            System.out.println("Veuillez saisir un mot à ajouter (tapez 'echap' pour quitter)");
            Scanner inAdd = new Scanner(System.in);
            String add = inAdd.next();

            do {
                if (add.equals("echap")) {
                    parameters();
                } else {
                    motsSpam.add(add);
                    parameters();
                }
            } while (!(add.equals("echap")));

        } else if (sortie.equals("H")) {
            System.out.println("Veuillez saisir un mot à supprimer (tapez 'echap' pour quitter)");
            Scanner inAdd = new Scanner(System.in);
            String add = inAdd.next();

            do {
                if (add.equals("echap")) {
                    parameters();
                } else {
                    motsSpam.remove(add);
                    parameters();
                }
            } while (!(add.equals("echap")));
        } else {
            menuPrincipal();
        }
    }

    public void logout() {
        System.out.print("\033[H\033[2J");
        String choix;

        System.out.println(CYAN + "*** PAGE DE DÉCONNEXION ***" + RESET);

        do {
            System.out.println("Êtes-vous sûr de vous déconnecter ? (Oui/Non) : ");

            Scanner choixScan = new Scanner(System.in);
            choix = choixScan.next();

        } while (!choix.equalsIgnoreCase("Oui") && !choix.equalsIgnoreCase("Non"));

        if (choix.equalsIgnoreCase("Oui")) {
            System.out.println("Déconnexion...");
            System.out.print("\033[H\033[2J");
            connectionOuInscription();
        } else if (choix.equalsIgnoreCase("Non")) {
            System.out.println("Redirection vers le menu");
            System.out.print("\033[H\033[2J");
            menuPrincipal();
        }
    }
}