import java.util.ArrayList;

import exceptions.StackEmptyException;

public class DynamicStack<T> implements Stack<T> {
  private ArrayList<T> stack;

  public DynamicStack() {
    stack = new ArrayList<>();
  }

  @Override
  public void push(T element) {
    stack.add(element);
  }

  @Override
  public T pop() throws StackEmptyException {
    if (isEmpty()) {
      throw new StackEmptyException("Stack Underflow: Cannot pop from empty stack");
    }
    return stack.remove(stack.size() - 1);
  }

  @Override
  public T peek() throws StackEmptyException {
    if (isEmpty()) {
      throw new StackEmptyException("Stack is empty: Cannot peek");
    }
    return stack.get(stack.size() - 1);
  }

  @Override
  public boolean isEmpty() {
    return stack.isEmpty();
  }

  @Override
  public String toString() {
    return stack.toString();
  }
}
