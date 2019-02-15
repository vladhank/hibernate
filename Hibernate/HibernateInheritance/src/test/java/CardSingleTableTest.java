import inheritance.BankCreditCard;
import inheritance.Card;
import inheritance.BankCreditCard;
import inheritance.DebitCard;
import org.hibernate.Transaction;
import org.junit.Assert;
import org.junit.Test;
import util.EMFactory;

import javax.persistence.EntityManager;
import java.time.LocalDate;

public class CardSingleTableTest {
    @Test
    public void saveCardTest(){
        EntityManager entityManager = EMFactory.getEntityManager();
        Card testCard = new Card(4321652287554311L,"VISA","Vova","Gudzo", LocalDate.of(2022,12,31),333,4321,0,"ACTIVE","BY04AKBB36029110100040000000",1L);
        BankCreditCard creditCard = new BankCreditCard(4321652287554311L,"VISA","Vova","Gudzo", LocalDate.of(2022,12,31),333,4321,0,"ACTIVE","BY04AKBB36029110100040000000",1L,5,10);
        DebitCard debitCard = new DebitCard(5643872165447717L,"MASTERCARD","Denis","Cuba",LocalDate.of(2025,07,30),455,6600,5,"ACTIVE","BY04AKBB3602911010004000JNBD",2L,5);
        entityManager.getTransaction().begin();
        entityManager.persist(testCard);
        entityManager.persist(creditCard);
        entityManager.persist(debitCard);
        entityManager.getTransaction().commit();
        BankCreditCard cardFromDB = new BankCreditCard();
        entityManager.getTransaction().begin();
        cardFromDB=entityManager.find(BankCreditCard.class,creditCard.getId());
        entityManager.getTransaction().commit();
        entityManager.close();
        Assert.assertEquals(creditCard,cardFromDB);
    }

    @Test
    public void updateCardTest(){
        EntityManager entityManager = EMFactory.getEntityManager();
        DebitCard debitCard = new DebitCard(5643872165447717L,"MASTERCARD","Denis","Cuba",LocalDate.of(2025,07,30),455,6600,5,"ACTIVE","BY04AKBB3602911010004000JNBD",2L,5);
        Transaction tx = ( Transaction ) entityManager.getTransaction();
        tx.begin();
        entityManager.persist(debitCard);
        tx.commit();
        tx.begin();
        debitCard.setCashBack(99);
        tx.commit();
        tx.begin();
        DebitCard mergedCard = entityManager.merge(debitCard);
        DebitCard cardFromDb = entityManager.find(DebitCard.class,debitCard.getId());
        tx.commit();
        tx.begin();
        DebitCard newDebitCard = new DebitCard();
        debitCard = newDebitCard;
        debitCard.setCashBack(115);
        tx.commit();
        Assert.assertEquals(99,cardFromDb.getCashBack());
        entityManager.close();
    }
}
