package graphics.command;

import lombok.Getter;

import java.util.Stack;

public class CommandContainer {
    private final Stack<DrawingCommand> undoStack = new Stack<>(); // Может хранить и команды и состояния
    private final Stack<DrawingCommand> redoStack = new Stack<>();


    @Getter
    private static final CommandContainer instance;

    static {
        instance = new CommandContainer();
    }

    public void executeCommand(DrawingCommand command) {
        command.execute();

        System.out.println(command.getClass());


        undoStack.push(command);
        redoStack.clear();
    }

    public void undo() {

        System.out.println(undoStack.size());

        if (!undoStack.isEmpty()) {
            DrawingCommand command = undoStack.pop();
            redoStack.push(command);
            command.undo();
        }
    }

    public void redo() {
        if (!redoStack.isEmpty()) {
            DrawingCommand command = redoStack.pop();
            command.execute();
            undoStack.push(command);
        }
    }
}
