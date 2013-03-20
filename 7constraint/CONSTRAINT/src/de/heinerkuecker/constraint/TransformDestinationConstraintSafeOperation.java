package de.heinerkuecker.constraint;

import java.util.List;

/**
 * Sichere Operation mit Ziel-Constraint,
 * welches durch die Transformation des
 * vorherigen Constraints entsteht.
 * 
 * @param <CT> Type-Parameter, Klasse des Context-Objekts der Prädikate des Constraint-Code-Generators
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

    // TODO diese Operation habe ich an das Prädikat verlegt, damit dieses in einem if-else-Verteiler über die dem Prädikat bekannten sicheren Operationen die Transformation ausführt und bei einer unbekannten sicheren Operation eine IllegalArgumentException wirft
    ///**
    // * 
    // * @param srcConstraintPredicate hier wird die vor der sicheren Operation gültige Constraint-Prädikat-Expression übergeben, damit dies beim Transformieren des übergebenen einzelnen Constraint-Prädikates mit in Betracht gezogen werden kann
    // * @param predicateToTransform zu transformierendes primitives Prädikat aus der ursprünglichen Constraint-Expression
    // * @return originales unverändertes Prädikat, wenn unverändert im neuen Constraint verwendbar oder
    // *         transformiertes neues Prädikat, wenn durch sichere Operation verändert oder
    // *         <code>null</code>, wenn das übergebene Prädikat durch die sichere Operation entfällt
    // */
    //abstract public Predicate<CT> transformPredicate(
    //        final Predicate<CT> srcConstraintPredicate ,
    //        final PrimitivPredicate<CT> predicateToTransform );

    /**
     * 
     * @param lineNr Zeilen-Nummer für neues Prädikat zum Finden Definition
     * @param cache Cache mit Exclude für eventuellen Aufruf von {@link And#instanceOf} bzw {@link Or#instanceOf}
     * @return Liste mit hinzuzufügenden Prädikaten, welche durch die sichere Operation eingehalten werden.
     *         Die hinzuzufügenden Prädikate werden mit dem transformierten Prädikat And-verknüpft
     */
    abstract public List<? extends Predicate<CT>> getPredicatesToAdd(
            final int lineNr ,
            final ConstraintCodeGeneratorCache<CT> cache );

}
