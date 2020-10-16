package me.desertdweller.sky3d.renderengine.font.renderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.desertdweller.sky3d.renderengine.Loader;
import me.desertdweller.sky3d.renderengine.font.meshcreator.FontType;
import me.desertdweller.sky3d.renderengine.font.meshcreator.Text;
import me.desertdweller.sky3d.renderengine.font.meshcreator.TextMeshData;

public class TextMaster {
	
	private static Loader loader;
	private static Map<FontType, List<Text>> texts = new HashMap<FontType, List<Text>>();
	private static FontRenderer renderer;
	
	public static void init(Loader theLoader){
		renderer = new FontRenderer();
		loader = theLoader;
	}
	
	public static void render(){
		renderer.render(texts);
	}
	
	public static void loadText(Text text){
		FontType font = text.getFont();
		TextMeshData data = font.loadText(text);
		int vao = loader.loadToVAO(data.getVertexPositions(), data.getTextureCoords());
		text.setMeshInfo(vao, data.getVertexCount());
		List<Text> textBatch = texts.get(font);
		if(textBatch == null){
			textBatch = new ArrayList<Text>();
			texts.put(font, textBatch);
		}
		textBatch.add(text);
	}
	
	@SuppressWarnings("unlikely-arg-type")
	public static void removeText(Text text){
		List<Text> textBatch = texts.get(text.getFont());
		textBatch.remove(text);
		if(textBatch.isEmpty()){
			texts.remove(texts.get(text.getFont()));
		}
	}
	
	public static void cleanUp(){
		renderer.cleanUp();
	}

}
