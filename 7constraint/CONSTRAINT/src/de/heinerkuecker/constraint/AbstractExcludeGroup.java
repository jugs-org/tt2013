package de.heinerkuecker.constraint;

import java.util.Collection;

/**
 * Abstrakte ExcludeGroup.
 * 
 * @author Heiner K&uuml;cker
 */
public abstract class AbstractExcludeGroup<CT>
{
    /**
     * Name der Exclude-Gruppe
     */
    protected final String name;

    /**
     * Konstruktor.
     * @param name
     */
    protected AbstractExcludeGroup( 
            final String name )
    {
        if ( name == null )
        {
            throw new IllegalArgumentException(
                    "name is null" );
        }
        this.name = name;
    }
    
    /**
     * 
     * @param predicateToCheck
     * @return
     */
    abstract public boolean contains(
            final PrimitivPredicate<CT> predicateToCheck );
    
    /**
     * 
     * @param predicateToCheck
     * @return
     */
    public final boolean containsAll(
            final Collection<PrimitivPredicate<CT>> predicateToCheckColl )
    {
        for ( final PrimitivPredicate<CT> predicateToCheck : predicateToCheckColl )
        {
            if ( false == contains(
                    predicateToCheck ) )
            {
                return false;
            }
        }
        return true;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
    	return name.hashCode();
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
    	final ExcludeGroup<?> other = (ExcludeGroup<?>) obj;
    	return name.equals( other.name );
    }

}
