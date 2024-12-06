package org.example.DAO;
import jakarta.persistence.*;
import java.util.List;
import org.example.Entity.CatalogItem;
import org.example.Entity.Book;

public class CatalogItemDAO {
    private EntityManager entityManager;

    public CatalogItemDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void addCatalogItem(CatalogItem item) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(item);
        transaction.commit();
    }

    public void removeCatalogItem(String isbn) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        CatalogItem item = entityManager.find(CatalogItem.class, isbn);
        if (item != null) {
            entityManager.remove(item);
        }
        transaction.commit();
    }

    public CatalogItem findCatalogItemByIsbn(String isbn) {
        return entityManager.find(CatalogItem.class, isbn);
    }

    public List<CatalogItem> findCatalogItemsByYear(int year) {
        return entityManager.createQuery("SELECT c FROM CatalogItem c WHERE c.publicationYear = :year", CatalogItem.class)
                .setParameter("year", year)
                .getResultList();
    }

    public List<Book> findBooksByAuthor(String author) {
        return entityManager.createQuery("SELECT b FROM Book b WHERE LOWER(b.author) LIKE :author", Book.class)
                .setParameter("author", "%" + author.toLowerCase() + "%")
                .getResultList();
    }

    public List<CatalogItem> findCatalogItemsByTitle(String title) {
        return entityManager.createQuery("SELECT c FROM CatalogItem c WHERE LOWER(c.title) LIKE :title", CatalogItem.class)
                .setParameter("title", "%" + title.toLowerCase() + "%")
                .getResultList();
    }
}
