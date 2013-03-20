package de.heinerkuecker.constraint;

/**
 * TODO
 * 
 * @author Heiner K&uuml;cker
 */
public final class SetConstraintDefinition<CT>
{
    /**
     * Prädikat
     */
    public final Or<CT> predicate;
    
    /*default*/ final AbstractSafeOperation<CT>[] safeOperationArr;

    /**
     * Konstruktor.
     * 
     * @param predicate
     */
    public SetConstraintDefinition(
            final Or<CT> predicate ,
            final AbstractSafeOperation<CT>[] safeOperationArr )
    {
        this.predicate = predicate;
        this.safeOperationArr = safeOperationArr;
    }

}
