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
        String[] spelareSymboler = {"♠", "♥", "♦", "♣"};
        String[] spelareNamn = {"Späder", "Hjärter", "Ruter", "Klöver"};
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
        
        
        int[] rundaVärden = spelRunda(nästaTur);
        skrivUtSpelplan(rutor, rutorPerRad);
    }
    
    //returnerar ett värde 1-6
    static int tärningSlump() {
        return (int)(Math.random()*6+1);
    }
    
    static int[] spelRunda(int nästaTur) {
        Scanner input = new Scanner(System.in);
        String[] spelareSymboler = {"♠", "♥", "♦", "♣"};
        String[] spelareNamn = {"Späder", "Hjärter", "Ruter", "Klöver"};
        
        System.out.println("---- Spelare "+nästaTur+" ("+spelareNamn[nästaTur-1]+") ----"
                + "\nTryck [Enter] för att slå tärningen");
        input.nextLine();
        
        return new int[]{nästaTur};
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
    
    //Skriver ut spelplanen
    static void skrivUtSpelplan(String[] rutor, int rutorPerRad) {
        int rutaSkrivUt = 0; // för att kolla vilken ruta i arrayen "rutor" som ska skrivas ut
        for (int i = 1; i < 12; i++) {
            if (!(i % 2 == 0)) {
                //skriv ut rutornas kanter
                System.out.print("|");
                for (int j = 0; j < rutorPerRad; j++) {
                    System.out.print("----|");
                }
                System.out.println();
            } else {
                //Skriv ut själva värden av rutorna
                if (i == 4) {
                    //bara en ruta på höger sida
                    for (int j = 0; j < rutorPerRad-1; j++) {
                        System.out.print("     ");
                    }
                    if (rutor[rutaSkrivUt].length()>1) {
                        System.out.println("| "+rutor[rutaSkrivUt]+" |");
                    } else {
                        System.out.println("| "+rutor[rutaSkrivUt]+"  |");
                    }
                    rutaSkrivUt++;
                } else if (i == 8) {
                    //bara en ruta på vänster sida
                    if (rutor[rutaSkrivUt].length()>1) {
                        System.out.println("| "+rutor[rutaSkrivUt]+" |");
                    } else {
                        System.out.println("| "+rutor[rutaSkrivUt]+"  |");
                    }
                    rutaSkrivUt++;
                } else if (i == 6) {
                    //en rad av rutor fasst i omvänd ordning
                    System.out.print("|");
                    for (int j = 0; j < rutorPerRad; j++) {
                        if (rutor[(rutaSkrivUt+(rutorPerRad-(j*2)-1))].length()>1) {
                            System.out.print(" "+rutor[(rutaSkrivUt+(rutorPerRad-(j*2)-1))]+" |");
                        } else {
                            System.out.print(" "+rutor[(rutaSkrivUt+(rutorPerRad-(j*2)-1))]+"  |");
                        }
                        rutaSkrivUt++;
                    }
                    System.out.println();
                } else {
                    //en rad av rutor i vanlig ordning
                    System.out.print("|");
                    for (int j = 0; j < rutorPerRad; j++) {
                        if (rutor[rutaSkrivUt].length()>1) {
                            System.out.print(" "+rutor[rutaSkrivUt]+" |");
                        } else {
                            System.out.print(" "+rutor[rutaSkrivUt]+"  |");
                        }
                        rutaSkrivUt++;
                    }
                    System.out.println();
                }
            }
        }
    }
    
}
