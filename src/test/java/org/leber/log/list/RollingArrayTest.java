package org.leber.log.list;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class RollingArrayTest {

    @Test
    public void testRolling(){
        RollingArray<String>roller = new RollingArray<String>(String.class,20);
        for (int i = 1; i<22;i++){
            roller.push(""+i);
        }


    }
    @Test
    public void testRollingWithLateSetVAlue(){
        RollingArray<Object[]>roller = new RollingArray<Object[]>(Object[].class,20);
        for (int i = 0; i < roller.getArray().length; i++) {
            roller.getArray()[i] = new Object[1];
        }
        for (int i = 1; i<224;i++){
            getBufferEntry(roller)[0]=""+i;
        }
        int actPos = roller.getActPos();
        int endPos = actPos+ roller.size();
        if (endPos>roller.getArray().length){
            endPos=endPos- roller.getArray().length;
        }
        Object[] array = Arrays.copyOfRange(roller.getArray(),roller.getActPos()+1, roller.getArray().length);

        Object[] array2 = Arrays.copyOfRange(roller.getArray(),0, endPos+1);
        roller.getArray();

    }
    private static Object[] getBufferEntry(RollingArray<Object[]> roller) {
        Object[] buffer;
        if (roller.size() == 0) {
            buffer = new Object[7];
            roller.push(buffer);
        } else {
            roller.moveVorward();
            buffer = roller.peek();
        }

        return buffer;
    }
}