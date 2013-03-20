package de.heinerkuecker.constraint;

/**
 * TODO
 * 
 * @author Heiner K&uuml;cker
 */
public final class CounterBooleanSource
implements BooleanSource
{
    private final BinCounterWithSkipListeners counter;
    
    private final int counterDigitIndex;

    /**
     * Konstruktor.
     *
     * @param counter
     * @param counterDigitIndex
     */
    public CounterBooleanSource( 
            final BinCounterWithSkipListeners counter , 
            final int counterDigitIndex )
    {
        this.counter = counter;
        
        if ( counterDigitIndex < 0 ||
                counterDigitIndex >= counter.size() )
        {
            throw new IllegalArgumentException(
                    "counterDigitIndex: " + counterDigitIndex );
        }
        this.counterDigitIndex = counterDigitIndex;
    }



    /**
     * @see BooleanSource#get()
     */
    @Override
    public boolean get()
    {
        return this.counter.get( 
                this.counterDigitIndex );
    }

}
