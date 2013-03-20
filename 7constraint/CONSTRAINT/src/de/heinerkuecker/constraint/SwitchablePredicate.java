package de.heinerkuecker.constraint;

import java.util.Arrays;

import de.heinerkuecker.constraint.util.lang.UniqueChecker;

/**
 * Prädikat-Expression mit
 * Möglichkeit zum Erzeugen
 * einer Switch-Klasse zum
 * Spezialisieren der
 * Prädikat-Expression.
 * 
 * @param <CT> Type-Parameter Kontext-Objekt-Type
 * @author Heiner K&uuml;cker
 */
public final class SwitchablePredicate<CT>
{
    /**
     * Zeilen-Nummer der Erzeugung zum Finden
     * der Definition des Prädikates.
     * 
     * Zeilen-Nummer zum besseren Finden der Codestelle,
     * an welcher das Constraint definiert wurde.
     */
    public final int lineNr;

    /**
     * Normales Prädikat
     */
    public final Predicate<CT> predicate;
    
    
    public final SafeSet<CT, ?>[] safeSetArr;
    
    /**
     * Array mit den Spezialisierungs-Definitionen.
     */
    public final SwitchDefinition<CT>[] switchDefinitionArr;

    /**
     * Array mit Definitionen sicherer Operationen
     */
    /*default*/ /*final*/ AbstractSafeOperation<CT>[] safeOperationArr;
    
    /*default*/ boolean isDynamicSafeOperationsAdded = false;
    
    /**
     * Eltern-Prädikat, falls dieses SwitchablePredicate
     * aus einem vorherigen Switch entstanden ist.
     */
    public /*final*/ Predicate<CT> parentPredicate;
    
    /**
     * Konstruktor.
     *
     * @param predicate
     * @param switchDefinitionArr
     */
    SwitchablePredicate( 
            final int lineNr ,
            final Predicate<CT> predicate , 
            final SafeSet<CT, ?>[] safeSetArr ,
            final SwitchDefinition<CT>[] switchDefinitionArr )
    {
        this.lineNr = lineNr;

        this.predicate = predicate;
        
        this.safeSetArr = safeSetArr;

        checkSwitchDefinitionNameUniqueness(
                switchDefinitionArr );

        this.switchDefinitionArr = switchDefinitionArr;

        this.safeOperationArr = null;
    }

    /**
     * Konstruktor.
     *
     * @param predicate
     * @param safeOperationArr Array mit Definitionen sicherer Operationen
     * @param switchDefinitionArr
     */
    SwitchablePredicate( 
            final int lineNr ,
            final Predicate<CT> predicate , 
            final SafeSet<CT, ?>[] safeSetArr ,
            final AbstractSafeOperation<CT>[] safeOperationArr ,
            final SwitchDefinition<CT>[] switchDefinitionArr )
    {
        this.lineNr = lineNr;

        this.predicate = predicate;

        this.safeSetArr = safeSetArr;

        checkSwitchDefinitionNameUniqueness(
                switchDefinitionArr );

        this.switchDefinitionArr = switchDefinitionArr;

        this.safeOperationArr = safeOperationArr;
    }

    /**
     * Prüfen, dass alle SwitchDefinitionen unterschiedliche Namen haben
     * @param switchDefinitionArr
     */
    private static <CT> void checkSwitchDefinitionNameUniqueness(
            final SwitchDefinition<CT>[] switchDefinitionArr )
    {
        if ( switchDefinitionArr == null )
        {
            return;
        }

        // Prüfen, dass alle SwitchDefinitionen unterschiedliche Namen haben
        final UniqueChecker<String> switchDefinitionNameUniqueChecker =
                new UniqueChecker<String>(
                        "switch definition name" );

        for ( final SwitchDefinition<CT> switchDefinition : switchDefinitionArr )
        {
            switchDefinitionNameUniqueChecker.add(
                    switchDefinition.name );
        }
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + this.predicate.hashCode();
        result = prime * result + Arrays.hashCode( this.switchDefinitionArr );
        return result;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals( Object obj )
    {
        if ( this == obj )
        {
            return true;
        }
        if ( obj == null )
        {
            return false;
        }
        if ( getClass() != obj.getClass() )
        {
            return false;
        }
        final SwitchablePredicate<?> other = (SwitchablePredicate<?>) obj;
        if ( ! this.predicate.equals( other.predicate ) )
        {
            return false;
        }
        if ( ! Arrays.equals( this.switchDefinitionArr , other.switchDefinitionArr ) )
        {
            return false;
        }
        return true;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return 
                "SwitchablePredicate [" +
                "predicate=" + predicate + ", " +
                "switchDefinitionArr=" + Arrays.toString( switchDefinitionArr ) + ", " +
                "safeOperationArr=" + Arrays.toString( safeOperationArr ) + ", " +
                "parentPredicate=" + parentPredicate
                + "]";
    }

}
