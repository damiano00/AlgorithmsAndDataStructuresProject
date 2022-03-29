package it.unicam.cs.asdl2021.totalproject2;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

/**
 * 
 * @author Template: Luca Tesei
 * @author Implementation: Damiano Pasquini
 *
 */
class BinaryHeapMinPriorityQueueTest {

    private final BinaryHeapMinPriorityQueue<PriorityQueueElement> queue = new BinaryHeapMinPriorityQueue<>();

    private final PriorityQueueElement firstElement = new PriorityQueueElement() {

        private double priority = 1.0;
        private int handle = 0;
        @Override
        public double getPriority() {
            return this.priority;
        }
        @Override
        public void setPriority(double newPriority) {
            this.priority = newPriority;
        }
        @Override
        public int getHandle() {
            return this.handle;
        }
        @Override
        public void setHandle(int newHandle) {
            this.handle = newHandle;
        }
    };

    private final PriorityQueueElement secondElement = new PriorityQueueElement() {

        private double priority = 2.0;
        private int handle = 0;
        @Override
        public double getPriority() {
            return this.priority;
        }
        @Override
        public void setPriority(double newPriority) {
            this.priority = newPriority;
        }
        @Override
        public int getHandle() {
            return this.handle;
        }
        @Override
        public void setHandle(int newHandle) {
            this.handle = newHandle;
        }
    };

    private final PriorityQueueElement thirdElement = new PriorityQueueElement() {

        private double priority = 3.0;
        private int handle = 0;
        @Override
        public double getPriority() {
            return this.priority;
        }
        @Override
        public void setPriority(double newPriority) {
            this.priority = newPriority;
        }
        @Override
        public int getHandle() {
            return this.handle;
        }
        @Override
        public void setHandle(int newHandle) {
            this.handle = newHandle;
        }
    };

    private final PriorityQueueElement fourthElement = new PriorityQueueElement() {

        private double priority = 4.0;
        private int handle = 0;
        @Override
        public double getPriority() {
            return this.priority;
        }
        @Override
        public void setPriority(double newPriority) {
            this.priority = newPriority;
        }
        @Override
        public int getHandle() {
            return this.handle;
        }
        @Override
        public void setHandle(int newHandle) {
            this.handle = newHandle;
        }
    };

    private void repeatedCode(){
        this.queue.clear();
        this.queue.insert(firstElement);
        this.queue.insert(secondElement);
        this.queue.insert(thirdElement);
        this.queue.insert(fourthElement);
    }

    @Test
    final void testBinaryHeapMinPriorityQueue() {
        this.queue.clear();
        assertTrue(this.queue.isEmpty());
    }

    @Test
    final void testInsert() {
        // controllo eccezione
        this.queue.clear();
        assertThrows(NullPointerException.class, () -> this.queue.insert(null));

        // controllo valori di ritorno
        this.queue.clear();
        assertTrue(this.queue.isEmpty());

        this.queue.insert(firstElement);
        assertFalse(this.queue.isEmpty());
        assertEquals(1 ,this.queue.size());
        assertEquals(0, firstElement.getHandle());

        this.queue.insert(secondElement);
        assertEquals(2 ,this.queue.size());
        assertEquals(1, secondElement.getHandle());

        this.queue.insert(thirdElement);
        assertEquals(3 ,this.queue.size());
        assertEquals(2, thirdElement.getHandle());

        this.queue.insert(fourthElement);
        assertEquals(4,this.queue.size());
        assertEquals(3, fourthElement.getHandle());
    }

