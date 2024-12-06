package org.example;
import jakarta.persistence.*;
import org.example.DAO.CatalogItemDAO;
import org.example.DAO.LoanDAO;
import org.example.DAO.UserDAO;
import org.example.Entity.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("unit-jpa");
        EntityManager em = emf.createEntityManager();
        Scanner scanner = new Scanner(System.in);
        CatalogItemDAO catalogItemDAO = new CatalogItemDAO(em);
        LoanDAO loanDAO = new LoanDAO(em);
        UserDAO userDAO = new UserDAO(em);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy,MM,dd");
        int scelta;

        while (true) {
            try {
                System.out.println("---------------------------------------------------------------------------");
                System.out.println("Benvenuto nella nostra Biblioteca, quale operazione si desidera effettuare?");
                System.out.println("1. Inserisci un libro");
                System.out.println("2. Inserisci una rivista");
                System.out.println("3. Ricerca per titolo");
                System.out.println("4. Ricerca per anno");
                System.out.println("5. Ricerca per ISBN");
                System.out.println("6. Ricerca per autore");
                System.out.println("7. Inserisci un nuovo utente");
                System.out.println("8. Aggiungi un prestito");
                System.out.println("9. Visualizza i prestiti di un utente");
                System.out.println("10. Visualizza prestiti scaduti");
                System.out.println("11. Aggiorna la data di riconsegna di un prestito");
                System.out.println("0. Esci");
                System.out.print("Scegli un'opzione: ");
                scelta = scanner.nextInt();
                scanner.nextLine();

                switch (scelta) {
                    case 1:
                        Book book = new Book();
                        System.out.println("Inserisci il titolo");
                        book.setTitle(scanner.nextLine());
                        System.out.println("Inserisci l'autore");
                        book.setAuthor(scanner.nextLine());
                        System.out.println("Inserisci il numero univoco (ISBN)");
                        book.setIsbn(scanner.nextLine());
                        System.out.println("Inserisci il genere");
                        book.setGenre(scanner.nextLine());
                        System.out.println("Inserisci l'anno di pubblicazione");
                        book.setPublicationYear(scanner.nextInt());
                        System.out.println("Inserisci il numero di pagine");
                        book.setNumberOfPages(scanner.nextInt());
                        System.out.println("Inserimento libro nel database...");
                        catalogItemDAO.addCatalogItem(book);
                        break;
                    case 2:
                        Magazine magazine = new Magazine();
                        System.out.println("Inserisci il titolo");
                        magazine.setTitle(scanner.nextLine());
                        System.out.println("Inserisci l'anno di pubblicazione");
                        magazine.setPublicationYear(scanner.nextInt());
                        scanner.nextLine();
                        System.out.println("Inserisci il codice univoco (ISBN)");
                        magazine.setIsbn(scanner.nextLine());
                        System.out.println("Inserisci il numero di pagine");
                        magazine.setNumberOfPages(scanner.nextInt());
                        System.out.println("Scegli 1. per periodicità settimanale, 2. per periodicità semi-annuale, 3. per periodicità mensile");
                        int p = scanner.nextInt();
                        if (p == 1) {
                            magazine.setPeriodicity(Magazine.Periodicity.WEEKLY);
                        } else if (p == 2) {
                            magazine.setPeriodicity(Magazine.Periodicity.SEMIANNUAL);
                        } else {
                            magazine.setPeriodicity(Magazine.Periodicity.MONTHLY);
                        }
                        System.out.println("Salvataggio rivista nel database...");
                        catalogItemDAO.addCatalogItem(magazine);
                        break;
                    case 3:
                        System.out.println("Inserisci il titolo da ricercare: ");
                        String titolo = scanner.nextLine();
                        List<CatalogItem> byTitle = catalogItemDAO.findCatalogItemsByTitle(titolo);
                        System.out.println("---------------------------------------------------------------------------");
                        byTitle.forEach(mag -> System.out.println("In base al titolo: " + titolo + " abbiamo trovato: " + mag.getTitle()));
                        break;
                    case 4:
                        System.out.println("Inserisci l'anno di ricerca");
                        int anno = scanner.nextInt();
                        List<CatalogItem> byYear = catalogItemDAO.findCatalogItemsByYear(anno);
                        System.out.println("---------------------------------------------------------------------------");
                        byYear.forEach(mag -> System.out.println("In base all'anno: " + anno + " abbiamo trovato: " + mag.getTitle()));
                        break;
                    case 5:
                        System.out.println("Inserisci l'ISBN da ricercare: ");
                        String ISBNr = scanner.nextLine();
                        CatalogItem byISBN = catalogItemDAO.findCatalogItemByIsbn(ISBNr);
                        System.out.println("---------------------------------------------------------------------------");
                        System.out.println("L'ISBN immesso corrisponde all'elemento: " + byISBN.getTitle());
                        break;
                    case 6:
                        System.out.println("Inserisci il nome dell'autore da ricercare: ");
                        String authorNew = scanner.nextLine();
                        List<Book> byAuthor = catalogItemDAO.findBooksByAuthor(authorNew);
                        System.out.println("---------------------------------------------------------------------------");
                        byAuthor.forEach(mag -> System.out.println("In base all'autore fornito abbiamo trovato: " + mag.getTitle()));
                        break;
                    case 7:
                        UserDB user = new UserDB();
                        System.out.println("Inserisci il nome ");
                        user.setFirstName(scanner.nextLine());
                        System.out.println("Inserisci il cognome");
                        user.setLastName(scanner.nextLine());
                        System.out.println("Inserisci la data di nascita (yyyy,MM,dd)");
                        String nuovaDataString = scanner.nextLine();
                        try {
                            LocalDate nuovaData = LocalDate.parse(nuovaDataString, formatter);
                            user.setBirthDate(nuovaData);
                        } catch (Exception e) {
                            System.out.println("Data non valida. Formato corretto di inserimento: yyyy,MM,dd");
                            break;
                        }
                        System.out.println("Inserisci il codice della carta membro");
                        user.setMembershipCardNumber(scanner.nextLine());
                        System.out.println("Salvando il nuovo utente...");
                        em.getTransaction().begin();
                        em.persist(user);
                        em.getTransaction().commit();
                        break;
                    case 8:
                        Loan loan = new Loan();
                        System.out.println("Inserisci il codice dell'elemento prestato ");
                        String newISBN = scanner.nextLine();
                        CatalogItem byISBNLoan = catalogItemDAO.findCatalogItemByIsbn(newISBN);
                        loan.setCatalogItem(byISBNLoan);
                        System.out.println("Inserisci la card dell'utente associato al prestito");
                        String MEMCard = scanner.nextLine();
                        UserDB currUser = userDAO.findUserByMembershipCard(MEMCard);
                        loan.setUser(currUser);
                        System.out.println("Inserisci la data del prestito (yyyy,MM,dd) ");
                        String nuovaDataLoan = scanner.nextLine();
                        try {
                            LocalDate nuovaLocalDateLoan = LocalDate.parse(nuovaDataLoan, formatter);
                            loan.setLoanStartDate(nuovaLocalDateLoan);
                        } catch (Exception e) {
                            System.out.println("Data non valida. Formato corretto di inserimento: yyyy,MM,dd");
                            break;
                        }
                        System.out.println("Salvataggio prestito...");
                        loanDAO.addLoan(loan);
                        break;
                    case 9:
                        System.out.println("Inserisci il numero di card");
                        List<Loan> loansByUser = loanDAO.findLoansByMembershipCardNumber(scanner.nextLine());
                        loansByUser.forEach(l -> System.out.println("Prestito dell'elemento: " + l.getCatalogItem().getTitle() + " per lo user: " + l.getUser().getFirstName()));
                        break;
                    case 10:
                        List<Loan> overDueLoans = loanDAO.findOverdueLoans();
                        overDueLoans.forEach(l -> System.out.println("Prestito dell'elemento: " + l.getCatalogItem().getTitle() + " scaduto " + "Per lo user: " + l.getUser().getFirstName()));
                        break;
                    case 11:
                        System.out.println("Inserisci la data originale del prestito (yyyy,mm,dd)");
                        String updateDateLoan = scanner.nextLine();
                        LocalDate updateDateLoan1 = LocalDate.parse(updateDateLoan, formatter);
                        System.out.println("Inserisci il codice univoco del prodotto");
                        String ISBNupdateLoan = scanner.nextLine();
                        System.out.println("Inserisci la card dell'utente");
                        String cardUpdateLoan = scanner.nextLine();
                        System.out.println("Infine, inserisci la data di riconsegna (yyyy,mm,dd)");
                        String updateDateFinal = scanner.nextLine();
                        LocalDate updateDateFinal1 = LocalDate.parse(updateDateFinal, formatter);
                        loanDAO.updateActualReturnDate(updateDateFinal1, updateDateLoan1, ISBNupdateLoan, cardUpdateLoan);
                        System.out.println("Aggiorno il DB...");
                        break;
                    case 0:
                        System.out.println("Arrivederci! Grazie per aver usato il sistema.");
                        scanner.close();
                        em.close();
                        emf.close();
                        return;
                    default:
                        System.out.println("Opzione non valida. Per favore, scegli un numero tra 0 e 11.");
                }
            } catch (Exception e) {
                System.out.println("Errore: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
