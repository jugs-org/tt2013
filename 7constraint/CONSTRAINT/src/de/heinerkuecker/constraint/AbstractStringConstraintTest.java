package de.heinerkuecker.constraint;

import org.junit.After;
import org.junit.Before;

/**
 * Abstrakte Oberklasse für JUnit4-Testcase des 
 * {@link AbstractConstraintCodeGenerator}
 * mit {@link String}-Kontext-Objekt.
 * 
 * @author Heiner K&uuml;cker
 */
abstract public class AbstractStringConstraintTest
extends AbstractConstraintTest<String>
{
    /**
     * Cache für Code-Generator mit {@link Exclude}
     */
    private ConstraintCodeGeneratorCache<String> cache;
    
    /**
     * für jeden Testcase (Methode) einen frischen Cache anlegen
     */
    @Before
    public void setUp()
    {
        this.cache =
                // für jeden Testcase (Methode) einen frischen Cache anlegen
                new ConstraintCodeGeneratorCache<String>(
                        null ,
                        null ,
                        new Exclude<String>() );
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
    protected ConstraintCodeGeneratorCache<String> getCache()
    {
        if ( this.cache == null )
        {
            // Beim Lauf(Run/Debug) einzelner Methoden aus dem Unit-Test scheint die setUp-Methode nicht aufgerufen zu werden
            setUp();
        }
        return this.cache;
    }
}