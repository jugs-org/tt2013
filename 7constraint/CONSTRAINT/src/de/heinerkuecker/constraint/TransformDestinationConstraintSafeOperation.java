package de.heinerkuecker.constraint;

import java.util.List;

/**
 * Sichere Operation mit Ziel-Constraint,
 * welches durch die Transformation des
 * vorherigen Constraints entsteht.
 * 
 * @param <CT> Type-Parameter, Klasse des Context-Objekts der Pr�dikate des Constraint-Code-Generators
 * @author Heiner K&uuml;cker
 */
public abstract class TransformDestinationConstraintSafeOperation<CT>
extends DestinationConstraintSafeOperation<CT, CT>
{
    /**
     * Konstruktor.
     * 
     * @param lineNr
     */
    public TransformDestinationConstraintSafeOperation(
            final int lineNr )
    {
        super( lineNr );
    }

    // TODO diese Operation habe ich an das Pr�dikat verlegt, damit dieses in einem if-else-Verteiler �ber die dem Pr�dikat bekannten sicheren Operationen die Transformation ausf�hrt und bei einer unbekannten sicheren Operation eine IllegalArgumentException wirft
    ///**
    // * 
    // * @param srcConstraintPredicate hier wird die vor der sicheren Operation g�ltige Constraint-Pr�dikat-Expression �bergeben, damit dies beim Transformieren des �bergebenen einzelnen Constraint-Pr�dikates mit in Betracht gezogen werden kann
    // * @param predicateToTransform zu transformierendes primitives Pr�dikat aus der urspr�nglichen Constraint-Expression
    // * @return originales unver�ndertes Pr�dikat, wenn unver�ndert im neuen Constraint verwendbar oder
    // *         transformiertes neues Pr�dikat, wenn durch sichere Operation ver�ndert oder
    // *         <code>null</code>, wenn das �bergebene Pr�dikat durch die sichere Operation entf�llt
    // */
    //abstract public Predicate<CT> transformPredicate(
    //        final Predicate<CT> srcConstraintPredicate ,
    //        final PrimitivPredicate<CT> predicateToTransform );

    /**
     * 
     * @param lineNr Zeilen-Nummer f�r neues Pr�dikat zum Finden Definition
     * @param cache Cache mit Exclude f�r eventuellen Aufruf von {@link And#instanceOf} bzw {@link Or#instanceOf}
     * @return Liste mit hinzuzuf�genden Pr�dikaten, welche durch die sichere Operation eingehalten werden.
     *         Die hinzuzuf�genden Pr�dikate werden mit dem transformierten Pr�dikat And-verkn�pft
     */
    abstract public List<? extends Predicate<CT>> getPredicatesToAdd(
            final int lineNr ,
            final ConstraintCodeGeneratorCache<CT> cache );

}
