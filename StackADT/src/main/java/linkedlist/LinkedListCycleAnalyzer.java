package linkedlist;

import support.CycleInfo;
import support.LLNode;

public class LinkedListCycleAnalyzer<T> {
    /**
     * Detects if a singly linked list contains a cycle and returns the entry index
     * and length of the cycle. Uses O(1) extra space.
     *
     * @param head The head of the singly linked list.
     * @return A CycleInfo object containing the entry index and cycle length.
     */
    public static <T> CycleInfo detectCycleInfo(LLNode<T> head){
        if (head == null || head.getLink() == null) {
            return new CycleInfo(-1, 0);
        }

        LLNode<T> slow = head;
        LLNode<T> fast = head;

        // Stage 1: Detect Cycle (Find Meeting Point)
        // If there's a cycle, slow and fast will meet.
        while (fast != null && fast.getLink() != null) {
            slow = slow.getLink();         // Moves 1 step (Tortoise)
            fast = fast.getLink().getLink(); // Moves 2 steps (Hare)

            if (slow == fast) {
                // Cycle detected. Contimue to find details.
                return findCycleDetails(head, slow);
            }
        }

        // If the loop finishes, fast reached the end (null). Then No cycle was found.
        return new CycleInfo(-1, 0);
    }
    /**
     * Helper method to calculate the cycle entry point and length using the meeting point.
     * This method is called only after a cycle has been detected.
     */
    private static <T> CycleInfo findCycleDetails(LLNode<T> head, LLNode<T> meetingPoint) {
        // Stage 2: Find Cycle Entry Node (Entry Index)
        // Start one pointer at the head and one at the meeting point.
        // They will meet at the cycle entry node.
        LLNode<T> pointer1 = head;
        LLNode<T> pointer2 = meetingPoint;
        int entryIndex = 0;

        while (pointer1 != pointer2) {
            pointer1 = pointer1.getLink();
            pointer2 = pointer2.getLink();
            entryIndex++; // Count the steps from the head to the entry node (aka the steps from the meetingPoint to the entry node)
        }
        // Stage 3 : Calculate Cycle Length
        // Start a third pointer from the entry node and count steps until it returns.
        LLNode<T> entryNode = pointer1; // This is the cycle entry node- equal to pointer2 at this point
        LLNode<T> current = entryNode.getLink(); //current starts at the second node of the cycle
        int cycleLength = 1; // Start with 1 because the entry node itself is the first element of the loop

        while (current != entryNode) {
            current = current.getLink();
            cycleLength++;
        }

        return new CycleInfo(entryIndex, cycleLength);
    }
}
