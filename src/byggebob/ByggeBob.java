package byggebob;

import java.util.Scanner;
public class ByggeBob {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        // Variabeldeklerationer
        int rutorPerRad = 9;
        int antalSpelare = 4;
        int specialRutor = 5; // var femte ruta
        int nästaTur = 1;
        int antalVinnare = 0;
        String[] spelareSymboler = {"♠", "♥", "♦", "♣"};
        String[] spelareNamn = {"Späder", "Hjärter", "Ruter", "Klöver"};
        int[] spelareRuta = {0,0,0,0};
        boolean[] skippaRunda = {false,false,false,false};
        String fråga;
        
        String[] rutor = new String[(rutorPerRad*3)+2];
        
        for (int i = 0; i < rutor.length; i++) {
            if ((i+1) % specialRutor == 0) {
                rutor[i]= "!";
            } else {
                rutor[i]= ""+(i+1);
            }
        }
        
        // Logik
        System.out.println("---- Bygge Bob ----");
        
        while (true) {
            System.out.println("\nTryck [Enter] för att börja"
                + "\nSkriv in [R] för att läsa reglerna"
                + "\nSkriv in [I] för att ändra inställningarna"
                + "\n(Antalet Spelare, Rutor och Specialrutor.)");
            
            fråga = input.nextLine();
            
            if (fråga.equalsIgnoreCase("R")) {
                visaRegler();
            } else if (fråga.equalsIgnoreCase("I")) {
                int[] nyaInställningar = inställningar(antalSpelare, rutorPerRad, specialRutor, input);
                antalSpelare = nyaInställningar[0];
                rutorPerRad = nyaInställningar[1];
                specialRutor = nyaInställningar[2];
                rutor = new String[(rutorPerRad * 3) + 2];
                if (specialRutor == 0) {
                    for (int i = 0; i < rutor.length; i++) {
                        rutor[i] = "" + (i + 1);
                    }
                } else {
                    for (int i = 0; i < rutor.length; i++) {
                        if ((i + 1) % specialRutor == 0) {
                            rutor[i] = "!";
                        } else {
                            rutor[i] = "" + (i + 1);
                        }
                    }
                }    
            } else {
                break;
            }
        }
        
        while(true) {
            spelareRuta = spelRunda(nästaTur, spelareRuta, rutorPerRad, antalSpelare, specialRutor, skippaRunda);
            
            if (spelareRuta[nästaTur-1] == -1) {
                System.out.println();
                System.out.println(spelareNamn[nästaTur-1]+" har vunnit!");
                System.out.println();
                antalVinnare++;
            }
            
            if (antalVinnare == antalSpelare-1) {
                break;
            }
            
            skrivUtSpelplan(rutor, rutorPerRad, spelareRuta, spelareSymboler);
            
            if (nästaTur == antalSpelare) {
                nästaTur = 1;
            } else {
                nästaTur++;
                while (nästaTur <= antalSpelare && spelareRuta[nästaTur-1] == -1) {
                    nästaTur++;
                }
            }
        }
        