    @Test
    final void testInsert2() {
        // controllo eccezione
        this.queue.clear();
        assertThrows(NullPointerException.class, () -> this.queue.insert(null));

        // controllo valori di ritorno
        this.queue.clear();
        assertTrue(this.queue.isEmpty());

        this.queue.insert(fourthElement);
        assertFalse(this.queue.isEmpty());
        assertEquals(1 ,this.queue.size());
        assertEquals(0, fourthElement.getHandle());

        this.queue.insert(thirdElement);
        assertEquals(2 ,this.queue.size());
        assertEquals(0, thirdElement.getHandle());
        assertEquals(1, fourthElement.getHandle());

        this.queue.insert(secondElement);
        assertEquals(3,this.queue.size());
        assertEquals(0, secondElement.getHandle());
        assertEquals(1, fourthElement.getHandle());
        assertEquals(2, thirdElement.getHandle());

        this.queue.insert(firstElement);
        assertEquals(4, this.queue.size());
        assertEquals(0, firstElement.getHandle());
        assertEquals(1, secondElement.getHandle());
        assertEquals(2, thirdElement.getHandle());
        assertEquals(3, fourthElement.getHandle());
    }

    @Test
    final void testDecreasePriority2() {
        // controllo eccezione
        this.queue.clear();
        assertThrows(NullPointerException.class, () -> this.queue.insert(null));

        // controllo valori di ritorno
        this.queue.clear();
        assertTrue(this.queue.isEmpty());

        this.queue.insert(fourthElement);
        assertFalse(this.queue.isEmpty());
        assertEquals(1 ,this.queue.size());
        assertEquals(0, fourthElement.getHandle());

        this.queue.insert(thirdElement);
        assertEquals(2 ,this.queue.size());
        assertEquals(0, thirdElement.getHandle());
        assertEquals(1, fourthElement.getHandle());

        this.queue.insert(secondElement);
        assertEquals(3,this.queue.size());
        assertEquals(0, secondElement.getHandle());
        assertEquals(1, fourthElement.getHandle());
        assertEquals(2, thirdElement.getHandle());

        this.queue.insert(firstElement);
        assertEquals(4, this.queue.size());
        assertEquals(0, firstElement.getHandle());
        assertEquals(1, secondElement.getHandle());
        assertEquals(2, thirdElement.getHandle());
        assertEquals(3, fourthElement.getHandle());

        queue.decreasePriority(fourthElement, 1.5);
        assertEquals(1, fourthElement.getHandle());
        assertEquals(3, secondElement.getHandle());
    }

    @Test
    final void testMinimum() {
        // controllo eccezione
        this.queue.clear();
        assertThrows(NoSuchElementException.class, () -> this.queue.minimum());

        // controllo valori di ritorno
        this.repeatedCode();
        assertEquals(firstElement, this.queue.minimum());
    }

    @Test
    final void testExtractMinimum() {
        // controllo eccezione
        this.queue.clear();
        assertThrows(NoSuchElementException.class, () -> this.queue.extractMinimum());

        // controllo valori di ritorno
        this.repeatedCode();
        assertEquals(4, this.queue.size());
        assertEquals(firstElement, this.queue.extractMinimum());
        assertEquals(secondElement, this.queue.minimum());
        assertEquals(3, this.queue.size());
    }

    @Test
    final void testDecreasePriority() {
        // controllo eccezioni
        this.queue.clear();
        assertThrows(NoSuchElementException.class, () -> this.queue.decreasePriority(firstElement, 0.5));
        this.repeatedCode();
        assertThrows(IllegalArgumentException.class, () -> this.queue.decreasePriority(firstElement, 3.0));

        // controllo valori di ritorno
        this.repeatedCode();
        this.queue.decreasePriority(secondElement, 0.5);
        assertEquals(0, secondElement.getHandle());
        assertEquals(secondElement, queue.extractMinimum());
        queue.decreasePriority(fourthElement, 0);
        assertEquals(0, fourthElement.getHandle());
    }

    @Test
    final void testIsEmpty() {
        this.queue.clear();
        assertTrue(this.queue.isEmpty());
    }

    @Test
    final void testSize() {
        this.queue.clear();
        assertEquals(0, this.queue.size());
        this.repeatedCode();
        assertEquals(4, this.queue.size());
    }

    @Test
    final void testClear() {
        this.repeatedCode();
        this.queue.clear();
        assertEquals(0, this.queue.size());
    }

}
