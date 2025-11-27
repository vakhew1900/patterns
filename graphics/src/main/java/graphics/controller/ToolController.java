package graphics.controller;

import graphics.tools.Tool;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ToolController extends MouseAdapter {
    private final Map<String, Tool> tools = new HashMap<>();
    private Tool currentTool;



    public void registerTool(Tool tool) {
        tools.put(tool.getName(), tool);
    }

    public void setCurrentTool(String toolName) {
        Tool tool = tools.get(toolName);
        if (tool != null) {
            this.currentTool = tool;
        }
    }

    public Map<String, Tool> getTools() {
        return new HashMap<>(tools);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        super.mouseMoved(e);
        currentTool.mouseMove(e);
    }
    @Override
    public void mouseDragged(MouseEvent e){
        super.mouseDragged(e);
        currentTool.mouseDrag(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        currentTool.mousePressed(e);
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
        currentTool.mouseReleased(e);
    }

    @Override
    public void mouseClicked(MouseEvent e){
        super.mouseClicked(e);
        System.out.println("Clicked mouse");
        currentTool.mouseClicked(e);
    }

    public Set<String> getRegisteredTools() {
        return tools.keySet();
    }
}