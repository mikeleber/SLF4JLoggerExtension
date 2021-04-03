package org.leber.log.list;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class RollingArrayTest {

    @Test
    public void testFirst(){
        RollingArray<String>roller = new RollingArray<String>(String.class,20);
        for (int i = 1; i<22;i++){
            roller.push(""+i);
            if (i==1){
                Assertions.assertEquals("1",roller.first());
            }
            if (i==2){
                Assertions.assertEquals("1",roller.first());
            }
            if (i==20){
                Assertions.assertEquals("1",roller.first());
            }
        }
        Assertions.assertEquals("2",roller.first());

    }
    @Test
    public void testPush(){
        RollingArray<String>roller = new RollingArray<String>(String.class,20);
        for (int i = 1; i<22;i++){
            roller.push(""+i);
        }

        Assertions.assertEquals("2",roller.first());

    }
    @Test
    public void testToArray(){
        RollingArray<String>roller = new RollingArray<String>(String.class,20);
        for (int i = 1; i<22;i++){
            roller.push(""+i);
        }
        String[] toArray= roller.toArray();

        Assertions.assertEquals("2",roller.first());

    }
    @Test
    public void testRollingWithLateSetValue(){
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
            roller.moveForward();
            buffer = roller.peek();
        }

        return buffer;
    }
}