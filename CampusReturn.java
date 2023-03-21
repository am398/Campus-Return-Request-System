import java.io.File;
import java.util.Scanner;

public class CampusReturn {

    public static void main(String[] args) throws Exception {
        File file = new File("C:\\Users\\91979\\Desktop\\CRR.txt");
        Scanner sc = new Scanner(file);
        LinkedList REQUEST = new LinkedList();
        LinkedList CONFIRM = new LinkedList();
        LinkedList WAITLIST = new LinkedList();
        int N = Integer.parseInt(sc.next());

        while (sc.hasNextLine()) {

            String accc = sc.next();
            switch (accc) {
                case "1":
                    String FirstName = sc.next();
                    String LastName = sc.next();
                    int RollNumber = Integer.parseInt(sc.next());
                    int Dayofweek = Integer.parseInt(sc.next());
                    REQUEST = LinkedList.insert(REQUEST, FirstName, LastName, RollNumber, Dayofweek);
                    if (N >= 1) {
                        CONFIRM = LinkedList.insert(CONFIRM, FirstName, LastName, RollNumber, Dayofweek);
                        N--;
                    } else {
                        WAITLIST = LinkedList.insert(WAITLIST, FirstName, LastName, RollNumber, Dayofweek);

                    }
                    break;
                case "2":
                    LinkedList.deleteByKey(CONFIRM, sc.next());
                    sc.next();
                    N++;
                    break;
                case "3":
                    System.out.println("REQUEST");
                    REQUEST = LinkedList.sortList(REQUEST);
                    LinkedList.printList(REQUEST);
                    break;
                case "4":
                    System.out.println("CONFIRMED");
                    WAITLIST = LinkedList.sortList(WAITLIST);
                    for (int i = 0; i < N; i++) {
                        CONFIRM = LinkedList.shift_from_waitlist_to_confirmed(WAITLIST, CONFIRM);
                    }
                    N = 0;
                    CONFIRM = LinkedList.sortList(CONFIRM);
                    LinkedList.printList(CONFIRM);
                    break;
                case "8":
                    System.out.println("WAITLIST");
                    WAITLIST = LinkedList.sortList(WAITLIST);
                    LinkedList.printList(WAITLIST);
                    break;
                default:
                    break;
            }
        }
    }

}

class LinkedList {

    Node head;

    static class Node {

        String Firstname;
        String lastname;
        int rollno;
        int DAY;
        Node next;

        Node(String a, String b, int c, int d) {
            Firstname = a;
            lastname = b;
            rollno = c;
            DAY = d;
            next = null;
        }
    }

    public static LinkedList insert(LinkedList list, String firstn, String lastn, int roll, int dayy) {
        Node new_node = new Node(firstn, lastn, roll, dayy);
        new_node.next = null;
        if (list.head == null) {
            list.head = new_node;
        } else {
            Node last = list.head;
            while (last.next != null) {
                last = last.next;
            }
            last.next = new_node;
        }
        return list;
    }

    public static void printList(LinkedList list) {
        Node currNode = list.head;
        while (currNode != null) {
            System.out.print(currNode.DAY + " " + currNode.Firstname + " " + currNode.lastname + " " + "(" + currNode.rollno + ")  ");
            currNode = currNode.next;
        }
        System.out.println("\n");
    }

    public static Node deleteByKey(LinkedList list, String firstname) {
        Node currNode = list.head, prev = null;
        if (currNode != null && currNode.Firstname.equals(firstname)) {
            list.head = currNode.next;
            return currNode;
        }
        while (currNode != null && !currNode.Firstname.equals(firstname)) {
            prev = currNode;
            currNode = currNode.next;
        }
        if (currNode != null) {
            prev.next = currNode.next;
        }
        if (currNode == null) {
            System.out.println();
        }
        return currNode;
    }

    public static LinkedList shift_from_waitlist_to_confirmed(LinkedList list1, LinkedList list2) {
        Node currNode = list1.head;
        if (currNode == null) {
            System.out.print(' ');
        } else {
            list2 = LinkedList.insert(list2, currNode.Firstname, currNode.lastname, currNode.rollno, currNode.DAY);
            list1.head = currNode.next;
        }
        return list2;
    }

    public static LinkedList sortList(LinkedList list) {
        Node current = list.head, index = null;
        if (current == null) {
            return list;
        } else if (current.next == null) {
            return list;
        } else {
            while (current != null) {
                index = current.next;
                while (index != null) {
                    if (current.DAY > index.DAY) {
                        swapNodes(list, current, index);
                        Node temp = current;
                        current = index;
                        index = temp;
                    }
                    index = index.next;
                }
                current = current.next;
            }
        }
        return list;
    }

    public static void swapNodes(LinkedList list, Node node1, Node node2) {
        int x = node1.rollno;
        int y = node2.rollno;
        if (x == y) {
            return;
        }
        Node prevX = null;
        Node currX = list.head;
        while (currX != null && currX.rollno != x) {
            prevX = currX;
            currX = currX.next;
        }
        Node prevY = null, currY = list.head;
        while (currY != null && currY.rollno != y) {
            prevY = currY;
            currY = currY.next;
        }
        if (currX == null || currY == null) {
            return;
        }
        if (prevX != null) {
            prevX.next = currY;
        } else //
        {
            list.head = currY;
        }
        if (prevY != null) {
            prevY.next = currX;
        } else {
            list.head = currX;
        }
        Node temp = currX.next;
        currX.next = currY.next;
        currY.next = temp;
    }

}
