package de.heinerkuecker.constraint;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;

import de.heinerkuecker.constraint.util.coll.ReadOnlySet;

/**
 * Abstrakte Ober-Klasse für Prädikate des
 * {@link AbstractConstraintCodeGenerator}.
 * 
 * @param <CT> Type-Parameter Kontext-Objekt-Type
 * @author Heiner K&uuml;cker
 */
@SuppressWarnings( "serial" ) // serial version id wird nicht deklariert, damit der Cache Änderungen an der Klasse erkennt
abstract public class Predicate<CT>
implements Serializable
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
     * Cache für Information, ob dieses Prädikat
     * eine Konstradiktion ist(nie erfüllbar).
     * <br/>
     * Wird intern vom 
     * {@link AbstractConstraintCodeGenerator}
     * verwendet.
     */
    private Boolean isContradiction;

    /**
     * @param exclude Exclude
     * @return ob dieses Prädikat
     * eine Konstradiktion ist(nie erfüllbar)
     */
    public final boolean isContradiction(
            final ConstraintCodeGeneratorCache<CT> cache )
    {
        if ( this.isContradiction != null )
        {
            return this.isContradiction.booleanValue();
        }
        
        this.isContradiction =
                BruteForceContradictionChecker.isContradiction(
                        cache ,
                        this );

        return this.isContradiction.booleanValue();
    }

    /**
     * Konstruktor.
     *
     * @param lineNr
     */
    protected Predicate( 
            final int lineNr )
    {
        this.lineNr = lineNr;
    }

    /**
     * Zu implementierende Methode zum Testen
     * der Bedingung.
     * 
     * @param contextObj Kontext-Objekt, dieses Objekt enthält den aktuellen Benutzer,
     *                   die aktuell geladene fachliche Entität und weitere Informationen
     *                   über den Context des Prädikates
     * @return ob die Bedingung des Prädikates durch das Kontext-Objekt eingehalten wird
     */
    abstract public boolean test(
            final CT contextObj );

    /**
     * Zu implementierende Methode zum
     * Erzeugen einer als 
     * Datei(Java-Klassen)-Name
     * geeigneten String-Darstellung.
     * 
     * @param separatorStr Trenn-String
     * @return String-Darstellung
     */
    abstract public String toFileName(
            final String separatorStr );
    
    abstract public boolean isPrimitive();
    
    abstract public Predicate<CT> replacePrimitivePredicatesWithFalse(
            final HashSet<PrimitivPredicate<CT>> predicateSetToRemove , 
            //final Exclude<CT> exclude ,
            final ConstraintCodeGeneratorCache<CT> cache );
    //abstract public Predicate<CT> replacePredicatesWithFalse(
    //        final HashSet<Predicate<CT>> predicateSetToRemove , 
    //        final Exclude<CT> exclude );
    
    //abstract public Predicate<CT> replacePrimitivePredicatesWithTrue(
    //        final HashSet<PrimitivPredicate<CT>> predicateSetToRemove , 
    //        final Exclude<CT> exclude );
    abstract public Predicate<CT> replacePredicatesWithTrue(
            final HashSet<Predicate<CT>> predicateSetToRemove , 
            //final Exclude<CT> exclude ,
            final ConstraintCodeGeneratorCache<CT> cache );

    /**
     * Diese zu implementierende Methode gibt alle
     * nichtnegierten und auf dem obersten Level
     * in einem And befindlichen Prädikate zurück,
     * welche die übergebene Klasse implementieren.
     * <br/>
     * Diese Methode wird in Implementierungen von
     * {@link NewDestinationConstraintSafeOperation#getDestinationConstraintExpression}
     * aufgerufen, um vor der sicheren Operationen
     * vorhandene Constraint-Prädikate abzufragen.
     * 
     * @param predicateClass gesuchte Prädikats-Klasse
     * @return List mit Prädiakten oder <code>null</code>
     */
    abstract public List<PrimitivPredicate<CT>> getPredicateListFromAndNotNegated(
            final Class<? extends PrimitivPredicate<CT>> predicateClass );

    /**
     * @return alle includierten Prädikate
     */
    abstract public ReadOnlySet<PrimitivPredicate<CT>> getIncludePredicateSetRecursiv();
    
    /**
     * @return alle includierten Prädikate und dieses selbst
     */
    abstract public ReadOnlySet<PrimitivPredicate<CT>> getIncludePredicateSetRecursivInclusiveSelf();
    
    /**
     * @return alle enthaltenen primitiven Prädikate
     */
    abstract public ReadOnlySet<PrimitivPredicate<CT>> getPrimitivPredicateSet();

    /**
     * @return ob dieses Prädikat ein untergeordnetes {@link Not}-Prädikat hat
     */
    abstract public boolean hasNotSubPred();

    /**
     * @return alle enthaltenen unbedingt zu erfüllenden primitiven Prädikate
     */
    abstract public ReadOnlySet<PrimitivPredicate<CT>> getMandantoryPrimitivPredicateSet();

    /**
     * @return alle enthaltenen unbedingt negiert zu erfüllenden primitiven Prädikate
     */
    abstract public ReadOnlySet<PrimitivPredicate<CT>> getNegatedMandantoryPrimitivPredicateSet();

    /**
     * @return alle enthaltenen nicht negierten primitiven Prädikate
     */
    abstract public ReadOnlySet<PrimitivPredicate<CT>> getNonNegatedPrimitivPredicateSet();
    
    /**
     * @return ob es im Prädikat eine Or-Verknüpfung gibt
     */
    abstract public boolean hasOr();

    /**
     * @return ob es im Prädikat eine Not-Verknüpfung gibt
     */
    abstract public boolean hasNot();

}
