package de.heinerkuecker.constraint;

import org.junit.After;
import org.junit.Before;

/**
 * Abstrakte Oberklasse für JUnit4-Testcase des 
 * {@link AbstractConstraintCodeGenerator}
 * mit {@link Integer}-Kontext-Objekt.
 * 
 * @author Heiner K&uuml;cker
 */
abstract public class AbstractIntegerConstraintTest
extends AbstractConstraintTest<Integer>
{
    /**
     * Cache für Code-Generator mit {@link Exclude}
     */
    private ConstraintCodeGeneratorCache<Integer> cache;
    
    /**
     * für jeden Testcase (Methode) einen frischen Cache anlegen
     */
    @Before
    public void setUp()
    {
        this.cache =
                // für jeden Testcase (Methode) einen frischen Cache anlegen
                new ConstraintCodeGeneratorCache<Integer>(
                        null ,
                        null ,
                        new Exclude<Integer>() );
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
    protected ConstraintCodeGeneratorCache<Integer> getCache()
    {
        if ( this.cache == null )
        {
            // Beim Lauf(Run/Debug) einzelner Methoden aus dem Unit-Test scheint die setUp-Methode nicht aufgerufen zu werden
            setUp();
        }
        return this.cache;
    }
}