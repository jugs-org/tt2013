package _14_transform_safeoperation_tic_tac_toe.test;

import org.junit.After;
import org.junit.Before;

import de.heinerkuecker.constraint.AbstractConstraintCodeGenerator;
import de.heinerkuecker.constraint.AbstractConstraintTest;
import de.heinerkuecker.constraint.ConstraintCodeGeneratorCache;
import de.heinerkuecker.constraint.Exclude;

import _14_transform_safeoperation_tic_tac_toe.TicTacToe;

/**
 * Abstrakte Oberklasse für JUnit4-Testcase des 
 * {@link AbstractConstraintCodeGenerator}
 * mit {@link TicTacToe}-Kontext-Objekt.
 * 
 * @author Heiner K&uuml;cker
 */
abstract public class AbstractTicTacToeConstraintTest
extends AbstractConstraintTest<TicTacToe>
{
    /**
     * Cache für Code-Generator mit {@link Exclude}
     */
    private ConstraintCodeGeneratorCache<TicTacToe> cache;
    
    /**
     * für jeden Testcase (Methode) einen frischen Cache anlegen
     */
    @Before
    public void setUp()
    {
        this.cache =
                // für jeden Testcase (Methode) einen frischen Cache anlegen
                new ConstraintCodeGeneratorCache<TicTacToe>(
                        null ,
                        null ,
                        new Exclude<TicTacToe>() );
    }
    
    @After
    public void tearDown()
    {
        // TODO System.out.println( "Duration: " + );
    }

    /**
     * @see AbstractConstraintTest#getCache()
     */
    @Override
    protected ConstraintCodeGeneratorCache<TicTacToe> getCache()
    {
        if ( this.cache == null )
        {
            // Beim Lauf(Run/Debug) einzelner Methoden aus dem Unit-Test scheint die setUp-Methode nicht aufgerufen zu werden
            setUp();
        }
        return this.cache;
    }
}