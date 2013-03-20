package de.heinerkuecker.constraint;

import java.io.Serializable;

/**
 * Anhand der Informationen,
 * welche in dieser Klasse codiert sind,
 * wird eine constraint-sichere Member
 * in der Constraint-Klasse erzeugt.
 * 
 * Vorraussetzung zum Erzeugen der
 * constraint-sicheren Member ist,
 * dass in allen Prädikats-Belegungen
 * (Model) bei denen das Constraint
 * erfüllt ist, der Typ für die
 * constraint-sichere Member
 * gleich ist.
 * 
 * Die erzeugte Member in der
 * Constraint-Klasse erspart
 * das typunsichere Casten
 * eines Wertes, der bei allen
 * zutreffenden Ausprägungen
 * des Constraints sicher einen
 * bestimmten Typ hat.
 * 
 * @author Heiner K&uuml;cker
 */
abstract public class SafeMember
implements Serializable
{
    /**
     * Name der Member
     */
    public final String name;
    
    /**
     * Angabe des Types der Member als String.
     * Der Type-String kann auch eine Klasse
     * mit Typ-Parameter sein.
     */
    public final String typStr;
    
    /**
     * Member oder get-Methode als String
     * zum Entnehmen des Wertes aus dem
     * Kontext-Objekt.
     */
    //public final String contextObjMemberOrGetterStr;

    /**
     * Konstruktor.
     *
     * @param name
     * @param typStr
     * @param contextObjMemberOrGetterStr 
     */
    public SafeMember( 
            final String name , 
            final String typStr
            //final String contextObjMemberOrGetterStr
            )
    {
        this.name = name;
        this.typStr = typStr;
        //this.contextObjMemberOrGetterStr = contextObjMemberOrGetterStr;
    }
    
    abstract protected String createContextObjMemberOrGetterStr(
            final String contextObjName );

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( this.name == null ) ? 0 : this.name.hashCode() );
        result = prime * result + ( ( this.typStr == null ) ? 0 : this.typStr.hashCode() );
        return result;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals( 
            final Object obj )
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
        final SafeMember other = (SafeMember) obj;
        if ( this.name == null )
        {
            if ( other.name != null )
            {
                return false;
            }
        }
        else if ( !this.name.equals( other.name ) )
        {
            return false;
        }
        if ( this.typStr == null )
        {
            if ( other.typStr != null )
            {
                return false;
            }
        }
        else if ( !this.typStr.equals( other.typStr ) )
        {
            return false;
        }
        return true;
    }

    /**
     * debug output
     */
	@Override
	public String toString()
	{
		return
		        "SafeMember[" +
		        "name=" + name + ", " +
		        "typStr=" + typStr + 
		        //", " + "contextObjMemberOrGetterStr=" + contextObjMemberOrGetterStr + 
		        "]";
	}

}
