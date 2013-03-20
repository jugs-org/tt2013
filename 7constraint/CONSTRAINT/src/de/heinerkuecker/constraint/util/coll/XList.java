package de.heinerkuecker.constraint.util.coll;

import java.util.Collection;
import java.util.List;

/**
 * Erweiterung {@link java.util.List}
 * um nützliche Methoden.
 * 
 * @author Heiner K&uuml;cker
 */
public interface XList<E>
extends List<E>
{
    public void addAllFromIterable(
            final Iterable<? extends E> ibl );
}
