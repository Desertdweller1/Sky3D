package me.desertdweller.sky3d.renderengine.guis;

import java.util.List;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import me.desertdweller.sky3d.renderengine.Loader;
import me.desertdweller.sky3d.renderengine.guis.guiobjects.GUIContainer;
import me.desertdweller.sky3d.renderengine.guis.guiobjects.GUIObject;
import me.desertdweller.sky3d.renderengine.models.RawModel;
import toolbox.Maths;

public class GUIRenderer {
	private final RawModel quad;
	private GUIShader shader;
	
	public GUIRenderer(Loader loader) {
		float[] positions = {-1, 1, -1, -1, 1, 1, 1, -1};
		quad = loader.loadToVAO(positions, 2);
		shader = new GUIShader();
	}
	
	public void render(List<GUIObject> guis) {
		shader.start();
		GL30.glBindVertexArray(quad.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		for(GUIObject gui : guis) {
			if(!(gui instanceof GUIContainer)) {
				GL13.glActiveTexture(GL13.GL_TEXTURE0);
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, gui.getTexture());
				Matrix4f matrix = Maths.createTransformationMatrix(gui.getGlobalPosition(), gui.getGlobalScale());
				shader.loadModifiers(gui.getOpacity(), gui.getCornerRadius(), gui.getColour());
				shader.loadTransformation(matrix);
				GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, quad.getVertexCount());
			}
		}
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_BLEND);
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		shader.stop();
	}
	
	public void cleanUp() {
		shader.cleanUp();
	}
}
