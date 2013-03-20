package _10_transform_safeoperation_str_len.predicates;

import de.heinerkuecker.constraint.util.coll.ReadOnlySet;

/**
 * Marker-Interface f�r Pr�dikate,
 * die f�r das ein bestimmtes Zeichen 
 * eines Strings gelten.
 * 
 * @author Heiner K&uuml;cker
 */
public interface StrCharacter
{
    /**
     * @return Set mit den erlaubten Zeichen
     */
    public ReadOnlySet<Character> getEnabledCharacters();

}