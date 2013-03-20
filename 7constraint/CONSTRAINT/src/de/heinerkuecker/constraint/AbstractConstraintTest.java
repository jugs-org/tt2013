package de.heinerkuecker.constraint;


/**
 * Oberklasse f�r Unit-Tests.
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
     * Bequemlichkeits-Methode zum Erzeugen {@link And}-Pr�dikat
     * @param predArr Sub-Pr�dikate
     * @return {@link And}-Pr�dikat
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
     * Bequemlichkeits-Methode zum Erzeugen {@link Or}-Pr�dikat
     * @param predArr Sub-Pr�dikate
     * @return {@link Or}-Pr�dikat
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
     * Bequemlichkeits-Methode zum Erzeugen {@link Not}-Pr�dikat
     * @param predToNegate zu negierendes Pr�dikate
     * @return {@link Not}-Pr�dikat
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