package de.heinerkuecker.constraint;


/**
 * Oberklasse für Unit-Tests.
 * 
 * @author Heiner K&uuml;cker
 */
abstract public class AbstractConstraintTest<CT>
{
    /**
     * @return ConstraintCodeGeneratorCache
     */
    abstract protected ConstraintCodeGeneratorCache<CT> getCache();

    /**
     * Bequemlichkeits-Methode zum Erzeugen {@link And}-Prädikat
     * @param predArr Sub-Prädikate
     * @return {@link And}-Prädikat
     */
    protected final Predicate<CT> and(
            final Predicate<CT>... predArr )
    {
        return And.instanceOf(
                //lineNr
                AbstractConstraintCodeGenerator.callerLineNr() ,
                getCache() ,
                //cleanForImplicits
                //false ,
                //andPredicateArr
                predArr );
    }

    /**
     * Bequemlichkeits-Methode zum Erzeugen {@link Or}-Prädikat
     * @param predArr Sub-Prädikate
     * @return {@link Or}-Prädikat
     */
    protected final Predicate<CT> or(
            final Predicate<CT>... predArr )
    {
        return Or.instanceOf(
                //lineNr
                AbstractConstraintCodeGenerator.callerLineNr() ,
                getCache() ,
                //orPredicateArr
                predArr );
    }

    /**
     * Bequemlichkeits-Methode zum Erzeugen {@link Not}-Prädikat
     * @param predToNegate zu negierendes Prädikate
     * @return {@link Not}-Prädikat
     */
    protected final Predicate<CT> not(
            final Predicate<CT> predToNegate )
    {
        return Not.instanceOf(
                //lineNr
                AbstractConstraintCodeGenerator.callerLineNr() ,
                predToNegate );
    }

}