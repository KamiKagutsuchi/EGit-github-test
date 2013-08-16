package tetris.gui;

import java.awt.Graphics2D;
import java.util.Collection;
import java.util.HashSet;

public class GuiContainer extends GuiComponent {

	private Collection<GuiComponent> components=new HashSet<GuiComponent>();
	
	public void addComponent(GuiComponent component) {
		components.add(component);
	}
	
	public void removeComponent(GuiComponent component) {
		components.remove(component);
	}

	@Override
	public void render(Graphics2D g) {
		for (GuiComponent c : components) {
			c.render(g);
		}
	}

	@Override
	public void tick() {
		for (GuiComponent c : components) {
			c.tick();
		}
	}

}
