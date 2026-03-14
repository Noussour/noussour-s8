public class DoubledList {

  Node head;

  public DoubledList() {
    head = null;
  }

  public void insertFirst(int value) {
    Node newNode = new Node(value);

    if (head != null) {
      head.previous = newNode;
      newNode.next = head;
    }

    head = newNode;
  }

  public void insertEnd(int value) {
    Node newNode = new Node(value);

    if (head == null) {
      head = newNode;
      return;
    }

    Node current = head;

    while (current.next != null) {
      current = current.next;
    }

    current.next = newNode;
    newNode.previous = current;
  }

  public void supressFirst() {
    if (head == null) {
      return;
    }

    head = head.next;

    if (head != null) {
      head.previous = null;
    }
  }

  public void supressEnd() {
    if (head == null) {
      return;
    }

    if (head.next == null) {
      head = null;
      return;
    }

    Node current = head;

    while (current.next != null) {
      current = current.next;
    }

    current.previous.next = null;
  }
}