        System.out.println("Spelet har slutat!");
    }
    
    //returnerar ett värde 1-6
    static int tärningSlump() {
        return (int)(Math.random()*6+1); 
    }
    
    static String hämtaRutaVärde(String[] rutor, int[] spelareRuta, String[] spelareSymboler, int rutaIndex) {
    for (int i = 0; i < spelareRuta.length; i++) {
        if (spelareRuta[i] == rutaIndex + 1) {
            return spelareSymboler[i];
        }
    }
    return rutor[rutaIndex];
}
    
    static int[] spelRunda(int nästaTur, int[] spelareRuta, int rutorPerRad, int antalSpelare, int specialRutor, boolean[] skippaRunda) {
        Scanner input = new Scanner(System.in);
        String[] spelareSymboler = {"♠", "♥", "♦", "♣"};
        String[] spelareNamn = {"Späder", "Hjärter", "Ruter", "Klöver"};
        String[] talSkriva = {"Ett", "Två", "Tre", "Fyra", "Fem", "Sex"};
        
        System.out.println("---- Spelare "+nästaTur+" ("+spelareNamn[nästaTur-1]+") ----"
                + "\nTryck [Enter] för att slå tärningen");
        input.nextLine();
        
        int rutaInnanSlag = spelareRuta[nästaTur-1];
        
        int tärningSlag = tärningSlump();
        spelareRuta[nästaTur-1] = spelareRuta[nästaTur-1]+tärningSlag;
        
        if (spelareRuta[nästaTur-1] > rutorPerRad*3+2) {
            spelareRuta[nästaTur-1] = -1;
        }
        
        if (tärningSlag == 6) {
            System.out.println("Du Slog "+talSkriva[tärningSlag-1]+"!!!");
        } else if (tärningSlag == 1) {
            System.out.println("Du Slog "+talSkriva[tärningSlag-1]+"...");
        } else if (tärningSlag == 4 || tärningSlag == 5) {
            System.out.println("Du Slog "+talSkriva[tärningSlag-1]+"!");
        } else {
            System.out.println("Du Slog "+talSkriva[tärningSlag-1]);
        }
        
        if (spelareRuta[nästaTur - 1] > 0) {
            System.out.println();
            System.out.print("Clack");
            for (int i = 0; i < tärningSlag-1; i++) {
                System.out.print(" clack");
            }
            System.out.print("...\n");
            
            boolean skaKnuffa = true;
            while (skaKnuffa) {
                skaKnuffa = false;
                for (int i = 0; i < antalSpelare; i++) {
                    if (spelareRuta[i] == spelareRuta[nästaTur - 1] && spelareRuta[i] > 0 && !(i == nästaTur - 1)) {
                        System.out.println(spelareNamn[nästaTur - 1] + " knuffade " + spelareNamn[i] + " en ruta bak!");
                        System.out.println();
                        spelareRuta[i]--;
                        skaKnuffa = true;
                    }
                }
            }
            
            System.out.println("Nu ligger du ("+spelareNamn[nästaTur-1]+") på ruta "+spelareRuta[nästaTur-1]+".");
            if (spelareRuta[nästaTur-1] % specialRutor == 0) {
                System.out.println("...Som är en specialruta!");
                System.out.println();
                spelareRuta = specialRuta(spelareRuta, nästaTur, rutorPerRad, specialRutor, spelareNamn, skippaRunda, antalSpelare, rutaInnanSlag);
            } else {
                System.out.println();
                System.out.println("Tryck [Enter] för att fortsätta");
                input.nextLine();
            }
        }
        
        return spelareRuta;
    }
    
    static int[] specialRuta(int[] spelareRuta, int nästaTur, int rutorPerRad, int specialRutor, String[] spelareNamn, boolean[] skippaRunda, int antalSpelare, int rutaInnanSlag) {
        Scanner input = new Scanner(System.in);
        int händelse = (int)(Math.random()*10+1);
        
        //hantera de två specialstraffen vid sista specialrutan
        if (spelareRuta[nästaTur - 1] > (rutorPerRad*3+2 - specialRutor + 1)) {
            int chans = (int)(Math.random()*6+1);
            if (chans == 1) {
                händelse = 11;
            }
            else if (chans == 6) {
                händelse = 12;
            }
        }
        
        switch (händelse) {
            case 1 -> {
                System.out.println("Du hade tur, ingenting hände!");
            }
            case 2 -> {
                System.out.println("Du halkade på ett bananskal, du gick bakåt en ruta!");
                if (spelareRuta[nästaTur - 1] > 1) {
                    spelareRuta[nästaTur - 1]--;
                } else {
                    spelareRuta[nästaTur - 1] = 0;
                }
            }
            case 3 -> {
                System.out.println("Du blev förkyld och orkar inte, du skippar en runda!");
                skippaRunda[nästaTur - 1] = true;
            }
            case 4 -> {
                System.out.println("Du hittade en teleporteringsapparat.");
                
                int minstaRutor = spelareRuta[0];
                int minstaSpelare = -1;
                
                for (int i = 0; i < spelareRuta.length; i++) {
                    if (spelareRuta[i] < minstaRutor && spelareRuta[i] >= 0) {
                        minstaRutor = spelareRuta[i];
                    } 
                }
                for (int i = 0; i < spelareRuta.length; i++) {
                    if (spelareRuta[i] == minstaRutor) {
                        minstaSpelare = i;
                    }
                }
                
                if (!(antalSpelare < 2 || nästaTur-1 == minstaSpelare || minstaSpelare == -1)) {
                    System.out.println("Du bytte plats med "+spelareNamn[minstaSpelare]+"!");
                    int sparaRutaVärde = spelareRuta[nästaTur-1];
                    spelareRuta[nästaTur - 1] = spelareRuta[minstaSpelare];
                    spelareRuta[minstaSpelare] = sparaRutaVärde;
                } else {
                    System.out.println("Men den var ur funktion... Ingenting hände.");
                }
            }
            case 5 -> {
                System.out.println("Du hittade en teleporteringsapparat.");
                
                int mestRutor = spelareRuta[0];
                int mestSpelare = -1;
                
                for (int i = 0; i < spelareRuta.length; i++) {
                    if (spelareRuta[i] > mestRutor && spelareRuta[i] >= 0) {
                        mestRutor = spelareRuta[i];
                    } 
                }
                for (int i = 0; i < spelareRuta.length; i++) {
                    if (spelareRuta[i] == mestRutor) {
                        mestSpelare = i;
                    }
                }
                
                if (!(antalSpelare < 2 || nästaTur-1 == mestSpelare || mestSpelare == -1)) {
                    System.out.println("Du bytte plats med "+spelareNamn[mestSpelare]+"!");
                    int sparaRutaVärde = spelareRuta[nästaTur-1];
                    spelareRuta[nästaTur - 1] = spelareRuta[mestSpelare];
                    spelareRuta[mestSpelare] = sparaRutaVärde;
                } else {
                    System.out.println("Men den var ur funktion... Ingenting hände.");
                }
            }
            case 6 -> {
                System.out.println("Du slog ditt huvud och lider av minnesförlust");
                System.out.println("Du är fortfarande i ruta "+rutaInnanSlag+", slå tärningen!");
                System.out.println("\nTryck [Enter] för att slå tärningen");
                input.nextLine();
                int tärningSlag = tärningSlump();
                System.out.println("Du slog "+ tärningSlag +"!");
                spelareRuta[nästaTur - 1] += tärningSlag;
            }
            case 7 -> {
                System.out.println("Du hittade en tärning på golvet, slå igen!");
                System.out.println("\nTryck [Enter] för att slå tärningen");
                input.nextLine();
                int bonusSlag = tärningSlump();
                System.out.println("Du slog "+ bonusSlag +"!");
                spelareRuta[nästaTur - 1] += bonusSlag;
            }
            case 8 -> {
                System.out.println("Rundan du nyss körde var bara en dröm.");
                System.out.println("Nu är det för sent, din runda skippades!");
                spelareRuta[nästaTur - 1] += rutaInnanSlag;
            }
            case 9 -> {
                System.out.println("Du hittade en raket och flög framåt 3 rutor!");
                spelareRuta[nästaTur - 1] += 3;
            }
            case 10 -> {
                System.out.println("Du råkade sitta på en baklängesraket och flög bakåt 3 rutor!");
                if (!(spelareRuta[nästaTur - 1] < 0)) {
                    spelareRuta[nästaTur - 1] -= 3;
                }
            }
            case 11 -> {
                
                System.out.println("Du hade tur, ingenting hände!");
                System.out.println();
                System.out.println("Tryck [Enter] för att fortsätta");
                input.nextLine();
                System.out.println("Men innan din runda är slut känner du en tyngd i bröstkorgen...");
                System.out.println("Du hade en hjärtattack, du gick tillbaka till början!");
                spelareRuta[nästaTur - 1] = 0;
            }
            case 12 -> {
                System.out.println("Du hittade en teleporteringsapparat.");
                System.out.println("När du track på den så teleporterade du till slutet, grattis!");
                spelareRuta[nästaTur - 1] = rutorPerRad * 3 + 3;
            }
        }
        
        System.out.println();
        if (händelse == 11) {
            System.out.println("Tryck [Enter] för att fortsätta (på riktigt den här gången)");
        } else {
            System.out.println("Tryck [Enter] för att fortsätta");
        }
        input.nextLine();
        
        return spelareRuta;
    }
    
    static void visaRegler() {
        System.out.println("det här är reglerna");
        System.out.println();
    }
    
    static int[] inställningar(int antalSpelare, int rutorPerRad, int specialRutor, Scanner input) {
        System.out.println("---- Inställningar ----");
        System.out.println("Antal spelare: " + antalSpelare);
        System.out.println("Rutor per rad: " + rutorPerRad);
        System.out.println("Specialrutornas mellanrum: " + specialRutor);
        
        System.out.println("\nVill du ändra inställningarna? (J/N)");
        String fråga = input.nextLine();
        
        if (fråga.equalsIgnoreCase("J")) {
            while(true) {
                System.out.print("Nytt antal spelare: ");
                antalSpelare = input.nextInt();
                input.nextLine();
                if (antalSpelare>4) {
                    System.out.println("Error, max 4 spelare.\n");
                }
                else if (antalSpelare < 1) {
                    System.out.println("Error, minst 1 spelare.\n");
                } else {
                    break;
                }
            }
            
            while(true) {
                System.out.print("Nya rutor per rad: ");
                rutorPerRad = input.nextInt();
                input.nextLine();
                if (rutorPerRad > 32) {
                    System.out.println("Error, max 32 rutor per rad.\n");
                }
                else if (rutorPerRad < 3) {
                    System.out.println("Error, minst 3 rutor per rad.\n");
                } else {
                    break;
                }
            }
            
            System.out.print("Nytt specialruta mellanrum? ");
            specialRutor = input.nextInt();
            input.nextLine();
        }
        return new int[]{antalSpelare, rutorPerRad, specialRutor};
    }
    
    // Skriver ut spelplanen
    static void skrivUtSpelplan(String[] rutor, int rutorPerRad, int[] spelareRuta, String[] spelareSymboler) {
        int rutaSkrivUt = 0; // för att kolla vilken ruta i arrayen "rutor" som ska skrivas ut
        for (int i = 1; i < 12; i++) {
            if (!(i % 2 == 0)) {
                // Skriv ut rutornas kanter
                System.out.print("|");
                for (int j = 0; j < rutorPerRad; j++) {
                    System.out.print("----|");
                }
                System.out.println();
            } else {
                // Skriv ut själva värdena av rutorna
                if (i == 4) {
                    // Bara en ruta på höger sida
                    for (int j = 0; j < rutorPerRad-1; j++) {
                        System.out.print("     ");
                    }
                    String visaVärde = hämtaRutaVärde(rutor, spelareRuta, spelareSymboler, rutaSkrivUt);
                    if (visaVärde.length() > 1) {
                        System.out.println("| " + visaVärde + " |");
                    } else {
                        System.out.println("| " + visaVärde + "  |");
                    }
                    rutaSkrivUt++;
                } else if (i == 8) {
                    // Bara en ruta på vänster sida
                    String visaVärde = hämtaRutaVärde(rutor, spelareRuta, spelareSymboler, rutaSkrivUt);
                    if (visaVärde.length() > 1) {
                        System.out.println("| " + visaVärde + " |");
                    } else {
                        System.out.println("| " + visaVärde + "  |");
                    }
                    rutaSkrivUt++;
                } else if (i == 6) {
                    // En rad av rutor i omvänd ordning
                    System.out.print("|");
                    for (int j = 0; j < rutorPerRad; j++) {
                        int omväntIndex = rutaSkrivUt + (rutorPerRad - (j * 2) - 1);
                        String visaVärde = hämtaRutaVärde(rutor, spelareRuta, spelareSymboler, omväntIndex);
                        if (visaVärde.length() > 1) {
                            System.out.print(" " + visaVärde + " |");
                        } else {
                            System.out.print(" " + visaVärde + "  |");
                        }
                        rutaSkrivUt++;
                    }
                    System.out.println();
                } else {
                    // En rad av rutor i vanlig ordning
                    System.out.print("|");
                    for (int j = 0; j < rutorPerRad; j++) {
                        String visaVärde = hämtaRutaVärde(rutor, spelareRuta, spelareSymboler, rutaSkrivUt);
                        if (visaVärde.length() > 1) {
                            System.out.print(" " + visaVärde + " |");
                        } else {
                            System.out.print(" " + visaVärde + "  |");
                        }
                        rutaSkrivUt++;
                    }
                    System.out.println();
                }
            }
        }
        System.out.println();
    }
    
}
