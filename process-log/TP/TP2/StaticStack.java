import java.util.Arrays;

import exceptions.StackFullException;
import exceptions.StackEmptyException;

public class StaticStack<T> implements Stack<T> {
  private T[] stack;
  private int top;
  private int capacity;

  @SuppressWarnings("unchecked")
  public StaticStack(int capacity) {
    this.capacity = capacity;
    stack = (T[]) new Object[capacity];
    top = -1;
  }

  @Override
  public void push(T element) throws StackFullException {
    if (top >= capacity - 1) {
      throw new StackFullException("Stack Overflow: Cannot push " + element);
    }
    stack[++top] = element;
  }

  @Override
  public T pop() throws StackEmptyException {
    if (isEmpty()) {
      throw new StackEmptyException("Stack Underflow: Cannot pop from empty stack");
    }
    T element = stack[top];
    stack[top--] = null;
    return element;
  }

  @Override
  public T peek() throws StackEmptyException {
    if (isEmpty()) {
      throw new StackEmptyException("Stack is empty: Cannot peek");
    }
    return stack[top];
  }

  @Override
  public boolean isEmpty() {
    return top == -1;
  }

  @Override
  public String toString() {
    return Arrays.toString(Arrays.copyOfRange(stack, 0, top + 1));
  }
}
