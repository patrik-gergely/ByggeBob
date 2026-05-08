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
            
            System.out.println();
            
            fråga = input.nextLine();
            
            if (fråga.equalsIgnoreCase("R")) {
                visaRegler();
            } else if (fråga.equalsIgnoreCase("I")) {
                int[] nyaInställningar = inställningar(antalSpelare, rutorPerRad, specialRutor, input);
                antalSpelare = nyaInställningar[0];
                rutorPerRad = nyaInställningar[1];
                specialRutor = nyaInställningar[2];
                rutor = new String[(rutorPerRad * 3) + 2];
                for (int i = 0; i < rutor.length; i++) {
                    if ((i + 1) % specialRutor == 0) {
                        rutor[i] = "!";
                    } else {
                        rutor[i] = "" + (i + 1);
                    }
                }
            } else {
                break;
            }
        }
        
        while(true) {
            spelareRuta = spelRunda(nästaTur, spelareRuta, rutorPerRad, antalSpelare);
            
            if (spelareRuta[nästaTur-1] == -1) {
                System.out.println(spelareNamn[nästaTur-1]+" har vunnit!");
                antalVinnare++;
            }
            
            if (antalVinnare == antalSpelare) {
                break;
            }
            
            if (nästaTur == antalSpelare) {
                nästaTur = 1;
            } else {
                nästaTur++;
                while (nästaTur <= antalSpelare && spelareRuta[nästaTur-1] == -1) {
                    nästaTur++;
                }
            }
            
            input.nextLine();
            
            skrivUtSpelplan(rutor, rutorPerRad, spelareRuta, spelareSymboler);
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
    
    static int[] spelRunda(int nästaTur, int[] spelareRuta, int rutorPerRad, int antalSpelare) {
        Scanner input = new Scanner(System.in);
        String[] spelareSymboler = {"♠", "♥", "♦", "♣"};
        String[] spelareNamn = {"Späder", "Hjärter", "Ruter", "Klöver"};
        
        System.out.println("---- Spelare "+nästaTur+" ("+spelareNamn[nästaTur-1]+") ----"
                + "\nTryck [Enter] för att slå tärningen");
        input.nextLine();
        
        int tärningSlag = tärningSlump();
        spelareRuta[nästaTur-1] = spelareRuta[nästaTur-1]+tärningSlag;
        
        if (spelareRuta[nästaTur-1] > rutorPerRad*3+2) {
            spelareRuta[nästaTur-1] = -1;
        }
        
        if (spelareRuta[nästaTur - 1] > 0) {
            boolean skaKnuffa = true;
            while (skaKnuffa) {
                skaKnuffa = false;
                for (int i = 0; i < antalSpelare; i++) {
                    if (i == nästaTur - 1) continue;
                    if (spelareRuta[i] == spelareRuta[nästaTur - 1] && spelareRuta[i] > 0) {
                        System.out.println(spelareNamn[nästaTur - 1] + " knuffade " + spelareNamn[i] + " en ruta bak!");
                        spelareRuta[i]--;
                        skaKnuffa = true;
                    }
                }
            }
        }
        
        if (tärningSlag == 6) {
            System.out.println("Du Slog: "+tärningSlag+" !!!");
        } else if (tärningSlag == 1) {
            System.out.println("Du Slog: "+tärningSlag+" ...");
        } else if (tärningSlag == 4 || tärningSlag == 5) {
            System.out.println("Du Slog: "+tärningSlag+" !");
        } else {
            System.out.println("Du Slog: "+tärningSlag);
        }
        
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
                } else {
                    break;
                }
            }
            
            while(true) {
                System.out.print("Nya rutor per rad: ");
                rutorPerRad = input.nextInt();
                input.nextLine();
                if (rutorPerRad>32) {
                    System.out.println("Error, max 32 rutor per rad.\n");
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
    }
    
}
